package org.piquerez.scalapaxos.paxos

import org.piquerez.scalapaxos.InternalSystem
import org.piquerez.scalapaxos.Process

/**
  * Created by Adrien on 12/30/2016.
  */
class PaxosProcess[T](val system : InternalSystem[PaxosProcess[T]], generator: PaxosNumberGenerator) extends Process[T] {
  private var learnedValue : Option[T] = None
  private var promisedNumber : Option[Int] = None
  private var acceptedValue : Option[T] = None

  override def isValueChosen: Boolean = learnedValue.isDefined

  override def propose(value: T): Unit = {
    val number = generator.Generate
    val promises = system.internalProcess.map(_.prepare(number))
        .filter(_.isPromise).toSeq
    if(promises.size >= system.quorum) {
      val lastAcceptedProposals = promises.flatten(_.lastAcceptedProposal)
      val newValue = if(lastAcceptedProposals.nonEmpty) {
        val maxNumber = lastAcceptedProposals.map{case (i, _) => i}.max
        lastAcceptedProposals.filter{case (i, _) => i == maxNumber}.head._2
      } else value
      val acceptCount = system.internalProcess.count(_.accept(number, newValue))
      if(acceptCount >= system.quorum) system.internalProcess.foreach(p => p.learn(newValue))
    }
  }

  override def value: T = learnedValue.get

  private def prepare(number: Int): Promise = this.synchronized {
    if(promisedNumber.fold(true)(i => i < number)) {
      val promise = promisedNumber.flatMap(i => acceptedValue.map(v => ConditionalPromise(i, v)))
        .getOrElse(UnconditionalPromise)
      promisedNumber = Some(number)
      promise
    }
    else Refuse
  }

  private def accept(number: Int, value: T): Boolean = this.synchronized {
    if (promisedNumber.fold(true)(i => i <= number)) {
      acceptedValue = Some(value)
      true
    }
    else false
  }

  private def learn(value : T) = this.synchronized(learnedValue = Some(value))

  private abstract class Promise
  private final case object Refuse extends Promise
  private final case object UnconditionalPromise extends Promise
  private final case class ConditionalPromise(lastAcceptedProposal: (Int, T)) extends Promise
  private implicit class RichPromise(promise: PaxosProcess[T]#Promise) {
    def isPromise: Boolean = promise match {
      case Refuse => false
      case _ => true
    }
    def lastAcceptedProposal: Option[(Int, T)] = promise match {
      case ConditionalPromise(proposal) => Some(proposal)
      case _ => None
    }
  }
}

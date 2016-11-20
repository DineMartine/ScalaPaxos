package org.piquerez.scalapaxos.paxos

import org.piquerez.scalapaxos.{InternalSystem, Process}

/**
  * Created by Adrien on 12/30/2016.
  */
class PaxosProcess[T](val system : InternalSystem[PaxosProcess[T]]) extends Process[T] {
  private var chosenValue : Option[T] = None

  override def IsValueChosen: Boolean = chosenValue.isDefined

  override def Propose(value: T): Unit = if(!IsValueChosen) system.internalProcess.foreach(_.Inform(value))

  override def Value: T = chosenValue.get

  private def Inform(value : T) = chosenValue = Some(value)
}

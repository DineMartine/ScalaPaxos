package org.piquerez.scalapaxos.naive

import org.piquerez.scalapaxos.InternalSystem
import org.piquerez.scalapaxos.Process

/**
  * Created by Adrien on 11/17/2016.
  */
class NaiveProcess[T](val system : InternalSystem[NaiveProcess[T]]) extends Process[T] {
  private var chosenValue : Option[T] = None

  override def isValueChosen: Boolean = chosenValue.isDefined

  override def propose(value: T): Unit = if(!isValueChosen) system.internalProcess.foreach(_.Inform(value))

  override def value: T = chosenValue.get

  private def Inform(value : T) = this.synchronized(chosenValue = Some(value))
}

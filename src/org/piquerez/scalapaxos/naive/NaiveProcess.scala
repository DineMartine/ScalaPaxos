package org.piquerez.scalapaxos.naive

import org.piquerez.scalapaxos.{InternalSystem, Process}
/**
  * Created by Adrien on 11/17/2016.
  */
class NaiveProcess[T](val system : InternalSystem[NaiveProcess[T]]) extends Process[T] {
  private var chosenValue : Option[T] = None

  override def IsValueChosen: Boolean = chosenValue.isDefined

  override def Propose(value: T) = if(!IsValueChosen) system.internalProcess.foreach(_.Inform(value))

  override def Value: T = chosenValue.get

  private def Inform(value : T) = chosenValue = Some(value)
}

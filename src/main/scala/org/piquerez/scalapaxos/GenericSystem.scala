package org.piquerez.scalapaxos

import scala.collection.mutable

/**
  * Created by Adrien on 11/17/2016.
  */
trait InternalSystem[TInternalProcess] {
  def internalProcess : Iterable[TInternalProcess]
  def quorum: Int = (internalProcess.size + 1) / 2
}

class GenericSystem[TValue, TInternalProcess] extends InternalSystem[TInternalProcess] with System[TValue] {
  private val processList = new mutable.MutableList[TInternalProcess with Process[TValue]]()
  def add(process : TInternalProcess with Process[TValue]) { processList += process }
  override val internalProcess: Iterable[TInternalProcess] = processList
  override val Processes: Seq[Process[TValue]] = processList
}

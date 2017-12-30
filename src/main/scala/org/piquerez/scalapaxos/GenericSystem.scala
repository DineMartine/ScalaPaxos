package org.piquerez.scalapaxos

/**
  * Created by Adrien on 11/17/2016.
  */
trait InternalSystem[TInternalProcess] {
  def internalProcess : Iterable[TInternalProcess]
  def quorum: Int = (internalProcess.size + 1) / 2
}

class GenericSystem[TValue, TInternalProcess](implicit toProcess: TInternalProcess => Process[TValue])
  extends InternalSystem[TInternalProcess] with System[TValue] {
  private var processList = List.empty[TInternalProcess]
  def add(process : TInternalProcess with Process[TValue]) { processList ::= process }
  def internalProcess: Iterable[TInternalProcess] = processList
  def processes: Seq[Process[TValue]] = processList.map(t => toProcess(t))
}


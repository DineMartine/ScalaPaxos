package org.piquerez.scalapaxos

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * Created by Adrien on 12/29/2017.
  */
class AsyncProcess[TValue, TInternalProcess <: Process[TValue]](internalProcess: TInternalProcess)
  extends Process[TValue] {
  def isValueChosen: Boolean = internalProcess.isValueChosen
  def propose(value: TValue): Unit = Future(internalProcess.propose(value))
  def value: TValue = internalProcess.value
}

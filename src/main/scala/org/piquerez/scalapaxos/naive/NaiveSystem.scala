package org.piquerez.scalapaxos.naive

import org.piquerez.scalapaxos.{AsyncProcess, GenericSystem, Process}

/**
  * Created by Adrien on 12/6/2016.
  */
object NaiveSystem {
  def apply[T](processCount : Int): GenericSystem[T, NaiveProcess[T]] = {
    val system = new GenericSystem[T, NaiveProcess[T]]()
    for(p <- 0 until processCount) system.add(new NaiveProcess[T](system))
    system
  }

  def async[T](processCount: Int): GenericSystem[T, NaiveProcess[T]] = {
    val system = new GenericSystem[T, NaiveProcess[T]]()(new AsyncProcess(_))
    for(p <- 0 until processCount)  system.add(new NaiveProcess[T](system))
    system
  }
}

package org.piquerez.scalapaxos.paxos

import org.piquerez.scalapaxos.{Process, AsyncProcess, GenericSystem}

/**
  * Created by Adrien on 12/30/2016.
  */
object PaxosSystem {
  def apply[T](processCount: Int): GenericSystem[T, PaxosProcess[T]] = {
    val generator = new SequentialNumberGenerator
    val system = new GenericSystem[T, PaxosProcess[T]]()
    for(p <- 0 until processCount) system.add(new PaxosProcess[T](system, generator))
    system
  }

  def async[T](processCount: Int): GenericSystem[T, PaxosProcess[T]] = {
    val generator = new SequentialNumberGenerator
    val system = new GenericSystem[T, PaxosProcess[T]]()(new AsyncProcess(_))
    for(p <- 0 until processCount)  system.add(new PaxosProcess[T](system, generator))
    system
  }
}

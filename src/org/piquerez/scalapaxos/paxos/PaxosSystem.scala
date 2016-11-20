package org.piquerez.scalapaxos.paxos

import org.piquerez.scalapaxos.GenericSystem

/**
  * Created by Adrien on 12/30/2016.
  */
object PaxosSystem {
  def apply[T](processCount : Int): GenericSystem[T, PaxosProcess[T]] = {
    val system = new GenericSystem[T, PaxosProcess[T]]()
    for(p <- 0 until processCount) system.add(new PaxosProcess[T](system))
    system
  }
}

package org.piquerez.scalapaxos.naive

import org.piquerez.scalapaxos.GenericSystem

/**
  * Created by Adrien on 12/6/2016.
  */
object NaiveSystem {
  def apply[T](processCount : Int) = {
    val system = new GenericSystem[T, NaiveProcess[T]]()
    for(p <- 0 until processCount) system.add(new NaiveProcess[T](system))
    system
  }
}

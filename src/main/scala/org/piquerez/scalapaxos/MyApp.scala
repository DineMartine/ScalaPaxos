package org.piquerez.scalapaxos

import org.piquerez.scalapaxos.naive.NaiveSystem

/**
  * Created by Adrien on 11/13/2016.
  */
object MyApp {
  def main(args: Array[String]) = {
    val system = NaiveSystem.async[String](5)
    system.processes(0).propose("a")
    system.processes(1).propose("b")
    system.processes(2).propose("c")
    system.processes(3).propose("d")
    system.processes(4).propose("e")
    Thread.sleep(2)
    if(system.processes.forall(_.isValueChosen))
      for(p <- system.processes) println(p.value)
    else
      println("Hello, World!")
  }
}

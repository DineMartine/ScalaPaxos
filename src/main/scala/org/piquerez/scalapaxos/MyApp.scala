package org.piquerez.scalapaxos

import org.piquerez.scalapaxos.naive.NaiveSystem

/**
  * Created by Adrien on 11/13/2016.
  */
object MyApp {
  def main(args: Array[String]) = {
    val system = NaiveSystem[String](5)
    system.Processes(0).Propose("a")
    system.Processes(1).Propose("b")
    system.Processes(2).Propose("c")
    system.Processes(3).Propose("d")
    system.Processes(4).Propose("e")
    if(system.Processes.forall(_.IsValueChosen))
      for(p <- system.Processes) println(p.Value)
    else
      println("Hello, World!")
  }
}

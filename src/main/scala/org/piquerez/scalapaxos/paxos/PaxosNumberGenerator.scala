package org.piquerez.scalapaxos.paxos

/**
  * Created by Adrien on 12/30/2016.
  */
trait PaxosNumberGenerator {
  def Generate : Int
}

class SequentialNumberGenerator extends PaxosNumberGenerator {
  var iterator : Int = 0
  override def Generate = {
    iterator = iterator + 1
    iterator
  }
}

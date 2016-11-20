package org.piquerez.scalapaxos

/**
  * Created by Adrien on 11/16/2016.
  */
trait Process[T] {
  def IsValueChosen : Boolean
  def Propose(value : T) : Unit
  def Value : T
}

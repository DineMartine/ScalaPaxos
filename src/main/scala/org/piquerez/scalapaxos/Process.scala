package org.piquerez.scalapaxos

/**
  * Created by Adrien on 11/16/2016.
  */
trait Process[T] {
  def isValueChosen : Boolean
  def propose(value : T) : Unit
  def value : T
}

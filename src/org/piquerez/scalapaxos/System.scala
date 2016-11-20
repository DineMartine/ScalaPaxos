package org.piquerez.scalapaxos

/**
  * Created by Adrien on 11/15/2016.
  */
trait System[T] {
  val Processes : Seq[Process[T]]
}

package org.piquerez.scalapaxos.naive
import org.piquerez.scalapaxos.ConsensusProperties
import org.piquerez.scalapaxos.System
import org.scalacheck.{Arbitrary, Gen, Properties}

/**
  * Created by Adrien on 12/2/2016.
  */
class NaiveProperties extends Properties("Naive Properties") with ConsensusProperties {
  val evenInteger: Gen[Int] = Arbitrary.arbitrary[Int] suchThat (_ % 2 == 0)

  override implicit val arbitrarySystem: Arbitrary[System[Int]] = Arbitrary {
    Gen.sized(s => NaiveSystem[Int](s))
  }
}

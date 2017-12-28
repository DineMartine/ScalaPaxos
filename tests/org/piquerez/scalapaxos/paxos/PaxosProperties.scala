package org.piquerez.scalapaxos.paxos

import org.piquerez.scalapaxos.ConsensusProperties
import org.piquerez.scalapaxos.System
import org.scalacheck.{Arbitrary, Gen, Properties}

/**
  * Created by Adrien on 12/10/2017.
  */
class PaxosProperties extends Properties("Paxos Properties") with ConsensusProperties {
  override val arbitrarySystem: Arbitrary[System[Int]] = Arbitrary {
    Gen.sized(s => PaxosSystem[Int](s))
  }
}

package org.piquerez.scalapaxos

import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Properties}

/**
  * Created by Adrien on 11/13/2016.
  */
trait ConsensusProperties extends Properties {
  property("OnlyProposedValuesAreChosen") = forAll {
    (testCase: ConsensusTestCase) => testCase.chosenValues.forall(testCase.proposedValues.contains(_))
  }

  property("OnlyOneValueCanBeChosen") = forAll {
    (testCase : ConsensusTestCase) => testCase.chosenValues.size <= 1
  }

  val arbitrarySystem : Arbitrary[System[Int]]

  implicit val arbitraryTestCase: Arbitrary[ConsensusTestCase] = Arbitrary {
    def arbitraryProposals(system : System[Int]): Gen[List[(Int, Int)]] = {
      Gen.listOf(Gen.choose(0, system.Processes.size - 1).flatMap(p => Arbitrary.arbitrary[Int].map(v => (p, v))))
    }
    arbitrarySystem.arbitrary.flatMap(s => arbitraryProposals(s).map(p => new ConsensusTestCase(s, p)))
  }

  class ConsensusTestCase(system : System[Int], proposals : Iterable[(Int, Int)]) {
    proposals.foreach{ case (i, v) => system.Processes(i).Propose(v) }
    def proposedValues: Set[Int] = proposals.map{ case (_,v) => v }.toSet
    def chosenValues : Set[Int] = system.Processes.filter(_.IsValueChosen).map(_.Value).toSet
  }
}

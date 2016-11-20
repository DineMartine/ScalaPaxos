package org.piquerez.scalapaxos

import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Properties}

/**
  * Created by Adrien on 11/13/2016.
  */
trait ConsensusProperties extends Properties {
  property("OnlyProposedValuesAreChosen") = forAll {
    (testCase: ConsensusTestCase) => testCase.proposedValues.forall(testCase.chosenValues.contains(_))
  }

  property("OnlyOneValueCanBeChosen") = forAll {
    (testCase : ConsensusTestCase) => testCase.chosenValues.size == 1
  }

  implicit val arbitrarySystem : Arbitrary[System[Int]]

  implicit val arbitraryTestCase = Arbitrary {
    def arbitraryProposals(system : System[Int]) = {
      Gen.listOf(Gen.choose(0, system.Processes.size - 1).flatMap(p => Arbitrary.arbitrary[Int].map(v => (p, v))))
    }
    arbitrarySystem.arbitrary.flatMap(s => arbitraryProposals(s).map(p => new ConsensusTestCase(s, p)))
  }

  class ConsensusTestCase(system : System[Int], proposals : Iterable[(Int, Int)]) {
    def execute() { proposals.foreach{ case (i, v) => system.Processes(i).Propose(v) } }
    def proposedValues: Set[Int] = proposals.map{ case (i,v) => v }.toSet
    def chosenValues = system.Processes.filter(_.IsValueChosen).map(_.Value).toSet
  }
}

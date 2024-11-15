package Controller

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import Model.Spieler
import scala.collection.mutable.ArrayBuffer

class StartSpec extends AnyWordSpec with Matchers {
  // Base test TUI to avoid console interactions
  class TestTUI extends aView.TUI {
    override def Startansicht(): Unit = {}
    override def zuwenigspieler(): Unit = {}
    override def Spielereinlesen(c: Int): Spieler = new Spieler("")
  }

  "A Start Controller" when {
    class StartTestable extends Start {
      var startAnsichtCalled = false
      var zuWenigSpielerCalled = false
      private var inputCounter = 0
      var inputSequence: Seq[String] = Seq()

      override val TUI = new TestTUI {
        override def Spielereinlesen(c: Int): Spieler = {
          if (inputCounter < inputSequence.length) {
            val player = new Spieler(inputSequence(inputCounter))
            inputCounter += 1
            player
          } else {
            new Spieler("")
          }
        }
      }

      override def Startansicht(): Unit = {
        startAnsichtCalled = true
      }

      override def zuwenigspieler(): Unit = {
        zuWenigSpielerCalled = true
      }

      def resetCounters(): Unit = {
        startAnsichtCalled = false
        zuWenigSpielerCalled = false
        inputCounter = 0
      }
    }

    "initializing" should {
      "have an empty player list" in {
        val controller = new StartTestable()
        controller.Spielerlistedynamic shouldBe empty
      }

      "have a TUI instance" in {
        val controller = new StartTestable()
        controller.TUI should not be null
      }
    }

    "starting a new game" should {
      "show start screen" in {
        val controller = new StartTestable()
        controller.Startansicht()
        controller.startAnsichtCalled shouldBe true
      }

      "warn when not enough players" in {
        val controller = new StartTestable()
        controller.zuwenigspieler()
        controller.zuWenigSpielerCalled shouldBe true
      }
    }

    "handling the Start process" should {
      "handle normal case with multiple valid players" in {
        val controller = new StartTestable()
        controller.inputSequence = Seq("Player1", "Player2", "Player3", "")
        val result = controller.Start()

        result should have length 3
        result.map(_.name) should contain allOf("Player1", "Player2", "Player3")
        controller.startAnsichtCalled shouldBe true
      }

      "handle case with empty name before minimum players" in {
        val controller = new StartTestable()
        controller.inputSequence = Seq("Player1", "", "Player2", "Player3", "")
        val result = controller.Start()

        result should have length 3
        result.map(_.name) should contain allOf("Player1", "Player2", "Player3")
        controller.zuWenigSpielerCalled shouldBe true
      }

      "continue until minimum players reached despite empty names" in {
        val controller = new StartTestable()
        controller.inputSequence = Seq("Player1", "", "", "Player2", "")
        val result = controller.Start()

        result should have length 2
        result.map(_.name) should contain allOf("Player1", "Player2")
        controller.zuWenigSpielerCalled shouldBe true
      }

      "handle exactly minimum required players" in {
        val controller = new StartTestable()
        controller.inputSequence = Seq("Player1", "Player2", "")
        val result = controller.Start()

        result should have length 2
        result.map(_.name) should contain allOf("Player1", "Player2")
      }
    }

    "managing player list" should {
      "maintain correct order of players" in {
        val controller = new StartTestable()
        controller.inputSequence = Seq("First", "Second", "Third", "")
        val result = controller.Start()

        result.map(_.name) shouldBe Array("First", "Second", "Third")
      }

      "handle dynamic list conversion correctly" in {
        val controller = new StartTestable()
        val players = Array(new Spieler("Test1"), new Spieler("Test2"))
        controller.Spielerlistedynamic ++= players

        val converted = controller.Spielerlistedynamic.toArray
        converted.map(_.name) shouldBe Array("Test1", "Test2")
      }
    }

    "handling edge cases" should {
      "handle alternating valid and empty names" in {
        val controller = new StartTestable()
        controller.inputSequence = Seq("Player1", "", "Player2", "", "Player3", "")
        val result = controller.Start()

        result should have length 2
        controller.zuWenigSpielerCalled shouldBe true
      }

      "handle multiple consecutive empty names" in {
        val controller = new StartTestable()
        controller.inputSequence = Seq("Player1", "", "", "", "Player2", "")
        val result = controller.Start()

        result should have length 2
        controller.zuWenigSpielerCalled shouldBe true
      }

      "handle case when first input is empty" in {
        val controller = new StartTestable()
        controller.inputSequence = Seq("", "Player1", "Player2", "")
        val result = controller.Start()

        result should have length 2
        controller.zuWenigSpielerCalled shouldBe true
      }

      "reset state between multiple starts" in {
        val controller = new StartTestable()
        controller.inputSequence = Seq("Player1", "Player2", "")
        controller.Start()

        controller.resetCounters()
        controller.inputSequence = Seq("NewPlayer1", "NewPlayer2", "")
        val result = controller.Start()

        result should have length 4
        result.map(_.name) should contain allOf("NewPlayer1", "NewPlayer2")
      }
    }
  }
}
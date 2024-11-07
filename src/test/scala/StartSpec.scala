import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import scala.collection.mutable.ArrayBuffer

class StartSpec extends AnyWordSpec with Matchers {

  "The Start class" should {

    "initialize a list of players correctly when Start() is called" in {
      val mockTUI = new View.TUI {
        override def Startansicht(): Unit = {} // Mock empty view
        override def Spielereinlesen(c: Int): Teilnehmer.Spieler = {
          if (c == 0) new Teilnehmer.Spieler("Player 1", new Teilnehmer.Feld())
          else if (c == 1) new Teilnehmer.Spieler("Player 2", new Teilnehmer.Feld())
          else new Teilnehmer.Spieler("", new Teilnehmer.Feld())
        }
        override def zuwenigspieler(): Unit = {} // Mock warning for too few players
      }

      val start = new Start
      start.TUI = mockTUI // Use the mock TUI

      val result = start.Start()

      result.length shouldBe 2 // Expecting 2 players to be added
      result(0).name shouldBe "Player 1"
      result(1).name shouldBe "Player 2"
    }

    "call zuwenigspieler when less than 2 players are entered" in {
      val mockTUI = new View.TUI {
        var zuwenigspielerCalled = false
        override def Startansicht(): Unit = {}
        override def Spielereinlesen(c: Int): Teilnehmer.Spieler = new Teilnehmer.Spieler("", new Teilnehmer.Feld())
        override def zuwenigspieler(): Unit = {
          zuwenigspielerCalled = true
        }
      }

      val start = new Start
      start.TUI = mockTUI // Use the mock TUI

      start.Start() // Call Start() to trigger the zuwenigspieler() method

      mockTUI.zuwenigspielerCalled shouldBe true // Ensure zuwenigspieler() was called
    }

    "display the correct start view when Startansicht is called" in {
      val mockTUI = new View.TUI {
        var startansichtCalled = false
        override def Startansicht(): Unit = {
          startansichtCalled = true
        }
        override def Spielereinlesen(c: Int): Teilnehmer.Spieler = new Teilnehmer.Spieler("", new Teilnehmer.Feld())
        override def zuwenigspieler(): Unit = {}
      }

      val start = new Start
      start.TUI = mockTUI // Use the mock TUI

      start.Startansicht() // Trigger the Startansicht() method

      mockTUI.startansichtCalled shouldBe true // Ensure Startansicht() was called
    }
  }
}

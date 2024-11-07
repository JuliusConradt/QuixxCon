import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import Teilnehmer._

class SpielerSpec extends AnyWordSpec with Matchers {

  "The Spieler class" should {

    "initialize correctly with a name and a new Feld instance" in {
      val spieler = new Spieler("Max")

      spieler.name shouldBe "Max"
      spieler.Feld shouldBe a [Feld]  // Überprüft, dass das Feld ein Feld-Objekt ist
    }

    "assign a new Feld instance for each player" in {
      val spieler1 = new Spieler("Max")
      val spieler2 = new Spieler("Anna")

      spieler1.Feld should not be theSameInstanceAs(spieler2.Feld)
      // Überprüft, dass die Felder der Spieler nicht dasselbe Objekt sind
    }
  }
}

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import model.{Feld, Reihe}

class FeldSpec extends AnyWordSpec with Matchers {

  "A Feld" should {
    "correctly initialize Red, Yellow, Green, and Blue Reihen" in {
      val feld = new Feld

      // Überprüfe, ob die Reihen korrekt initialisiert wurden
      feld.Red shouldBe a [Reihe]
      feld.Yellow shouldBe a [Reihe]
      feld.Green shouldBe a [Reihe]
      feld.Blue shouldBe a [Reihe]

      // Überprüfe, ob jede Reihe den richtigen id-Wert hat
      feld.Red.Typ shouldBe 1
      feld.Yellow.Typ shouldBe 2
      feld.Green.Typ shouldBe 3
      feld.Blue.Typ shouldBe 4
    }

    "initialize fwcount to 0" in {
      val feld = new Feld

      // Überprüfe, ob der 'fwcount' korrekt auf 0 gesetzt ist
      feld.fwcount shouldBe 0
    }
  }
}

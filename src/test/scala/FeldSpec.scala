import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import Teilnehmer._

class FeldSpec extends AnyWordSpec with Matchers {

  "The Feld class" should {

    "initialize with default values correctly" in {
      val feld = new Feld

      feld.Red shouldBe a[Reihe]
      feld.Yellow shouldBe a[Reihe]
      feld.Green shouldBe a[Reihe]
      feld.Blue shouldBe a[Reihe]

      feld.Red.typ shouldBe 1
      feld.Yellow.typ shouldBe 2
      feld.Green.typ shouldBe 3
      feld.Blue.typ shouldBe 4

      feld.fwcount shouldBe 0
    }

    "allow modification of fwcount" in {
      val feld = new Feld

      feld.fwcount = 5
      feld.fwcount shouldBe 5

      feld.fwcount = 10
      feld.fwcount shouldBe 10
    }
  }
}

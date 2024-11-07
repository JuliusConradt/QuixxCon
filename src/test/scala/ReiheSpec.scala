import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import Teilnehmer._

class ReiheSpec extends AnyWordSpec with Matchers {

  "The Reihe class" should {

    "initialize with default values correctly" in {
      val reihe = new Reihe(1)

      reihe.Typ shouldBe 1
      reihe.Kreuze shouldBe Array.fill(12)(false)
      reihe.count shouldBe 0
    }

    "allow modification of Kreuze and count" in {
      val reihe = new Reihe(1)

      // Set some crosses
      reihe.Kreuze(0) = true
      reihe.Kreuze(3) = true

      // Test that Kreuze array was modified correctly
      reihe.Kreuze(0) shouldBe true
      reihe.Kreuze(3) shouldBe true
      reihe.Kreuze(1) shouldBe false

      // Increment the count
      reihe.count = 5
      reihe.count shouldBe 5
    }
  }
}

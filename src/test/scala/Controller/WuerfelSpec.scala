import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import Controller.Wuerfel

class WuerfelSpec extends AnyWordSpec with Matchers {

  "A Wuerfel" should {

    "initialize correctly" in {
      val wuerfel = new Wuerfel
      wuerfel should not be null
    }

    "roll a number between 1 and 6" in {
      val wuerfel = new Wuerfel
      val roll = wuerfel.random()
      roll should (be >= 1 and be <= 6)
    }

    "roll different numbers" in {
      val wuerfel = new Wuerfel
      val rolls = (1 to 100).map(_ => wuerfel.random()).toSet
      rolls.size should be > 1
    }
  }
}
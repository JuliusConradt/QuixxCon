import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class WuerfelSpec extends AnyWordSpec with Matchers {

  "The Wuerfel class" should {

    "generate a random integer between 1 and 6 with random()" in {
      val wuerfel = new Wuerfel
      val randomValue = wuerfel.random()
      randomValue should (be >= 1 and be <= 6)
    }

    "generate an array of 6 random integers between 1 and 6 with wuerfeln()" in {
      val wuerfel = new Wuerfel
      val wurfArray = wuerfel.wuerfeln()

      // Array should have a length of 6
      wurfArray.length shouldBe 6

      // Each element in the array should be between 1 and 6
      all(wurfArray) should (be >= 1 and be <= 6)
    }

    "generate a string with color codes for the rolled values with printwurf()" in {
      val wuerfel = new Wuerfel
      val wurfArray = Array(1, 2, 3, 4, 5, 6)
      val printResult = wuerfel.printwurf(wurfArray)

      // Expecting the format to contain the color codes from Spielfeld
      printResult should include (wuerfel.Spielfeld.white + "1")
      printResult should include ("2")
      printResult should include (wuerfel.Spielfeld.red + "3")
      printResult should include (wuerfel.Spielfeld.yellow + "4")
      printResult should include (wuerfel.Spielfeld.green + "5")
      printResult should include (wuerfel.Spielfeld.blue + "6")
    }
  }
}

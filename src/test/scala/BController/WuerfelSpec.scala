package BController

import model.Wuerfel
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class WuerfelSpec extends AnyWordSpec with Matchers {

  "A Wuerfel" should {

    val wuerfel = new Wuerfel()

    "return a random integer between 1 and 6 in random()" in {
      // Call the random method multiple times to verify that the result is within the expected range
      for (_ <- 1 to 100) {
        val result = wuerfel.random()
        result should be >= 1
        result should be <= 6
      }
    }

    "return an array of length 6 in wuerfeln()" in {
      val result = wuerfel.wuerfeln()
      result.length shouldBe 6
    }

    "return an array of integers between 1 and 6 in wuerfeln()" in {
      val result = wuerfel.wuerfeln()
      all(result) should (be >= 1 and be <= 6)
    }

    "produce different results on subsequent calls to wuerfeln()" in {
      val result1 = wuerfel.wuerfeln()
      val result2 = wuerfel.wuerfeln()
      result1 should not equal result2 // High likelihood that results differ due to randomness
    }

  }
}

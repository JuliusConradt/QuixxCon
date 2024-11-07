import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class EndeSpec extends AnyWordSpec with Matchers {

  "The Ende class" should {

    "calculate the winner correctly based on the player's points" in {
      val mockTUI = new View.TUI {
        override def ende(spieler: Teilnehmer.Spieler, points: Int): Unit = {
          // Mock the TUI output for testing
          spieler.name shouldBe "Player 2"
          points shouldBe 36
        }
      }

      val player1 = new Teilnehmer.Spieler("Player 1", new Teilnehmer.Feld(1, 2, 3, 4, 5))
      val player2 = new Teilnehmer.Spieler("Player 2", new Teilnehmer.Feld(2, 3, 4, 5, 6))
      val players = Array(player1, player2)

      val ende = new Ende
      ende.TUI = mockTUI
      ende.ende(players)
    }

    "return the correct points for different row counts in Reihenpunkte" in {
      val ende = new Ende

      ende.Reihenpunkte(0) shouldBe 0
      ende.Reihenpunkte(1) shouldBe 1
      ende.Reihenpunkte(2) shouldBe 3
      ende.Reihenpunkte(3) shouldBe 6
      ende.Reihenpunkte(4) shouldBe 10
      ende.Reihenpunkte(5) shouldBe 15
      ende.Reihenpunkte(6) shouldBe 21
      ende.Reihenpunkte(7) shouldBe 28
      ende.Reihenpunkte(8) shouldBe 36
      ende.Reihenpunkte(9) shouldBe 45
      ende.Reihenpunkte(10) shouldBe 55
      ende.Reihenpunkte(11) shouldBe 66
      ende.Reihenpunkte(12) shouldBe 78
    }
  }
}

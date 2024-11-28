import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import BController.Spiel
import BView.TUI
import model.Spieler

// Test implementation of TUI with enhanced response patterns
class TestTUI extends TUI {
  var responsePattern: List[Int] = List(1) // Default response

  def setResponses(responses: List[Int]): Unit = {
    responsePattern = responses
  }

  override def Antwort(a: Int): Int = {
    if (responsePattern.isEmpty) 0
    else {
      val response = responsePattern.head
      responsePattern = responsePattern.tail
      if (response > a) 0 else response
    }
  }

  override def Rundenbeginn(Nr: Int, name: String): Unit = {}
  override def Feld(p: Spieler): String = ""
  override def Wurf(w: Array[Int]): String = ""
  override def Option1(comb: Array[Int]): String = ""
  override def Option2(comb: Array[Int]): String = ""
}

class TestSpiel(spieler: Array[Spieler]) extends Spiel(spieler) {
  override val TUI = new TestTUI

  def getTestTUI: TestTUI = TUI.asInstanceOf[TestTUI]

  // Expose Wuerfel for testing
  def getWuerfel = Wuerfel
}

class SpielSpec extends AnyWordSpec with Matchers {
  "A Spiel" should {
    val player1 = new Spieler("Player1")
    val player2 = new Spieler("Player2")
    val players = Array(player1, player2)
    val spiel = new TestSpiel(players)

    "be properly initialized" in {
      spiel.Spieler should have length 2
      spiel.Runde should be(0)
      spiel.Master should be(-1)
      spiel.User should be(spiel.Master)
      spiel.rowsclosed should be(0)
      spiel.rclosed should be(false)
      spiel.yclosed should be(false)
      spiel.gclosed should be(false)
      spiel.bclosed should be(false)
      spiel.beendet should be(false)
    }

    "handle dice combinations for Option1" in {
      val testWurf = Array(2, 3, 4, 5, 1, 6)
      val option1Result = spiel.Option1(testWurf, player1)
      option1Result should have length 4
      option1Result.forall(_ >= 0) should be(true)
    }

    "handle dice combinations for Option2" in {
      val testWurf = Array(2, 3, 4, 5, 1, 6)
      val option2Result = spiel.Option2(testWurf, player1)
      option2Result should have length 8
      option2Result.forall(_ >= 0) should be(true)
    }

    "handle row closure conditions" in {
      // Test red row closure
      (1 to 5).foreach(i => spiel.Ankreuzen(player1, i + 2, 1))
      spiel.Ankreuzen(player1, 12, 1) // Close red row
      spiel.rclosed should be(true)
      spiel.rowsclosed should be(1)

      // Test yellow row closure
      (1 to 5).foreach(i => spiel.Ankreuzen(player2, i + 2, 2))
      spiel.Ankreuzen(player2, 12, 2) // Close yellow row
      spiel.yclosed should be(true)
      spiel.rowsclosed should be(2)

      // Test green row closure
      val player3 = new Spieler("Player3")
      (1 to 5).foreach(i => spiel.Ankreuzen(player3, 12 - i, 3))
      spiel.Ankreuzen(player3, 2, 3) // Close green row
      spiel.gclosed should be(true)

      // Test blue row closure
      (1 to 5).foreach(i => spiel.Ankreuzen(player3, 12 - i, 4))
      spiel.Ankreuzen(player3, 2, 4) // Close blue row
      spiel.bclosed should be(true)
    }

    "handle complete game flow" in {
      val newSpiel = new TestSpiel(Array(new Spieler("TestPlayer")))
      newSpiel.getTestTUI.setResponses(List(1, 1, 0, 1, 1, 0)) // Mix of valid moves and passes

      val result = newSpiel.Spielstart()
      result should have length 1
      result(0).name should be("TestPlayer")
    }

    "handle SubRound with different options" in {
      val newPlayer = new Spieler("SubRoundTest")
      val testWurf = Array(3, 4, 5, 6, 2, 1)

      // Test SubRound with different response patterns
      val newSpiel = new TestSpiel(Array(newPlayer))
      newSpiel.getTestTUI.setResponses(List(0)) // Pass
      newSpiel.SubRound(newPlayer, testWurf, 1)

      newSpiel.getTestTUI.setResponses(List(1)) // Take first option
      newSpiel.SubRound(newPlayer, testWurf, 2)

      newPlayer.Feld.fwcount should be >= 0
    }

    "handle verfuegbar correctly for all row types" in {
      val testPlayer = new Spieler("VerfuegbarTest")

      // Test red row (ascending)
      spiel.verfuegbar(testPlayer, 1, 5) should be(0)
      spiel.Ankreuzen(testPlayer, 5, 1)
      spiel.verfuegbar(testPlayer, 1, 5) should be(0)

      // Test yellow row (ascending)
      spiel.verfuegbar(testPlayer, 2, 6) should be(0)
      spiel.Ankreuzen(testPlayer, 6, 2)
      spiel.verfuegbar(testPlayer, 2, 6) should be(0)

      // Test green row (descending)
      spiel.verfuegbar(testPlayer, 3, 7) should be(0)
      spiel.Ankreuzen(testPlayer, 7, 3)
      spiel.verfuegbar(testPlayer, 3, 7) should be(0)

      // Test blue row (descending)
      spiel.verfuegbar(testPlayer, 4, 8) should be(0)
      spiel.Ankreuzen(testPlayer, 8, 4)
      spiel.verfuegbar(testPlayer, 4, 8) should be(0)
    }
  }
}
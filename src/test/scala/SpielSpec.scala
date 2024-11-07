import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import scala.collection.mutable.ArrayBuffer
import scala.util.control.Breaks

class SpielSpec extends AnyWordSpec with Matchers {

  "The Spiel class" should {

    "run the game start and process rounds correctly in Spielstart()" in {
      val mockTUI = new View.TUI {
        override def Antwort(a: Int): Int = 1
        override def Rundenbeginn(Nr: Int, name: String): Unit = {}
        override def Feld(p: Teilnehmer.Spieler): Unit = {}
        override def Wurf(w: Array[Int]): Unit = {}
        override def Option1(comb: Array[Int]): Array[Int] = comb
        override def Option2(comb: Array[Int]): Array[Int] = comb
      }

      val spieler = Array(
        new Teilnehmer.Spieler("Player 1", new Teilnehmer.Feld()),
        new Teilnehmer.Spieler("Player 2", new Teilnehmer.Feld())
      )

      val spiel = new Spiel(spieler)
      spiel.TUI = mockTUI // Set mock TUI

      val result = spiel.Spielstart()

      result.length shouldBe 2 // Verify two players
      result(0).name shouldBe "Player 1"
      result(1).name shouldBe "Player 2"
    }

    "process a sub-round correctly in SubRound()" in {
      val mockTUI = new View.TUI {
        override def Rundenbeginn(Nr: Int, name: String): Unit = {}
        override def Feld(p: Teilnehmer.Spieler): Unit = {}
        override def Auswertung(comb: Array[Int], p: Teilnehmer.Spieler, b: Boolean): Unit = {}
      }

      val spieler = Array(new Teilnehmer.Spieler("Player 1", new Teilnehmer.Feld()))
      val spiel = new Spiel(spieler)
      spiel.TUI = mockTUI // Use mock TUI

      spiel.SubRound(spieler(0), Array(1, 2, 3, 4, 5, 6), 1)

      // Ensure the methods are being called (you can verify with mock-specific checks if needed)
    }

    "evaluate the game correctly in Auswertung()" in {
      val mockTUI = new View.TUI {
        override def Rundenbeginn(Nr: Int, name: String): Unit = {}
        override def Feld(p: Teilnehmer.Spieler): Unit = {}
        override def Wurf(w: Array[Int]): Unit = {}
        override def Option1(comb: Array[Int]): Array[Int] = Array(1, 2, 3)
        override def Option2(comb: Array[Int]): Array[Int] = Array(1, 2, 3)
      }

      val spieler = Array(new Teilnehmer.Spieler("Player 1", new Teilnehmer.Feld()))
      val spiel = new Spiel(spieler)
      spiel.TUI = mockTUI // Use mock TUI

      // You can test the logic of `Auswertung()` with various inputs
      val comb = Array(1, 2, 3, 4)
      val player = spieler(0)
      spiel.Auswertung(comb, player, false)

      // You can extend the tests to assert that the state of the `spieler` object changes as expected
    }

    "correctly handle player actions and game flow in Ankreuzen()" in {
      val mockTUI = new View.TUI {
        override def Rundenbeginn(Nr: Int, name: String): Unit = {}
        override def Feld(p: Teilnehmer.Spieler): Unit = {}
        override def Wurf(w: Array[Int]): Unit = {}
        override def Option1(comb: Array[Int]): Array[Int] = Array(1, 2, 3)
        override def Option2(comb: Array[Int]): Array[Int] = Array(1, 2, 3)
      }

      val player = new Teilnehmer.Spieler("Player 1", new Teilnehmer.Feld())
      val spiel = new Spiel(Array(player))
      spiel.TUI = mockTUI // Use mock TUI

      spiel.Ankreuzen(player, 5, 1) // Test case where player crosses out a value

      // Further assertions can be made based on changes to the player's field or game state
    }

    "check the available options in verfuegbar()" in {
      val player = new Teilnehmer.Spieler("Player 1", new Teilnehmer.Feld())
      val spiel = new Spiel(Array(player))

      // Test with various values for `typ` and `sum` to ensure the logic in `verfuegbar` is working
      val result = spiel.verfuegbar(player, 1, 5)

      result shouldBe 5 // Modify based on the expected outcome
    }
  }
}

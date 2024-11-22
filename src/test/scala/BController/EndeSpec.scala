package BController

import model.Spieler
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class EndeSpec extends AnyWordSpec with Matchers {

  "The Ende class" should {

    "correctly calculate the winner and call TUI.ende with the correct player and score" in {
      // Fake-TUI-Klasse zur Simulation
      class FakeTUI extends BView.TUI {
        var ended = false
        var winnerPlayer: Spieler = _
        var score: Int = _

        // Fake-Methode für ende
        override def ende(player: Spieler, points: Int): Unit = {
          ended = true
          winnerPlayer = player
          score = points
        }
      }

      // Erstelle eine Instanz von FakeTUI
      val fakeTUI = new FakeTUI

      // Erstelle Spieler mit Namen. Das Feld wird automatisch erzeugt.
      val player1 = new Spieler("Player1")
      player1.Feld.Red.count = 3
      player1.Feld.Yellow.count = 4
      player1.Feld.Green.count = 2
      player1.Feld.Blue.count = 1

      val player2 = new Spieler("Player2")
      player2.Feld.Red.count = 2
      player2.Feld.Yellow.count = 5
      player2.Feld.Green.count = 3
      player2.Feld.Blue.count = 4

      val player3 = new Spieler("Player3")
      player3.Feld.Red.count = 1
      player3.Feld.Yellow.count = 2
      player3.Feld.Green.count = 3
      player3.Feld.Blue.count = 0

      // Erstelle das Ende-Objekt und überschreibe TUI
      val endeController = new Ende {
        override val TUI = fakeTUI
      }

      // Erstelle ein Array mit den Spielern
      val players = Array(player1, player2, player3)

      // Berechne das Ende
      endeController.ende(players)

      // Überprüfe, ob die Methode TUI.ende mit dem richtigen Gewinner und Punktzahl aufgerufen wurde
      fakeTUI.ended shouldBe true
      fakeTUI.winnerPlayer shouldBe player2  // Spieler 2 hat die höchste Punktzahl von 36
      fakeTUI.score shouldBe 34
    }

    "correctly calculate Reihenpunkte" in {
      val endeController = new Ende()

      // Teste, ob die Reihenpunkte korrekt berechnet werden
      endeController.Reihenpunkte(0) should be(0)
      endeController.Reihenpunkte(1) should be(1)
      endeController.Reihenpunkte(2) should be(3)
      endeController.Reihenpunkte(3) should be(6)
      endeController.Reihenpunkte(4) should be(10)
      endeController.Reihenpunkte(5) should be(15)
      endeController.Reihenpunkte(6) should be(21)
      endeController.Reihenpunkte(7) should be(28)
      endeController.Reihenpunkte(8) should be(36)
      endeController.Reihenpunkte(9) should be(45)
      endeController.Reihenpunkte(10) should be(55)
      endeController.Reihenpunkte(11) should be(66)
      endeController.Reihenpunkte(12) should be(78)
    }
  }
}

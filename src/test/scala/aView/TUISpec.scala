package aView

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.io.StdIn

class TUISpec extends AnyWordSpec with Matchers {

  "A TUI" should {

    val tui = new TUI()

    "print the start message in Startansicht" in {
      // Capture standard output for testing
      val outputStream = new java.io.ByteArrayOutputStream()
      Console.withOut(outputStream) {
        tui.Startansicht()
      }
      outputStream.toString should include ("Willkommen bei Quixx")
      outputStream.toString should include ("Erstelle alle Spieler. Beende deine Eingabe mit einem leeren Spieler")
    }

    "print the message for too few players in zuwenigspieler" in {
      val outputStream = new java.io.ByteArrayOutputStream()
      Console.withOut(outputStream) {
        tui.zuwenigspieler()
      }
      outputStream.toString should include ("Erstelle mindenstens 2 Spieler")
    }

    "read a player name in Spielereinlesen" in {
      val playerName = "TestPlayer"
      val inputStream = new java.io.ByteArrayInputStream(s"$playerName\n".getBytes)
      Console.withIn(inputStream) {
        val player = tui.Spielereinlesen(0)
        player.name should be (playerName)
      }
    }

    "display end game message with winner name and points in ende" in {
      val outputStream = new java.io.ByteArrayOutputStream()
      Console.withOut(outputStream) {
        val mockPlayer = new Model.Spieler("Winner")
        tui.ende(mockPlayer, 42)
      }
      outputStream.toString should include ("--- Winner gewinnt mit 42 Punkten ---")
    }

    "display the round number and player name in Rundenbeginn" in {
      val outputStream = new java.io.ByteArrayOutputStream()
      Console.withOut(outputStream) {
        tui.Rundenbeginn(3, "PlayerName")
      }
      outputStream.toString should include ("Runde 3 | Feld von Spieler: PlayerName")
    }

    "validate Antwort method for valid and invalid inputs" in {
      // Valid input scenario
      val validInput = "2\n"
      val inputStream = new java.io.ByteArrayInputStream(validInput.getBytes)
      Console.withIn(inputStream) {
        tui.Antwort(2) should be (2)
      }

      // Invalid input scenario (testing loop)
      val invalidInput = "invalid\n3\n1\n"
      val inputStreamInvalid = new java.io.ByteArrayInputStream(invalidInput.getBytes)
      Console.withIn(inputStreamInvalid) {
        tui.Antwort(2) should be (1) // Expected the valid input "1" after invalid inputs
      }
    }

    "generate the correct Wurf string with colors" in {
      val wurfArray = Array(1, 2, 3, 4, 5, 6)
      val result = tui.Wurf(wurfArray)
      result should include (tui.white + "Wurf: 1 2 ")
      result should include (tui.red + "3 ")
      result should include (tui.yellow + "4 ")
      result should include (tui.green + "5 ")
      result should include (tui.blue + "6" + tui.white)
    }

    "generate the correct Option2 string with available options" in {
      val combArray = Array(3, 0, 5, 0, 7, 0, 0, 4)
      val result = tui.Option2(combArray)
      result should include ("[1] " + tui.red + "3" + tui.white)
      result should include ("[2] " + tui.yellow + "5" + tui.white)
      result should include ("[3] " + tui.green + "7" + tui.white)
      result should include ("[4] " + tui.blue + "4" + tui.white)
    }

    "generate the correct Option1 string with available options" in {
      val combArray = Array(2, 0, 3, 0)
      val result = tui.Option1(combArray)
      result should include ("[1] N")
      result should include ("[2] " + tui.red + "2" + tui.white)
      result should include ("[3] " + tui.green + "3" + tui.white)
    }

    "generate the correct Feld string layout for a player" in {
      val mockPlayer = new Model.Spieler("Player")
      val result = tui.Feld(mockPlayer)
      result should include (tui.red + tui.mesh)
      result should include (tui.yellow + tui.mesh)
      result should include (tui.green + tui.mesh)
      result should include (tui.blue + tui.mesh)
      result should include (tui.white + tui.fails.substring(0, 8))
    }
  }
}

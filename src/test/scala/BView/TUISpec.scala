package BView

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import model.{Reihe, Spieler}
import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

class TUISpec extends AnyWordSpec with Matchers {
  "A TUI" when {
    val tui = new TUI()
    val testSpieler = new Spieler("TestPlayer")

    "displaying colors" should {
      "have correct ANSI color codes" in {
        tui.red should be("\u001B[31m")
        tui.yellow should be("\u001B[33m")
        tui.green should be("\u001B[32m")
        tui.blue should be("\u001B[34m")
        tui.white should be("\u001B[0m")
      }
    }

    "creating mesh lines" should {
      "have correct format" in {
        tui.mesh.length should be(73)  // 12 * 6 + 1
        tui.mesh should startWith("+")
        tui.mesh should endWith("+")
        tui.mesh should include("-----")
      }
    }

    "displaying game lines" should {
      "have correct format for red/yellow line" in {
        tui.ryline.length should be(73)
        tui.ryline should startWith("|")
        tui.ryline should endWith("|")
        tui.ryline should include("2")
        tui.ryline should include("12")
      }

      "have correct format for green/blue line" in {
        tui.gbline.length should be(73)
        tui.gbline should startWith("|")
        tui.gbline should endWith("|")
        tui.gbline should include("12")
        tui.gbline should include("2")
      }
    }

    "displaying fails" should {
      "have correct format" in {
        tui.fails.length should be(24)
        tui.fails should startWith("FEHLWURF")
        tui.fails should include("[ ]")
      }
    }

    "reading player input" should {
      "create a new player with given name" in {
        val input = new ByteArrayInputStream("TestPlayer\n".getBytes)
        val output = new ByteArrayOutputStream()
        Console.withIn(input) {
          Console.withOut(output) {
            val player = tui.Spielereinlesen(0)
            player.name should be("TestPlayer")
          }
        }
        output.toString should include("Spieler1:")
      }
    }

    "handling answer input" should {
      "accept valid input within range" in {
        val input = new ByteArrayInputStream("2\n".getBytes)
        Console.withIn(input) {
          tui.Antwort(3) should be(2)
        }
      }

      "reject invalid numeric input" in {
        val input = new ByteArrayInputStream("5\n2\n".getBytes)
        val output = new ByteArrayOutputStream()
        Console.withIn(input) {
          Console.withOut(output) {
            tui.Antwort(3) should be(2)
          }
        }
        output.toString should include("Falsche Eingabe")
      }

      "handle non-numeric input" in {
        val input = new ByteArrayInputStream("abc\n2\n".getBytes)
        val output = new ByteArrayOutputStream()
        Console.withIn(input) {
          Console.withOut(output) {
            tui.Antwort(3) should be(2)
          }
        }
        output.toString should include("Keine Zahl")
      }

      "handle empty input" in {
        val input = new ByteArrayInputStream("\n2\n".getBytes)
        val output = new ByteArrayOutputStream()
        Console.withIn(input) {
          Console.withOut(output) {
            tui.Antwort(3) should be(2)
          }
        }
        output.toString should include("Falsche Eingabe")
      }
    }

    "displaying game status messages" should {
      "show welcome message" in {
        val output = new ByteArrayOutputStream()
        Console.withOut(output) {
          tui.Startansicht()
        }
        output.toString should include("Willkommen bei Quixx")
        output.toString should include("Erstelle alle Spieler")
      }

      "show not enough players message" in {
        val output = new ByteArrayOutputStream()
        Console.withOut(output) {
          tui.zuwenigspieler()
        }
        output.toString should include("mindenstens 2 Spieler")
      }

      "show round beginning" in {
        val output = new ByteArrayOutputStream()
        Console.withOut(output) {
          tui.Rundenbeginn(1, "TestPlayer")
        }
        output.toString should include("Runde 1")
        output.toString should include("TestPlayer")
      }

      "show end game message" in {
        val output = new ByteArrayOutputStream()
        Console.withOut(output) {
          tui.ende(testSpieler, 10)
        }
        output.toString should include("gewinnt mit 10 Punkten")
        output.toString should include("TestPlayer")
      }
    }

    "displaying dice rolls" should {
      "format wurf correctly with all numbers" in {
        val testWurf = Array(1, 2, 3, 4, 5, 6)
        val wurf = tui.Wurf(testWurf)
        wurf should include("Wurf:")
        wurf should include(tui.red + "3")
        wurf should include(tui.yellow + "4")
        wurf should include(tui.green + "5")
        wurf should include(tui.blue + "6")
        wurf should include(tui.white)
      }
    }

    "displaying options" should {
      "format Option1 correctly with all combinations" in {
        val testComb = Array(2, 3, 4, 5)
        val options = tui.Option1(testComb)
        options should include("[0] F")
        options should include("[1] N")
        options should include(tui.red + "2")
        options should include(tui.yellow + "3")
        options should include(tui.green + "4")
        options should include(tui.blue + "5")
      }

      "format Option1 correctly with some zero values" in {
        val testComb = Array(2, 0, 4, 0)
        val options = tui.Option1(testComb)
        options should include("[0] F")
        options should include("[1] N")
        options should include(tui.red + "2")
        options should include(tui.green + "4")
        options should not include(tui.yellow)
        options should not include(tui.blue)
      }

      "format Option2 correctly with all combinations" in {
        val testComb = Array(2, 3, 4, 5, 6, 7, 8, 9)
        val options = tui.Option2(testComb)
        options should include("[0] F")
        options should include(tui.red + "2")
        options should include(tui.yellow + "4")
        options should include(tui.green + "6")
        options should include(tui.blue + "8")
      }

      "format Option2 correctly with some zero values" in {
        val testComb = Array(2, 0, 4, 0, 6, 0, 8, 0)
        val options = tui.Option2(testComb)
        options should include("[0] F")
        options should include(tui.red + "2")
        options should include(tui.yellow + "4")
        options should include(tui.green + "6")
        options should include(tui.blue + "8")
      }
    }

    "displaying game field" should {
      "show correct field layout for empty field" in {
        val field = tui.Feld(testSpieler)
        field should include(tui.mesh)
        field should include(tui.red)
        field should include(tui.yellow)
        field should include(tui.green)
        field should include(tui.blue)
        field should include("FEHLWURF")
      }

      "handle crosses in red line correctly" in {
        val spieler = new Spieler("TestPlayer")
        spieler.Feld.Red.Kreuze(0) = true
        spieler.Feld.Red.Kreuze(11) = true
        val field = tui.Feld(spieler)
        field should include(tui.white + "X" + tui.red)
      }

      "handle crosses in yellow line correctly" in {
        val spieler = new Spieler("TestPlayer")
        spieler.Feld.Yellow.Kreuze(5) = true
        spieler.Feld.Yellow.Kreuze(11) = true
        val field = tui.Feld(spieler)
        field should include("|  " + tui.white + "X" + tui.yellow)
      }

      "handle crosses in green line correctly" in {
        val spieler = new Spieler("TestPlayer")
        spieler.Feld.Green.Kreuze(3) = true
        spieler.Feld.Green.Kreuze(11) = true
        val field = tui.Feld(spieler)
        field should include("|  " + tui.white + "X" + tui.green)
      }

      "handle crosses in blue line correctly" in {
        val spieler = new Spieler("TestPlayer")
        spieler.Feld.Blue.Kreuze(7) = true
        spieler.Feld.Blue.Kreuze(11) = true
        val field = tui.Feld(spieler)
        field should include("|  " + tui.white + "X" + tui.blue)
      }

      "display fails correctly" in {
        val spieler = new Spieler("TestPlayer")
        spieler.Feld.fwcount = 2
        val field = tui.Feld(spieler)
        field should include(" [X] [X] [ ] [ ]")
      }
    }
  }
}
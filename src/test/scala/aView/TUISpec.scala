import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import Model.Spieler
import Model.Reihe

class TUISpec extends AnyWordSpec with Matchers {

  "A TUI" should {

    "initialize correctly" in {
      val tui = new aView.TUI
      noException should be thrownBy tui.Startansicht()
    }

    "handle too few players correctly" in {
      val tui = new aView.TUI
      noException should be thrownBy tui.zuwenigspieler()
    }

    "read player input correctly" in {
      val tui = new aView.TUI
      val readLineStub = () => "Player1"
      val player = tui.Spielereinlesen(0)
      player.name shouldEqual "Player1"
    }

    "end the game correctly" in {
      val tui = new aView.TUI
      val player = new Spieler("Winner")
      noException should be thrownBy tui.ende(player, 100)
    }

    "start a round correctly" in {
      val tui = new aView.TUI
      noException should be thrownBy tui.Rundenbeginn(1, "Player1")
    }

    "handle valid and invalid answers correctly" in {
      val tui = new aView.TUI
      val readLineStub = () => "2"
      tui.Antwort(2) shouldEqual 2
      val readLineStubInvalid = () => "invalid"
      tui.Antwort(2) shouldEqual 1
    }

    "display the roll correctly" in {
      val tui = new aView.TUI
      val roll = Array(1, 2, 3, 4, 5, 6)
      tui.Wurf(roll) should include ("Wurf: 1 2 3 4 5 6")
    }

    "display options correctly" in {
      val tui = new aView.TUI
      val comb = Array(1, 0, 2, 0, 3, 0, 4, 0)
      tui.Option1(comb) should include ("[1] 1 [2] 2 [3] 3 [4] 4")
      tui.Option2(comb) should include ("[1] 1 [2] 2 [3] 3 [4] 4")
    }

    "display the field correctly" in {
      val tui = new aView.TUI
      val player = new Spieler("Player1")
      noException should be thrownBy tui.Feld(player)
    }

    "write lines correctly" in {
      val tui = new aView.TUI
      val reihe = new Reihe(12)
      noException should be thrownBy tui.writeRedLine(reihe)
      noException should be thrownBy tui.writeYellowLine(reihe)
      noException should be thrownBy tui.writeGreenLine(reihe)
      noException should be thrownBy tui.writeBlueLine(reihe)
    }
  }
}
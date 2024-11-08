package Controller

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import scala.collection.mutable.ArrayBuffer
import Model.Spieler
import aView.TUI

class StartSpec extends AnyWordSpec with Matchers {

  "A Start" should {

    "initialize the game and add players correctly" in {
      // TestTUI erstellt, um simulierte Spieler-Eingaben bereitzustellen
      val testTUI = new TestTUI(Seq("Alice", "Bob", ""))
      val start = new Start {
        override val TUI: TUI = testTUI  // TestTUI statt der echten TUI
      }

      val result = start.Start()

      // Überprüfen, dass die richtige Anzahl von Spielern zurückgegeben wird
      result.length should be(2)
      result.map(_.name) should contain theSameElementsAs Seq("Alice", "Bob")

      // Sicherstellen, dass die Methode `zuwenigspieler` nicht aufgerufen wurde
      testTUI.zuwenigspielerCalled should be(false)
    }

    "require at least two players" in {
      val testTUI = new TestTUI(Seq("Alice", ""))
      val start = new Start {
        override val TUI: TUI = testTUI
      }

      start.Start()

      // Überprüfen, dass `zuwenigspieler` aufgerufen wird, wenn nur ein Spieler eingegeben wurde
      testTUI.zuwenigspielerCalled should be(true)
    }

    "call Startansicht once at the beginning" in {
      val testTUI = new TestTUI(Seq("Alice", "Bob", ""))
      val start = new Start {
        override val TUI: TUI = testTUI
      }

      start.Start()

      // Überprüfen, dass `Startansicht` genau einmal aufgerufen wurde
      testTUI.startansichtCalled should be(true)
    }
  }

  // TestTUI: Eine Test-spezifische Unterklasse von TUI, um Eingaben und Aufrufe zu steuern
  class TestTUI(inputs: Seq[String]) extends TUI {
    private var inputIndex = 0
    var zuwenigspielerCalled = false
    var startansichtCalled = false

    // Überschreiben der Startansicht-Methode, um den Aufruf zu protokollieren
    override def Startansicht(): Unit = {
      startansichtCalled = true
    }

    // Überschreiben der zuwenigspieler-Methode, um den Aufruf zu protokollieren
    override def zuwenigspieler(): Unit = {
      zuwenigspielerCalled = true
    }

    // Überschreiben der Spielereinlesen-Methode, um simulierte Eingaben zu liefern
    override def Spielereinlesen(c: Int): Spieler = {
      if (inputIndex < inputs.length) {
        val name = inputs(inputIndex)
        inputIndex += 1
        new Spieler(name)
      } else {
        new Spieler("") // Leere Eingabe, falls die Liste der simulierten Eingaben endet
      }
    }
  }
}

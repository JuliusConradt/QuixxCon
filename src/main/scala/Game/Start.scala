package Game

import scala.collection.mutable.ArrayBuffer

class Start {
  val TUI = new View.TUI
  val Spielerlistedynamic: ArrayBuffer[Teilnehmer.Spieler] = ArrayBuffer()

  def Start():Array[Teilnehmer.Spieler] = {
    Startansicht()
    var Eingabe:Teilnehmer.Spieler = Spielereinlesen(Spielerlistedynamic.length)
    while (Eingabe.name != "" || Spielerlistedynamic.length < 2) {
      if (Eingabe.name != "") {
        Spielerlistedynamic += Eingabe
      } else if (Eingabe.name == "")
        zuwenigspieler()
      Eingabe = Spielereinlesen(Spielerlistedynamic.length)
    }
    val SpielerListe : Array[Teilnehmer.Spieler] = Spielerlistedynamic.toArray
    SpielerListe
  }

  def Startansicht():Unit = {
    TUI.Startansicht()
    //GUI Startansicht()
  }

  def zuwenigspieler():Unit = {
    TUI.zuwenigspieler()
  }

  def Spielereinlesen(c:Int):(Teilnehmer.Spieler) = {
    TUI.Spielereinlesen(c)
  }

}

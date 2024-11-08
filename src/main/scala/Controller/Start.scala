package Controller

import scala.collection.mutable.ArrayBuffer

class Start {
  val TUI = new aView.TUI
  val Spielerlistedynamic: ArrayBuffer[Model.Spieler] = ArrayBuffer()

  def Start():Array[Model.Spieler] = {
    Startansicht()
    var Eingabe:Model.Spieler = Spielereinlesen(Spielerlistedynamic.length)
    while (Eingabe.name != "" || Spielerlistedynamic.length < 2) {
      if (Eingabe.name != "") {
        Spielerlistedynamic += Eingabe
      } else if (Eingabe.name == "")
        zuwenigspieler()
      Eingabe = Spielereinlesen(Spielerlistedynamic.length)
    }
    val SpielerListe : Array[Model.Spieler] = Spielerlistedynamic.toArray
    SpielerListe
  }

  def Startansicht():Unit = {
    TUI.Startansicht()
    //GUI Startansicht()
  }

  def zuwenigspieler():Unit = {
    TUI.zuwenigspieler()
  }

  def Spielereinlesen(c:Int):(Model.Spieler) = {
    TUI.Spielereinlesen(c)
  }

}

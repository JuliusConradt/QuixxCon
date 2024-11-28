package BController

import model.Spieler

import scala.collection.mutable.ArrayBuffer

class Start {
  val TUI = new BView.TUI
  val Spielerlistedynamic: ArrayBuffer[Spieler] = ArrayBuffer()

  def Start():Array[Spieler] = {
    Startansicht()
    var Eingabe:Spieler = Spielereinlesen(Spielerlistedynamic.length)
    while (Eingabe.name != "" || Spielerlistedynamic.length < 2) {
      if (Eingabe.name != "") {
        Spielerlistedynamic += Eingabe
      } else if (Eingabe.name == "")
        zuwenigspieler()
      Eingabe = Spielereinlesen(Spielerlistedynamic.length)
    }
    val SpielerListe : Array[Spieler] = Spielerlistedynamic.toArray
    SpielerListe
  }

  def Startansicht():Unit = {
    TUI.Startansicht()
    //GUI Startansicht()
  }

  def zuwenigspieler():Unit = {
    TUI.zuwenigspieler()
  }

  def Spielereinlesen(c:Int):(Spieler) = {
    TUI.Spielereinlesen(c)
  }

}

package Game

import View.TUI

class Spiel(val Spieler:Array[Teilnehmer.Spieler]){

  val TUI = new TUI

  var Runde = 0
  var Master = -1
  var User = Master
  var rclosed = false
  var yclosed = false
  var gclosed = false
  var bclosed = false
  var beendet = false

  def Spiel():Unit = {
    var continue = true
    while (continue) {
      continue = Round(Runde+1, Spieler(Master+1))
    }

    def Round(Nr: Int, p: Teilnehmer.Spieler):Boolean = {
      Rundenbeginn(Nr,p.name)
      false
    }

  }

  def Rundenbeginn(Nr:Int, name:String):Unit =
    TUI.Rundenbeginn(Nr, name)

}

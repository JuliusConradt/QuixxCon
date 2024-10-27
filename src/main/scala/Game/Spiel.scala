package Game

import View.TUI

class Spiel(val Spieler:Array[Teilnehmer.Spieler]){

  val TUI = new TUI
  val Wuerfel = new Wuerfel

  var Runde = 0
  var Master = -1
  var User = Master
  var rowsclosed = 0
  var rclosed = false
  var yclosed = false
  var gclosed = false
  var bclosed = false
  var beendet = false

  def Spiel():Unit = {
    var continue = true
    while (continue) { //continue = beendet --> Später also !continue
      continue = Round(Runde+1, Spieler(Master+1))
    }

    def Round(Nr: Int, p: Teilnehmer.Spieler):Boolean = {
      val Wurf = Wuerfel.wuerfeln()
      Rundenbeginn(Nr,p.name)
      Feld(p)
      WurfAusgeben(Wurf)
      Option1(Wurf,p)
      //Antwort abfragen

      if (p.Feld.fwcount == 4 || rowsclosed == 2) beendet = true //Spielende
      beendet
    }

  }

  def Rundenbeginn(Nr:Int, name:String):Unit =
    TUI.Rundenbeginn(Nr, name)

  def Feld(p: Teilnehmer.Spieler):Unit =
    println(TUI.Feld(p))

  def WurfAusgeben(w: Array[Int]):Unit =
    println(TUI.Wurf(w))

  def Option1(w: Array[Int],p: Teilnehmer.Spieler):Array[Int] = {
    val sum = w(0)+w(1)
    val comb = Array.fill(4)(0)
    for (i <- 1 to 4) {
      comb(i-1) = verfuegbar(p,i,sum)
    }
    println(TUI.Option1(comb))
    comb
  }

  def verfuegbar(p: Teilnehmer.Spieler, typ: Int, sum: Int):Int = {
    for (i <- (sum-2) to 10) {
      val result = typ match {
        case 1 => p.Feld.Red.Kreuze(i)
        case 2 => p.Feld.Yellow.Kreuze(i)
        case 3 => p.Feld.Green.Kreuze(i)
        case 4 => p.Feld.Blue.Kreuze(i)
      }
      if(result) return 0
    }
    sum
  }
}

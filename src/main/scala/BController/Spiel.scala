package BController

import BView.TUI
import model.{Spieler, Wuerfel}

import scala.util.control.Breaks._

class Spiel(val Spieler:Array[Spieler]){

  val TUI = new TUI
  var Runde = 0
  var Master = -1
  var User = Master
  var rowsclosed = 0
  var rclosed = false
  var yclosed = false
  var gclosed = false
  var bclosed = false
  var beendet = false

  def Spielstart():Array [Spieler] = {
    var continue = false
    while (!continue) { //continue = beendet --> Später also !continue
      Runde += 1
      Master = (Master+1) % Spieler.length
      User = Master
      continue = Round(Runde, Spieler(Master))
    }

    def Round(Nr: Int, p: Spieler):Boolean = {
      val Wurf = Wuerfel.wuerfeln()
      Rundenbeginn(Nr,p.name)
      Feld(p)
      WurfAusgeben(Wurf)
      val comb = Option1(Wurf,p)
      val Anzahl = comb.count(_ != 0)
      val a = Antwort(Anzahl)
      a match {
        case 0 => p.Feld.fwcount += 1
        case 1 => {
          Feld(p)
          WurfAusgeben(Wurf)
          Auswertung(Option2(Wurf,p),p, true)
        }
        case _ => {
          var count = 0
          var location = 1
          breakable {
            for (i <- 0 to 3) {
              if (comb(i) != 0)
                count += 1
              if (count + 1 == (a)) {
                location = i
                break
              }
            }
          }
          Ankreuzen(p,comb(location),location+1)
          Feld(p)
          WurfAusgeben(Wurf)
          Auswertung(Option2(Wurf,p),p, false)
        }
      }
      User = (User + 1) % Spieler.length
      while (User != Master) {
        SubRound(Spieler(User), Wurf, Nr)
        User = (User + 1) % Spieler.length
      }

      if (p.Feld.fwcount == 4 || rowsclosed == 2) beendet = true //Spielende
      beendet
    }
    Spieler
  }


  def SubRound(p : Spieler, w: Array[Int], Nr: Int):Unit = {
    Rundenbeginn(Nr, p.name)
    Feld(p)
    Auswertung(Option2(w,p),p, false)
  }
  def Auswertung(comb: Array[Int], p :Spieler, b: Boolean): Unit = {
    val Anzahl = comb.count(_ != 0)
    val a = Antwort(Anzahl)
    a match {
      case 0 => if (b) p.Feld.fwcount += 1
      case _ => {
        var count = 0
        var location = 0
        breakable {
          for (i <- 0 to 7) {
            if (comb(i) != 0)
              count += 1
            if (count == (a)) {
              location = i
              break
            }
          }
        }
        Ankreuzen(p, comb(location), (location/2) + 1)
      }
    }
  }

  def Ankreuzen(p: Spieler, n: Int, f: Int):Unit = {
    f match {
      case 1 => {
        if(n==12 && p.Feld.Red.count >= 5) {
          p.Feld.Red.Kreuze(11) = true
          p.Feld.Red.count += 1
          rclosed = true
          rowsclosed += 1
        }
        p.Feld.Red.Kreuze(n-2) = true
        p.Feld.Red.count += 1
      }
      case 2 => {
        if(n==12 && p.Feld.Yellow.count >= 5) {
          p.Feld.Yellow.Kreuze(11) = true
          p.Feld.Yellow.count += 1
          yclosed = true
          rowsclosed += 1
        }
        p.Feld.Yellow.Kreuze(n-2) = true
        p.Feld.Yellow.count += 1
      }
      case 3 => {
        if(n==2 && p.Feld.Green.count >= 5) {
          p.Feld.Green.Kreuze(11) = true
          p.Feld.Green.count += 1
          gclosed = true
          rowsclosed += 1
        }
        p.Feld.Green.Kreuze(12-n) = true
        p.Feld.Green.count += 1
      }
      case 4 => {
        if(n==2 && p.Feld.Blue.count >= 5) {
          p.Feld.Blue.Kreuze(11) = true
          p.Feld.Blue.count += 1
          bclosed = true
          rowsclosed += 1
        }
        p.Feld.Blue.Kreuze(12-n) = true
        p.Feld.Blue.count += 1
      }
    }
  }
  def Antwort(a: Int):Int = {
    TUI.Antwort(a)
  }

  def Feld(p: Spieler): Unit = {
    println(TUI.Feld(p))
  }
  def Rundenbeginn(Nr:Int, name:String):Unit =
    TUI.Rundenbeginn(Nr, name)

  def WurfAusgeben(w: Array[Int]):Unit =
    println(TUI.Wurf(w))

  def Option2(w: Array[Int], p: Spieler):Array[Int] = {
    val comb = Array.fill(8)(0)
    comb(0) = verfuegbar(p,1,(w(0)+w(2)))
    comb(1) = verfuegbar(p,1,(w(1)+w(2)))
    comb(2) = verfuegbar(p,2,(w(0)+w(3)))
    comb(3) = verfuegbar(p,2,(w(1)+w(3)))
    comb(4) = verfuegbar(p,3,(w(0)+w(4)))
    comb(5) = verfuegbar(p,3,(w(1)+w(4)))
    comb(6) = verfuegbar(p,4,(w(0)+w(5)))
    comb(7) = verfuegbar(p,4,(w(1)+w(5)))
//    for (i <- 0 to 7) {
//      comb(i) = verfuegbar(p,(i/2)+1,(w(i%2)+w((i/2)+2)))
//    }
    println(TUI.Option2(comb))
    comb
  }
  def Option1(w: Array[Int],p: Spieler):Array[Int] = {
    val sum = w(0)+w(1)
    val comb = Array.fill(4)(0)
    for (i <- 1 to 4) {
      comb(i-1) = verfuegbar(p,i,sum)
    }
    println(TUI.Option1(comb))
    comb
  }

  def verfuegbar(p: Spieler, typ: Int, sum: Int):Int = {
    var ret = sum
    var result = false
    if (typ < 3) {
      for (i <- (sum - 2) to 10) {
        result = typ match {
          case 1 => {
            var b = p.Feld.Red.Kreuze(i)
            if (rclosed) b = true
            b
          }
          case 2 => {
            var b = p.Feld.Yellow.Kreuze(i)
            if (yclosed) b = true
            b
          }
        }
        if (result) return 0
      }
    } else {
      for (i <-(12 - sum) to 10) {
        result = typ match {
          case 3 => {
            var b = p.Feld.Green.Kreuze(i)
            if (gclosed) b = true
            b
          }
          case 4 => {
            var b = p.Feld.Blue.Kreuze(i)
            if (bclosed) b = true
            b
          }
        }
        if (result) return 0
      }
    }
    ret
  }
}

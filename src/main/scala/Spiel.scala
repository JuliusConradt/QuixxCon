class Spiel(val Spieler: Array[Spieler]) {

  var Runde = 0
  var Master = -1
  var User = Master
  var rclosed = false
  var yclosed = false
  var gclosed = false
  var bclosed = false
  var beendet = false

  def CanBeClosed(r: Reihe):Boolean = {
    val alreadyclosed = r.Typ match {
      case 1 => rclosed
      case 2 => yclosed
      case 3 => gclosed
      case 4 => bclosed
    }
    if (alreadyclosed) return false
    r.count > 4
  }

  def nextRound():Unit = {
    User = Master
    Runde += 1
    Master = (Master + 1) % Spieler.length
    TUI.printFieldTUI(Spieler(User),this)
    val Wuerfel = new Wuerfel
    var masterchoice = TUI.printWurfTUI(Wuerfel)
    if(masterchoice == 0){
      Spieler(User).Feld.fwcount += 1
      //Next field
    } else if (masterchoice == 1) {
      //Farbige Kombinationen ausgeben
    } else {
      //Zum Spielfeld addieren
    }

  }

  def Ankreuzen(r: Reihe, wert: Int):Reihe = {
    if (r.Typ == 1 || r.Typ == 2){
      r.Kreuze(wert - 2) = true
      r.count += 1
      return r
    }
    r.Kreuze(12 - wert) = true
    r.count += 1
    r
  }

  def Punktzahl(rcount:Int, ycount:Int, gcount:Int, bcount:Int, fwcount:Int):Int = {
    SubPunkte(rcount) + SubPunkte(ycount) + SubPunkte(gcount) + SubPunkte(bcount) - 5*fwcount
  }

  def SubPunkte(count:Int):Int = {
    count match {
      case 0 => 0
      case 1 => 1
      case 2 => 3
      case 3 => 6
      case 4 => 10
      case 5 => 15
      case 6 => 21
      case 7 => 28
      case 8 => 36
      case 9 => 45
      case 10 => 55
      case 11 => 66
      case 12 => 78
    }
  }
}

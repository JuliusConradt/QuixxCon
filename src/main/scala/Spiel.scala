class Spiel(val Spieler: Array[Spieler]) {

  var Runde = 1
  var Master = 0 //Spieler der gewürfelt hat --> Spieler(Master)
  val rclosed = false
  val yclosed = false
  val gclosed = false
  val bclosed = false

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

  def NextRound():Unit = {
    Runde += 1
    Master = (Master + 1) % Spieler.size
    //Spielfeld von Master
    //Würfeln
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

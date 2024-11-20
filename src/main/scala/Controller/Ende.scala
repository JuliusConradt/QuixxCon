package Controller

import Controller.{Start, Ende, Spiel}
class Ende {
  val TUI = new aView.TUI


  def ende(p: Array[Model.Spieler]):Unit = {
    var top = 0
    var winner = 0
    for (i <- 0 until p.length){
      var sum = p(i).Feld.fwcount * (-5)
      sum += Reihenpunkte(p(i).Feld.Red.count)
      sum += Reihenpunkte(p(i).Feld.Yellow.count)
      sum += Reihenpunkte(p(i).Feld.Green.count)
      sum += Reihenpunkte(p(i).Feld.Blue.count)
      if (sum > top) {
        top = sum
        winner = i
      }
    }
    TUI.ende(p(winner),top)
  }

  def Reihenpunkte (n: Int):Int = {
    n match {
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

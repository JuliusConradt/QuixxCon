class Regeln {
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

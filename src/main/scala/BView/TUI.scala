package BView
import scala.io.StdIn
import scala.math
import BObserver.Observer
import model.{Reihe, Spieler}

class TUI extends Observer {

  val red = "\u001B[31m"
  val yellow = "\u001B[33m"
  val green = "\u001B[32m"
  val blue = "\u001B[34m"
  val white = "\u001B[0m"
  val mesh = ("+" + ("-") * 5) * 12 + "+"
  val ryline = "|  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  | 10  | 11  | 12  | [ ] |"
  val gbline = "| 12  | 11  | 10  |  9  |  8  |  7  |  6  |  5  |  4  |  3  |  2  | [ ] |"
  val fails = "FEHLWURF [ ] [ ] [ ] [ ]"

  def Startansicht():Unit = {
    println("Willkommen bei Quixx")
    println("Erstelle alle Spieler. Beende deine Eingabe mit einem leeren Spieler")
  }

  def zuwenigspieler():Unit =
    println("Erstelle mindenstens 2 Spieler")

  def Spielereinlesen(c:Int): Spieler = {
    println("Spieler" + (c+1) + ":")
    val Spieler = new Spieler(StdIn.readLine())
    Spieler
  }

  def ende(winner: Spieler, points: Int): Unit = {
    println("\n" + "--- " + winner.name + " gewinnt mit " + points + " Punkten ---")
    println(Feld(winner))
  }

  def Rundenbeginn(Nr: Int, name: String): Unit =
    println("\nRunde " + Nr + " | Feld von Spieler: " + name)

//  def Antwort(a: Int):Int = {
//    var in = StdIn.readInt()
//    while (in > a+1) {
//      println("Falsche Eingabe")
//      in = StdIn.readInt()
//    }
//    in
//  }

  def Antwort(a:Int):Int = {
    var in = StdIn.readLine()
    try {
    while (in.trim.isEmpty || in.toInt > a+1) {
      println("Falsche Eingabe")
      in = StdIn.readLine()
    }
      val out = in.toInt
      out
    } catch {
      case _: NumberFormatException =>
        println("Keine Zahl")
        Antwort(a)
    }
  }

  def Wurf(w: Array[Int]):String = {
    val sb = new StringBuilder()
    sb.append(white + "Wurf: ")
    sb.append(w(0) + " " +
      w(1) + " " +
      red + w(2) + " " +
      yellow + w(3) + " " +
      green + w(4) + " " +
      blue + w(5) + white)
    sb.append("\n")
    sb.toString()
  }

  def Option2(comb: Array[Int]):String = {
    val sb = new StringBuilder()
    sb.append("[0] F ")
    var option = 0
    for (i <- 0 to 7){
      if (math.signum(comb(i)) > 0) {
        option += 1
        sb.append("["+option+"] ")
        val farbe = i match {
          case 0|1 => red
          case 2|3 => yellow
          case 4|5 => green
          case 6|7 => blue
        }
        sb.append(farbe+comb(i)+white+" ")
      }
    }
    sb.append(white+"\n")
    sb.toString()
  }

  def Option1(comb: Array[Int]):String = {
    val sb = new StringBuilder()
    sb.append("[0] F [1] N ")
    var option = 1
    for (i <- 0 to 3) {
      if (math.signum(comb(i)) > 0) {
        option += 1
        sb.append("["+option+"] ")
        val farbe = i match {
          case 0 => red
          case 1 => yellow
          case 2 => green
          case 3 => blue
        }
        sb.append(farbe+comb(i)+white+" ")
      }
    }
    sb.append(white+"\n")
    sb.toString()
  }

  def Feld(p: Spieler):String = {
    val sb = new StringBuilder()
    sb.append(red + mesh + "\n")
    sb.append(writeRedLine(p.Feld.Red))
    sb.append(mesh + "\n")
    sb.append(yellow + mesh + "\n")
    sb.append(writeYellowLine(p.Feld.Yellow))
    sb.append(mesh + "\n")
    sb.append(green + mesh + "\n")
    sb.append(writeGreenLine(p.Feld.Green))
    sb.append(mesh + "\n")
    sb.append(blue + mesh + "\n")
    sb.append(writeBlueLine(p.Feld.Blue))
    sb.append(mesh + "\n")
    sb.append(white + fails.substring(0,8))
    val fw = " [X]" * p.Feld.fwcount
    val nfw = " [ ]" * (4 - p.Feld.fwcount)
    sb.append(fw + nfw)
    sb.toString()
  }

  def writeRedLine(r:Reihe):String = {
    val sb = new StringBuilder()
    for (i <- 0 to 10){
      if(r.Kreuze(i)) sb.append("|  "+white+"X"+red+"  ")
      else sb.append(ryline.substring(i*6,i*6+6))
    }
    sb.append("| [")
    if (r.Kreuze(11)) sb.append(white+"X"+red)
    else sb.append(" ")
    sb.append("] |\n")
    sb.toString()
  }

  def writeYellowLine(r:Reihe):String = {
    val sb = new StringBuilder()
    for (i <- 0 to 10){
      if(r.Kreuze(i)) sb.append("|  "+white+"X"+yellow+"  ")
      else sb.append(ryline.substring(i*6,i*6+6))
    }
    sb.append("| [")
    if (r.Kreuze(11)) sb.append(white+"X"+yellow)
    else sb.append(" ")
    sb.append("] |\n")
    sb.toString()
  }

  def writeGreenLine(r:Reihe):String = {
    val sb = new StringBuilder()
    for (i <- 0 to 10){
      if(r.Kreuze(i)) sb.append("|  "+white+"X"+green+"  ")
      else sb.append(gbline.substring(i*6,i*6+6))
    }
    sb.append("| [")
    if (r.Kreuze(11)) sb.append(white+"X"+green)
    else sb.append(" ")
    sb.append("] |\n")
    sb.toString()
  }

  def writeBlueLine(r:Reihe):String = {
    val sb = new StringBuilder()
    for (i <- 0 to 10){
      if(r.Kreuze(i)) sb.append("|  "+white+"X"+blue+"  ")
      else sb.append(gbline.substring(i*6,i*6+6))
    }
    sb.append("| [")
    if (r.Kreuze(11)) sb.append(white+"X"+blue)
    else sb.append(" cove ")
    sb.append("] |\n")
    sb.toString()
  }

  override def update(p:Spieler): Unit = {
    Feld(p)
  }
}

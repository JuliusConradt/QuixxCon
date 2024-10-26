class Feldprint {
  val red = "\u001B[31m"
  val yellow = "\u001B[33m"
  val green = "\u001B[32m"
  val blue = "\u001B[34m"
  val white = "\u001B[0m"
  val mesh = ("+" + ("-") * 5) * 12 + "+"
  val ryline = "|  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  | 10  | 11  | 12  | [ ] |"
  val gbline = "| 12  | 11  | 10  |  9  |  8  |  7  |  6  |  5  |  4  |  3  |  2  | [ ] |"
  val fails = "FEHLWURF [ ] [ ] [ ] [ ]"

  def emptyfield(): String = { //gibt ein leeres Spielfeld aus
    val sb = new StringBuilder()
    sb.append("Welcome to Quixx \n")
    sb.append(red + mesh +"\n" + ryline + "\n" + mesh + "\n")
    sb.append(yellow + mesh + "\n" + ryline + "\n" + mesh + "\n")
    sb.append(green + mesh + "\n" + gbline + "\n" + mesh + "\n")
    sb.append(blue + mesh + "\n" + gbline + "\n" + mesh + "\n")
    sb.append(white + fails + "\n")
    sb.toString()
  }

  //FELD PRINTEN:
  //mesh
  //durch erste Reihe iterieren
  //Für jedes true den betroffenen Teil im ryline mit einem x replacen
  //reihe printen
  //

  def fieldprint(p: Spieler, s:Spiel):String = {
    val sb = new StringBuilder()
    sb.append(white + "Spielfeld von Spieler " + p.name + "  |  Runde: " + s.Runde + "\n")
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
    sb.append(fails.substring(0,7))
    val fw = " [X]" * p.Feld.fwcount
    val nfw = " [ ]" * (4 - p.Feld.fwcount)
    sb.append(fw + nfw +"\n")
    sb.toString()
  }

  def writeRedLine(r:Reihe):String = {
    val sb = new StringBuilder()
    for (i <- 0 to 10){
      if(r.Kreuze(i)) sb.append("|  "+white+"X"+red+"  ")
      else sb.append(ryline.substring(i*6,i*6+5))
    }
    sb.append("| [")
    if (r.Kreuze(11)) sb.append(white+"X"+red)
    else sb.append(" ")
    sb.append(" |\n")
    sb.toString()
  }

  def writeYellowLine(r:Reihe):String = {
    val sb = new StringBuilder()
    for (i <- 0 to 10){
      if(r.Kreuze(i)) sb.append("|  "+white+"X"+yellow+"  ")
      else sb.append(ryline.substring(i*6,i*6+5))
    }
    sb.append("| [")
    if (r.Kreuze(11)) sb.append(white+"X"+yellow)
    else sb.append(" ")
    sb.append(" |\n")
    sb.toString()
  }

  def writeGreenLine(r:Reihe):String = {
    val sb = new StringBuilder()
    for (i <- 0 to 10){
      if(r.Kreuze(i)) sb.append("|  "+white+"X"+green+"  ")
      else sb.append(gbline.substring(i*6,i*6+5))
    }
    sb.append("| [")
    if (r.Kreuze(11)) sb.append(white+"X"+green)
    else sb.append(" ")
    sb.append(" |\n")
    sb.toString()
  }

  def writeBlueLine(r:Reihe):String = {
    val sb = new StringBuilder()
    for (i <- 0 to 10){
      if(r.Kreuze(i)) sb.append("|  "+white+"X"+blue+"  ")
      else sb.append(gbline.substring(i*6,i*6+5))
    }
    sb.append("| [")
    if (r.Kreuze(11)) sb.append(white+"X"+blue)
    else sb.append(" ")
    sb.append(" |\n")
    sb.toString()
  }
}
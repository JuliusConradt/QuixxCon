package view
import model.Spieler
import model.StatusManager
import scala.io.StdIn
import scala.collection.mutable.ArrayBuffer

class TUI extends View{

  val red = "\u001B[31m"
  val yellow = "\u001B[33m"
  val green = "\u001B[32m"
  val blue = "\u001B[34m"
  val white = "\u001B[0m"
  val mesh = ("+" + "-" * 5) * 12 + "+"
  val ryline = "|  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  | 10  | 11  | 12  | [ ] |"
  val gbline = "| 12  | 11  | 10  |  9  |  8  |  7  |  6  |  5  |  4  |  3  |  2  | [ ] |"
  val fails = "FEHLWURF [ ] [ ] [ ] [ ]"

  def status():model.Status = StatusManager.getStatus

  override def update(): Unit = {
    val Status = status()
    field()
    waitforaction(aktion())
  }

  override def start(): Array[Spieler] = {
    willkommen()
    val dynamiclist: ArrayBuffer[Spieler] = ArrayBuffer()
    var Eingabe:Spieler = spielereinlesen(dynamiclist.length)
    while (Eingabe.name != "" || dynamiclist.length < 2) {
      if (Eingabe.name != "") {
        dynamiclist += Eingabe
      } else {
        println("Erstelle mindestens 2 Spieler!")
      }
      Eingabe = spielereinlesen(dynamiclist.length)
    }
    dynamiclist.toArray
  }

  def spielereinlesen(c: Int): Spieler = {
    println("Spieler" + (c+1) + ":")
    Spieler(StdIn.readLine())
  }

  def willkommen(): Unit = {
    println("Willkommen bei Quixx")
    println("Erstelle alle Spieler. Beende deine Eingabe mit einem leeren Spieler")
  }


  def waitforaction(options: Array[Int]): Unit = {
    //Funktion um korrekten Char einzulesen
    def readchar(): Char =
      val eingabe = StdIn.readChar()
      if (!options.contains(eingabe))
        println("Ungültige Eingabe")
        readchar()
      else eingabe

    val option = readchar().toInt - 98
    val cont = controller.Controller(0)
    val Status = status()

    if (Status.whitedice && Status.colordice)
      if (option < 0)
        cont.fehlwurf()
        StatusManager.setStatus(Status.copy(colordice=false))
      else if (option < 4)
        cont.ankreuzen(option)
        StatusManager.setStatus(Status.copy(User = Status.User -1, whitedice=false, same= true))
        
      else
        StatusManager.setStatus(Status.copy(colordice=false))
        cont.ankreuzen(option)
    else if (Status.whitedice && !Status.colordice)
      if (option >= 0)
        cont.ankreuzen(option)
    else if (!Status.whitedice && Status.colordice)
      if (option < 0)
        StatusManager.setStatus(Status.copy(colordice=false, same=false))
      else if (option > 4)
        StatusManager.setStatus(Status.copy(colordice=false, same=false))
        cont.ankreuzen(option)
  }

  def color(i: Int): String = {
    i match {
      case 0 => red
      case 1 => yellow
      case 2 => green
      case 3 => blue
    }
  }
  def aktion(): Array[Int] = {
    val sb = new StringBuilder()
    val cont = controller.Controller(0)
    val comb = cont.comb()
    val whitedice = StatusManager.getStatus.whitedice
    val colordice = StatusManager.getStatus.colordice
    println(white + "___________________________________AKTION________________________________")
    println("|             |                      |                                  |")
    sb.append("|   NICHTS    |     ")
    if (whitedice)
      for (i <- 0 to 3)
        sb.append(color(i))
        if (comb(i) == 0) sb.append("   ")
        else
          val num = comb(i)
          sb.append(f"$num%2d"+" ")
    else sb.append("            ")
    sb.append(white + "     |     ")
    if (colordice)
      for (i <- 4 to 11)
        sb.append(color((i-4)/2))
        if (comb(i) == 0) sb.append("   ")
        else
          val num = comb(i)
          sb.append(f"$num%2d"+" ")
    else sb.append("                        ")
    sb.append(white + "     |")
    println(sb.toString())
    sb.clear()
    sb.append(white + "|      |      |      ")
    if (whitedice)
      for (i <- 0 to 3)
        if (comb(i) == 0) sb.append("   ")
        else sb.append("|  ")
    else sb.append("            ")
    sb.append("    |      ")
    if (colordice)
      for (i <- 4 to 11)
        if (comb(i) == 0) sb.append("   ")
        else sb.append("|  ")
    else sb.append("                        ")
    sb.append("    |")
    println(sb.toString())
    sb.clear()
    sb.append("|      a      |      ")
    var letter = 98
    val options = ArrayBuffer[Int](97)
    if (whitedice)
      for (i <- 0 to 3)
        if (comb(i) == 0)
          sb.append("   ")
          letter = letter+1
        else
          sb.append(letter.toChar + "  ")
          options += letter
          letter = letter+1
    else
      sb.append("            ")
      letter = letter + 4
    sb.append("    |      ")
    if (colordice)
      for (i <- 4 to 11)
        if (comb(i) == 0)
          sb.append("   ")
          letter = letter+1
        else
          sb.append(letter.toChar + "  ")
          options += letter
          letter = letter+1
    else sb.append("                        ")
    sb.append("    |")
    println(sb.toString())
    println("|             |                      |                                  |")
    println("|_____________|______________________|__________________________________|")
    options.toArray
  }

  def field(): Unit = {
    val Status = status()
    val p = Status.spieler(Status.User)
    val sb = new StringBuilder()
    println("Feld von Spieler: "+ p.name)
    println(red + mesh)
    redline()
    println(mesh)
    println(yellow + mesh)
    yellowline()
    println(mesh)
    println(green + mesh)
    greenline()
    println(mesh)
    println(blue + mesh)
    blueline()
    println(mesh)
    sb.append(white + fails.substring(0, 8))
    val fw = " [X]" * p.Feld.fwcount
    val nfw = " [ ]" * (4 - p.Feld.fwcount)
    sb.append(fw + nfw)
    sb.append("                                 ")
    sb.append(white + Status.Wurf(0) + "  ")
    sb.append(white + Status.Wurf(1) + "  ")
    sb.append(red + Status.Wurf(2) + "  ")
    sb.append(yellow + Status.Wurf(3) + "  ")
    sb.append(green + Status.Wurf(4) + "  ")
    sb.append(blue + Status.Wurf(5))
    println(sb.toString())
  }

  def redline(): Unit = {
    val Status = status()
    val row = Status.spieler(Status.User).Feld.Red
    val sb = new StringBuilder()
    for (i <- 0 to 10) {
      if (row.Kreuze(i)) sb.append("|  " + white + "X" + red + "  ")
      else sb.append(ryline.substring(i * 6, i * 6 + 6))
    }
    sb.append("| [")
    if (row.Kreuze(11)) sb.append(white + "X" + red)
    else sb.append(" ")
    sb.append("] |")
    println(sb.toString())
  }

  def yellowline(): Unit = {
    val Status = status()
    val row = Status.spieler(Status.User).Feld.Yellow
    val sb = new StringBuilder()
    for (i <- 0 to 10) {
      if (row.Kreuze(i)) sb.append("|  " + white + "X" + yellow + "  ")
      else sb.append(ryline.substring(i * 6, i * 6 + 6))
    }
    sb.append("| [")
    if (row.Kreuze(11)) sb.append(white + "X" + yellow)
    else sb.append(" ")
    sb.append("] |")
    println(sb.toString())
  }

  def greenline(): Unit = {
    val Status = status()
    val row = Status.spieler(Status.User).Feld.Green
    val sb = new StringBuilder()
    for (i <- 0 to 10) {
      if (row.Kreuze(i)) sb.append("|  " + white + "X" + green + "  ")
      else sb.append(gbline.substring(i * 6, i * 6 + 6))
    }
    sb.append("| [")
    if (row.Kreuze(11)) sb.append(white + "X" + green)
    else sb.append(" ")
    sb.append("] |")
    println(sb.toString())
  }

  def blueline(): Unit = {
    val Status = status()
    val row = Status.spieler(Status.User).Feld.Blue
    val sb = new StringBuilder()
    for (i <- 0 to 10) {
      if (row.Kreuze(i)) sb.append("|  " + white + "X" + blue + "  ")
      else sb.append(gbline.substring(i * 6, i * 6 + 6))
    }
    sb.append("| [")
    if (row.Kreuze(11)) sb.append(white + "X" + blue)
    else sb.append(" ")
    sb.append("] |")
    println(sb.toString())
  }

}

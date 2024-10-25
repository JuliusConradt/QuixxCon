class Spielfeld {
  val red = "\u001B[31m"
  val yellow = "\u001B[33m"
  val green = "\u001B[32m"
  val blue = "\u001B[34m"
  val white = "\u001B[0m"
  val mesh = ("+" + ("-") * 5) * 12 + "+"
  val ryline = "|  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  | 10  | 11  | 12  | [ ] |"
  val gbline = "| 12  | 11  | 10  |  9  |  8  |  7  |  6  |  5  |  4  |  3  |  2  | [ ] |"
  val fails = "FEHLWURF  []  []  []  []  []"

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
}
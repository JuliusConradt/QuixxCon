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

  def emptyfield(): Unit = { //gibt ein leeres Spielfeld aus
    println("Welcome to Quixx")
    printf("%s%s%n%s%n%s%n", red, mesh, ryline, mesh)
    printf("%s%s%n%s%n%s%n", yellow, mesh, ryline, mesh)
    printf("%s%s%n%s%n%s%n", green, mesh, gbline, mesh)
    printf("%s%s%n%s%n%s%n%s", blue, mesh, gbline, mesh, white)
    printf("%s%n", fails)
  }
}
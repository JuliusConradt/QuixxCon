@main def hello(): Unit = {
  // Die Variablen sollten zuerst definiert werden
  val red = "\u001B[31m"
  val yellow = "\u001B[33m"
  val green = "\u001B[32m"
  val blue = "\u001B[34m"
  val white = "\u001B[0m"
  val mesh = ("+"+("-")*5)*12 + "+"
  val mesh2 = "+" + ("-")*68 + "+"
  val ryline = "|  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  | 10  | 11  | 12  | [ ] |"
  val gbline = "| 12  | 11  | 10  |  9  |  8  |  7  |  6  |  5  |  4  |  3  |  2  | [ ] |"
  val crossed = "Kreuze| 1x | 2x | 3x | 4x | 5x | 6x | 7x | 8x | 9x | 10x | 11x | 12x |"
  val points = "Punkte|  1 |  3 |  6 | 10 | 15 | 21 | 28 | 36 | 45 |  55 |  66 |  78 |"
  val fails = "Fehlwürfe je -5 |[]|[]|[]|[]|[]|"
  val result = "[ ]"

  // Danach kommen die Print-Anweisungen
  println("Welcome to Quixx")
  printf("%s%s%n%s%n%s%n", red, mesh, ryline, mesh)
  printf("%s%s%n%s%n%s%n", yellow, mesh, ryline, mesh)
  printf("%s%s%n%s%n%s%n", green, mesh, gbline, mesh)
  printf("%s%s%n%s%n%s%n%s", blue, mesh, gbline, mesh, white)
  printf("%s%n%s%n", mesh2, crossed)
  printf("%s%n%s%n", points, mesh2)
  printf("%s%n", fails)
  printf("%s%s + %s%s + %s%s + %s%s - %s%s = %s%n", red, result, yellow, result, green, result, blue, result, white, result, result)
}

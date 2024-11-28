object BMain {
val Start = new BController.Start
val Ende = new BController.Ende
  def main(args: Array[String]): Unit = {
    val Spiel = new BController.Spiel(Start.Start())
    Ende.ende(Spiel.Spielstart())
  }
}
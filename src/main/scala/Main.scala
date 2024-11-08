object Main {
val Start = new Controller.Start
val Ende = new Controller.Ende
  def main(args: Array[String]): Unit = {
    val Spiel = new Controller.Spiel(Start.Start())
    Ende.ende(Spiel.Spielstart())
  }

}

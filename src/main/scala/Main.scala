object Main {
val Start = new Game.Start
val Ende = new Game.Ende
  def main(args: Array[String]): Unit = {
    val Spiel = new Game.Spiel(Start.Start())
    Spiel.Spiel()
    Ende.ende()
  }

}

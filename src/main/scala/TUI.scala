object TUI {
  def main(args: Array[String]): Unit = {
    val Spielfeld = new Spielfeld
    val Wuerfel = new Wuerfel
    println(Spielfeld.emptyfield())
    println(Wuerfel.printwurf(Wuerfel.wuerfeln()))
  }
}

object TUI {
    val Spielfeld = new Spielfeld
    val Wuerfel = new Wuerfel
     def main(args: Array[String]): Unit = {
      Spielfeld.emptyfield()
      Wuerfel.printwurf(Wuerfel.wuerfeln())
    }
}

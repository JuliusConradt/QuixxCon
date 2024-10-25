object TUI {
    val Spielfeld = new Spielfeld
    val Wuerfel = new Wuerfel

     def main(args: Array[String]): Unit = {
       println(Spielfeld.emptyfield())
       println(Wuerfel.printwurf(Wuerfel.wuerfeln()))
    }
}

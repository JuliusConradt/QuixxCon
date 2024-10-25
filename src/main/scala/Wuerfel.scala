import scala.util.Random

class Wuerfel {

  val Spielfeld = new Spielfeld
  def random(): Int = { //Return: Zufälliger Integer von 1 bis 6
    val random = new Random()
    random.nextInt(6) + 1
  }

  def wuerfeln(): Array[Int] = { //Return: zufällig gefülltes Integer-Array der Länge (6) > ein Wurf
    val Wurf = new Array[Int](6)
    for (i <- 0 to 5) {
      Wurf(i) = random()
    }
    Wurf
  }

  def printwurf(Wurf: Array[Int]): Unit = { //Return: wuerfelt und gibt den Wurf farblich gekennzeichnet in die Konsole aus
    printf("%s%d  %d  %s%d  %s%d  %s%d  %s%d", Spielfeld.white, Wurf(0),
      Wurf(1),
      Spielfeld.red, Wurf(2),
      Spielfeld.yellow, Wurf(3),
      Spielfeld.green, Wurf(4),
      Spielfeld.blue, Wurf(5))
  }
}
import scala.util.Random

object Wuerfel {
  def random(): Int = {
    val random = new Random()
    random.nextInt(6) + 1
  }

  def wuerfeln(): Array[Int] = {
    val Wurf = new Array[Int](6)
    for (i <- 0 to 5) {
      Wurf(i) = random()
    }
    Wurf
  }

  def printwurf(): Unit = {
    val Wurf = wuerfeln()
    printf("%s%d  %d  %s%d  %s%d  %s%d  %s%d", Spielfeld.white, Wurf(0),
      Wurf(1),
      Spielfeld.red, Wurf(2),
      Spielfeld.yellow, Wurf(3),
      Spielfeld.green, Wurf(4),
      Spielfeld.blue, Wurf(5))
  }
}

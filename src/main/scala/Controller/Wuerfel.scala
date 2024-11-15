package Controller

import scala.util.Random

class Wuerfel {

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

  def setValues(a : Array[Int]): Array[Int] = {
    a
  }

}

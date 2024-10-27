package View
import scala.io.StdIn
class TUI {
  def Startansicht():Unit = {
    println("Willkommen bei Quixx")
    println("Erstelle alle Spieler. Beende deine Eingabe mit einem leeren Spieler")
  }

  def zuwenigspieler():Unit =
    println("Erstelle mindenstens 2 Spieler")

  def Spielereinlesen(c:Int): Teilnehmer.Spieler = {
    println("Spieler" + (c+1) + ":")
    val Spieler = new Teilnehmer.Spieler(StdIn.readLine())
    Spieler
  }

  def ende(winner: String): Unit =
    println("Und der Sieger ist: " + winner)

  def Rundenbeginn(Nr: Int, name: String): Unit =
    println("Runde " + Nr + " | Feld von Spieler: " + name)

}

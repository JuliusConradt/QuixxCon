import scala.io.StdIn
import scala.collection.mutable.ArrayBuffer
object TUI {
  val Feldprint = new Feldprint

  def printFieldTUI(p:Spieler, s:Spiel):Unit = {
    println(Feldprint.fieldprint(p,s))
  }

  def printWurfTUI(w: Wuerfel) : Int = {
    println(w.printwurf(w.wuerfeln()))
    println("Whähle eine der folgenden Kombinationen oder bestätige deinen Fehlwurf mit 'F'")
    val input = StdIn.readLine()
    if (input == "F") return 0
    else if (input == "N") return 1
    1 + input.toInt
  }

  def main(args: Array[String]): Unit = {
    var count = 0
    var continue = true
    val Spielerliste: ArrayBuffer[Spieler] = ArrayBuffer()
    println("Herzlichen Willkommen bei Quixx!")
    println("Schreibe 'exit' wenn du alle Spieler erstellt hast")
    while(continue){
      count += 1
      println("Spieler"+count+":")
      val input = StdIn.readLine()
      if (input == "exit" && count > 2) {
        continue = false
      } else if (input == "exit" && count <= 2) {
        println("Bitte erstelle mindestens 2 Spieler")
        count -= 1
      } else {
        Spielerliste += new Spieler(input)
      }
    }
    val FinalSpielerliste: Array[Spieler] = Spielerliste.toArray
    val Spiel = new Spiel(FinalSpielerliste)
    Spiel.nextRound()
    println("Danke fürs mitspielen")
  }
}

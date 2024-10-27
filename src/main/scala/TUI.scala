import scala.io.StdIn
import scala.collection.mutable.ArrayBuffer
object TUI {
  val Feldprint = new Feldprint

  def printFieldTUI(p:Spieler, s:Spiel):Unit = {
    println(Feldprint.fieldprint(p,s))
  }

  def printWurfTUI(w: Array[Int]) : Int = {
    val Wuerfel = new Wuerfel
    println(Wuerfel.printwurf(w))
    println("Wähle eine der folgenden Kombinationen, bestätige deinen Fehlwurf mit 'F' oder wähle die weiteren Kombinationen 'N'")
    println(w(0)+w(1))
    val input = StdIn.readLine()
    if (input == "F") return 0
    else if (input == "N") return 1
    1 + input.toInt
  }

  def printUserWurf(w: Array[Int]): Int = {
    println("Wähle eine der folgenden Kombination oder gebe das Spiel weiter mit 'N'")
    println(w(0)+w(1))
    val input = StdIn.readLine()
    if (input == "N") return 1
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
      println("Teilnehmer" +count+":")
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

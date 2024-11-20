package Model

class Spieler(val name: String) extends Observable {
  val Feld = new Feld

  def updateFeld(): Unit = {
    notifyObservers(this)
  }
}
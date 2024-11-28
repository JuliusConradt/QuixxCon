package model

object StatusManager {
  private val emptyplayer : Array[Spieler] = Array.empty[Spieler]
  private val Beispielwurf = Array(1,2,3,4,5,6)
  private var currentStatus: Status = Status(emptyplayer, Beispielwurf, 0, 0,false, false, false, 0, false, false, false, false, false, false)
  
  def getStatus: Status = currentStatus
  
  def setStatus(newStatus: Status): Unit = currentStatus = newStatus
}
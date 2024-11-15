package util

trait Observer {
  def update(p: Model.Spieler): Unit
}

class Observable {
  var subscribers: Vector[Observer] = Vector()
  def add(s:Observer) = subscribers=subscribers:+s
  def remove(s:Observer) = subscribers=subscribers.filterNot(o=>o==s)
  def notifyObservers = subscribers.foreach(o=>o.update)
}

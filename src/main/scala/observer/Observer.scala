package observer

class Observer(subscribers: Array[view.View]) {
  
  def inform(): Unit = {
    subscribers.foreach(sub => sub.update())
  }
  
}

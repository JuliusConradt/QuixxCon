package view

import model.Spieler
trait View {
  def update(): Unit
  
  def start(): Array[Spieler]
}


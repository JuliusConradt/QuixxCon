package main

import controller.Controller

object Quixx {

  def main(args: Array[String]): Unit = {
    val controller = Controller(0)
    controller.start()
    controller.spiel()
    controller.auswertung()
  }

}
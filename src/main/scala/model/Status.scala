package model

import BView.TUI
class Status (
             val spieler: Array[Spieler],
             val Wurf: Array[Int],
             val Runde : Int,
             val Master: Int,
             val User: Int,
             val rowsclosed : Int,
             val rclosed: Boolean,
             val yclosed: Boolean,
             val gclosed: Boolean,
             val bclosed: Boolean,
             val beendet: Boolean
             ){
}

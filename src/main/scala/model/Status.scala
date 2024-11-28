package model

import view.TUI
case class Status (
             spieler: Array[Spieler],
             Wurf: Array[Int],
             Master: Int,
             User: Int,
             same: Boolean,
             whitedice: Boolean,
             colordice: Boolean,
             rowsclosed : Int,
             rclosed: Boolean,
             yclosed: Boolean,
             gclosed: Boolean,
             bclosed: Boolean,
             beendet: Boolean,
             print: Boolean
             )
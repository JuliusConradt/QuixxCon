package controller

import model.StatusManager
import model.Wuerfel
import observer.Observer
import view.TUI
class Controller(view: Int) {
  val View = view match {
    case 0 => new TUI()
    //case 1 => new GUI()
  }
  val observer = Observer(Array(View))

  def start(): Unit = {
    val Spieler = View.start()
    StatusManager.setStatus(model.Status(Spieler, Array(0), -1, 0, false, true, true, 0, false, false, false, false, false))
  }

  def spiel(): Unit = {
    val Status = status()
    val newMaster = (Status.Master + 1 ) % Status.spieler.length
    StatusManager.setStatus(Status.copy(Wurf = Wuerfel.wuerfeln(), Master = newMaster, User = newMaster, whitedice = true, colordice = true))
    option()
    val newstatus = status()
    if (!newstatus.beendet)
      spiel()
  }

  def option(): Unit = {
    if (!StatusManager.getStatus.same)
      StatusManager.setStatus(StatusManager.getStatus.copy(whitedice=true))
    observer.inform() //Spielfeld ausgeben
    val Status = status()
    if (Status.Master != (Status.User + 1)%Status.spieler.length || Status.same) {
      StatusManager.setStatus(Status.copy(User = (Status.User + 1)%Status.spieler.length))
      option()
      StatusManager.setStatus(StatusManager.getStatus.copy(same = false))
    }
  }

  def ankreuzen(option: Int): Unit = {
    val Status = status()
    val com = comb()
    val n = com(option)
    val p = Status.spieler(Status.User)

    if (option == 0 || option == 4 || option == 5) //ROT
      if(n==12 && p.Feld.Red.count >= 5)
        p.Feld.Red.Kreuze(11) = true
        p.Feld.Red.count += 1
        Status.spieler(Status.User) = p
        StatusManager.setStatus(Status.copy(rclosed = true, rowsclosed = Status.rowsclosed + 1))
      val newstatus = status()
      newstatus.spieler(Status.User).Feld.Red.Kreuze(n-2) = true
      newstatus.spieler(Status.User).Feld.Red.count += 1
      StatusManager.setStatus(newstatus)

    else if (option == 1 || option == 6 || option == 7) //GELB
      if(n==12 && p.Feld.Yellow.count >= 5)
        p.Feld.Yellow.Kreuze(11) = true
        p.Feld.Yellow.count += 1
        Status.spieler(Status.User) = p
        StatusManager.setStatus(Status.copy(yclosed = true, rowsclosed = Status.rowsclosed + 1))
      val newstatus = status()
      newstatus.spieler(Status.User).Feld.Yellow.Kreuze(n-2) = true
      newstatus.spieler(Status.User).Feld.Yellow.count += 1
      StatusManager.setStatus(newstatus)

    else if (option == 2 || option == 8 || option == 9) //GRÜN
      if (n==2 && p.Feld.Green.count >= 5)
        p.Feld.Green.Kreuze(11) = true
        p.Feld.Green.count += 1
        Status.spieler(Status.User) = p
        StatusManager.setStatus(Status.copy(gclosed = true, rowsclosed = Status.rowsclosed + 1))
      val newstatus = status()
      newstatus.spieler(Status.User).Feld.Green.Kreuze(12-n) = true
      newstatus.spieler(Status.User).Feld.Green.count += 1
      StatusManager.setStatus(newstatus)

    else if (option == 3 || option == 10 || option == 11) //BLAU
      if (n==2 && p.Feld.Blue.count >= 5)
        p.Feld.Blue.Kreuze(11) = true
        p.Feld.Blue.count += 1
        Status.spieler(Status.User) = p
        StatusManager.setStatus(Status.copy(bclosed = true, rowsclosed = Status.rowsclosed + 1))
      val newstatus = status()
      newstatus.spieler(Status.User).Feld.Blue.Kreuze(12-n) = true
      newstatus.spieler(Status.User).Feld.Blue.count += 1
      StatusManager.setStatus(newstatus)
  }

  def fehlwurf(): Unit = {
    val Status = status()
    Status.spieler(Status.User).Feld.fwcount += 1
    if (Status.spieler(Status.User).Feld.fwcount == 4)
      StatusManager.setStatus(Status.copy(beendet = true, colordice = false))
    else
      StatusManager.setStatus(Status.copy(colordice = false))
  }

  def comb(): Array[Int] = {
    val Wurf = status().Wurf
    val comb = Array.fill(12)(0)
    for (i <- 0 to 3)
      comb(i) = Wurf(0) + Wurf(1)
    for (i <- 0 to 7)
      comb(i+4) = Wurf(i%2)+Wurf(i/2+2)
    correctedcomb(comb)
  }

  def correctedcomb(comb: Array[Int]): Array[Int] = {
    for (i <- 0 to 3)
      comb(i) = verfuegbar(i+1, comb(i))
    for (i <- 0 to 7)
      comb(i+4) = verfuegbar((i/2)+1, comb(i+4))
    comb
  }

  def verfuegbar(row: Int, sum: Int):Int = {
    val Status = status()
    var result = false
    val p = Status.spieler(Status.User)
    if (row<3) {
      for (i <- (sum - 2) to 10) {
        result = row match {
          case 1 =>
            var b = p.Feld.Red.Kreuze(i)
            if (Status.rclosed) b = true
            b
          case 2 =>
            var b = p.Feld.Yellow.Kreuze(i)
            if (Status.yclosed) b = true
            b
        }
        if (result) return 0
      }
    } else {
      for (i <- (12 - sum) to 10) {
        result = row match {
          case 3 => {
            var b = p.Feld.Green.Kreuze(i)
            if (Status.gclosed) b = true
            b
          }
          case 4 => {
            var b = p.Feld.Blue.Kreuze(i)
            if (Status.bclosed) b = true
            b
          }
        }
        if (result) return 0
      }
    }
    sum
  }












  def status(): model.Status = StatusManager.getStatus

}

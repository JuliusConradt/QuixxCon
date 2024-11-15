import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import Model.Spieler


class SpielerSpec extends AnyWordSpec with Matchers {

  "A Spieler" should {
    "have a name that is correctly set" in {
      val spieler = new Spieler("John Doe")

      // Überprüfe, ob der Name des Spielers korrekt gesetzt ist
      spieler.name shouldBe "John Doe"
    }

    "have a Feld instance" in {
      val spieler = new Spieler("John Doe")

      // Überprüfe, ob das Feld korrekt initialisiert wurde
      spieler.Feld shouldBe a [Model.Feld] // Checkt, ob 'Feld' eine Instanz der Klasse 'Feld' ist
    }
  }
}

import model.Reihe
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ReiheSpec extends AnyWordSpec with Matchers {

  "A Reihe" should {

    "correctly initialize Typ, Kreuze, and count" in {
      val reihe = new Reihe(1)

      // Überprüfe, ob der Typ korrekt gesetzt wurde
      reihe.Typ shouldBe 1

      // Überprüfe, ob das Kreuze-Array mit der richtigen Länge und Initialwerten (false) gefüllt ist
      reihe.Kreuze.length shouldBe 12
      reihe.Kreuze shouldEqual Array.fill(12)(false)

      // Überprüfe, ob der count-Wert auf 0 gesetzt ist
      reihe.count shouldBe 0
    }
  }
}

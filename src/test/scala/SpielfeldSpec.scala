import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class SpielfeldSpec extends AnyWordSpec with Matchers {

  "The Spielfeld class" should {

    "generate an empty game field with emptyfield()" in {
      val spielfeld = new Spielfeld
      val emptyFieldOutput = spielfeld.emptyfield()

      // Check that the empty field output starts with the welcome message
      emptyFieldOutput should include ("Welcome to Quixx")

      // Check the presence of the colored mesh lines and line content for each row
      emptyFieldOutput should include (spielfeld.red + spielfeld.mesh)
      emptyFieldOutput should include (spielfeld.yellow + spielfeld.mesh)
      emptyFieldOutput should include (spielfeld.green + spielfeld.mesh)
      emptyFieldOutput should include (spielfeld.blue + spielfeld.mesh)

      // Check the presence of ryline and gbline lines in appropriate places
      emptyFieldOutput should include (spielfeld.ryline)
      emptyFieldOutput should include (spielfeld.gbline)

      // Check that the "FEHLWURF" line is included at the bottom with the correct formatting
      emptyFieldOutput should include (spielfeld.white + spielfeld.fails)
    }
  }
}

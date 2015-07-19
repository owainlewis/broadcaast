package controllers.api

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.mvc._

object T extends Controller with TokenAuthentication

@RunWith(classOf[JUnitRunner])
class TokenAuthenticationSpec extends Specification {

  "The token authentication trait" should {

    "extract a valid token" in {
      T.extractToken("Token token=123") shouldEqual Some("123")
    }

    "return none for an invalid token format" in {
      T.extractToken("Token 123") shouldEqual None
    }
  }
}

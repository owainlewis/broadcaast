package controllers

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

@RunWith(classOf[JUnitRunner])
class AuthenticationSpec extends Specification {

  "The authentication controller" should {

    "return 200 when creating a new session" in new WithApplication {
      val result = route(FakeRequest(GET, "/login"))
      result.isDefined must beTrue
      status(result.get) must equalTo(OK)
    }
  }
}

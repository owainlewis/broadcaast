package controllers

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

@RunWith(classOf[JUnitRunner])
class RegistrationSpec extends Specification {

  "The registration controller" should {

    "return 200 when creating a new account" in new WithApplication {
      val result = route(FakeRequest(GET, routes.RegistrationController.signUp().url))
      result.isDefined must beTrue
      status(result.get) must equalTo(OK)
    }
  }
}

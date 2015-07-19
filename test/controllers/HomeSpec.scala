package controllers

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

@RunWith(classOf[JUnitRunner])
class HomeSpec extends Specification {

  "The home controller" should {

    "return HTTP 200" in new WithApplication {
      val newConversation = route(FakeRequest(GET, routes.HomeController.index().url))
      status(newConversation.get) must equalTo(OK)
    }
  }
}

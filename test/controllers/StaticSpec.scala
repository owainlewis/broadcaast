package controllers

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

@RunWith(classOf[JUnitRunner])
class StaticSpec extends Specification {

  "The static controller" should {

    "return 200 when viewing FAQs" in new WithApplication {
      val result = route(FakeRequest(GET, routes.StaticController.faq().url))
      result.isDefined must beTrue
      status(result.get) must equalTo(OK)
    }

    "return 200 when viewing press" in new WithApplication {
      val result = route(FakeRequest(GET, routes.StaticController.press().url))
      result.isDefined must beTrue
      status(result.get) must equalTo(OK)
    }
  }
}

package controllers

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

@RunWith(classOf[JUnitRunner])
class StreamSpec extends Specification {

  "The conversation controller" should {

    "return 200 when listing trending items" in new WithApplication {
      val result = route(FakeRequest(GET, routes.StreamController.index().url))
      result.isDefined must beTrue
      status(result.get) must equalTo(OK)
    }

    "return 303 when creating a new item but not logged in" in new WithApplication {
      val result = route(FakeRequest(GET, routes.StreamController.newStream().url))
      result.isDefined must beTrue
      status(result.get) must equalTo(303)
    }
  }
}

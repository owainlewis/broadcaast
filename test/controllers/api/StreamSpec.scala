package controllers.api

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

@RunWith(classOf[JUnitRunner])
class StreamSpec extends Specification {

  "The Stream controller" should {

    "Show all streams for a user" in new WithApplication {
      val result = route(FakeRequest(GET, controllers.api.routes.StreamController.index(user = 1).url))

      result.isDefined must beTrue
      status(result.get) must equalTo(200)
      contentType(result.get) must equalTo(Some("application/json"))
    }

    "Show a single stream for a user" in new WithApplication {
      val result = route(FakeRequest(GET, controllers.api.routes.StreamController.show(user = 1, id = 1).url))
      result.isDefined must beTrue
      status(result.get) must equalTo(200)
      contentType(result.get) must equalTo(Some("application/json"))
    }
  }
}

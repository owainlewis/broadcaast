package controllers.api

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

@RunWith(classOf[JUnitRunner])
class CommentSpec extends Specification {

  "The comment api controller" should {

    "return 401 unauthorized when making requests without a token" in new WithApplication {
      val result = route(FakeRequest(GET, "/api/discussions/1/comments"))
      pending
    }
  }
}
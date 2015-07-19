package controllers.api

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.{WithApplication, FakeRequest}
import play.api.test.Helpers._

@RunWith(classOf[JUnitRunner])
class UsersSpec extends Specification {

  "The user controller" should {

    "return 401 unauthorized when making requests without a token" in new WithApplication {
      val result = route(FakeRequest(GET, "/api/users"))
      pending
    }
  }
}

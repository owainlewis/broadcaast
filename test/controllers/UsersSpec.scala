package controllers

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

@RunWith(classOf[JUnitRunner])
class UsersSpec extends Specification {

  "The user controller" should {

    "return 200 when listing users" in new WithApplication {
      val result = route(FakeRequest(GET, routes.UserController.index().url))
      result.isDefined must beTrue
      status(result.get) must equalTo(OK)
    }
  }
}

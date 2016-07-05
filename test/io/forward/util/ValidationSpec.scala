package io.forward.util

import org.specs2.mutable._

class ValidationSpec extends Specification {

  "Validation" should {
    "check valid jpeg image url" in {
      Validation.validImage("http://mysite.com/image.jpeg") must beTrue
    }
    "check valid jpg image url" in {
      Validation.validImage("http://mysite.com/image.jpg") must beTrue
    }
    "check valid gif image url" in {
      Validation.validImage("http://mysite.com/image.gif") must beTrue
    }
    "check valid png image url" in {
      Validation.validImage("http://mysite.com/image.png") must beTrue
    }
    "handle images with no period in the URL" in {
      Validation.validImage("foobar") must beFalse
    }
  }
}

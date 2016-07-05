package io.forward.util

import org.specs2.mutable._

class TokenSpec extends Specification {
  
  "A Token" should {
    "have a getRandomAccessToken method that returns a random access token for a given user ID" in {
      Token.randomAccessToken.isInstanceOf[String] mustEqual true
      Token.randomAccessToken.length mustEqual 32
    }
  }
}

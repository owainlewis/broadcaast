package io.forward.util

import org.specs2.mutable.Specification

class SanitizerSpec extends Specification {

  "The Sanitizer" should {

    "lowercase and trim whitespace from a string" in {
      Sanitizer.lowerCaseStrip(" FOO ") mustEqual "foo"
    }
  }
}

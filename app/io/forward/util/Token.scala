package io.forward.util

import java.util.UUID

object Token {

  /** Generates a random API access token for a given user */
  def randomAccessToken: String = {
    UUID.randomUUID.toString.replaceAll("-", "")
  }
}

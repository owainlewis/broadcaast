package io.forward.util

object Sanitizer {

  /**
   * Used to sanitize user input data for example registration user names and email addresses
   */
  def lowerCaseStrip(input: String): String = input.toLowerCase.trim

  /**
   * Truncates a line of text adding a ... if it is too long
   */
  def truncate(input: String, limit: Int = 30): String =
    if (input.length < limit) input else input.take(limit) ++ " ..."
}

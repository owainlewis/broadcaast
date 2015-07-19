package io.forward.util

object Validation {

  val supportedImageFormats = Set("jpeg", "jpg", "gif", "png")

  def validImage(url: String): Boolean = {
    val extension = url.split('.').last
    supportedImageFormats.contains(extension)
  }
}

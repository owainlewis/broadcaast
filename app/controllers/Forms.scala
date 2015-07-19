package controllers

import repository.UserRepository
import play.api.data.Form
import play.api.data.Forms._

case class ChannelData(
  title: String,
  context: Option[String],
  locked: Boolean
                       )

case class ItemData(body: Option[String])

case class LoginData(email: String, password: String)

case class RegistrationData(username: String, email: String, password: String)

case class ProfileData(
  username : Option[String],
  email    : Option[String],
  password : Option[String])

trait Forms {

  val loginForm = Form(
    mapping(
      "email"    -> email,
      "password" -> nonEmptyText
    )(LoginData.apply)(LoginData.unapply)
  )

  val registrationForm = Form(
    mapping(
      "username" -> nonEmptyText,
      "email"    -> email,
      "password" -> nonEmptyText
    )(RegistrationData.apply)(RegistrationData.unapply)
    verifying ("Username has been taken", f => UserRepository.findOneByUsername(f.username).isEmpty)
    verifying ("Email already registered", f => UserRepository.findOneByEmail(f.email).isEmpty)
    verifying ("Password must be greater than 6 characters", f => f.password.length >= 6)
  )

  /**
   * Editing user profile information
   */
  val userProfileForm = Form(
    mapping(
      "username" -> optional(text),
      "email"    -> optional(text),
      "password" -> optional(text)
    )(ProfileData.apply)(ProfileData.unapply)
    verifying ("Username has been taken", f =>
      f.username exists (u => UserRepository.findOneByUsername(u).isEmpty)
  ))

  /**
   * Create a new channel
   */
  val channelForm = Form(
    mapping(
      "title"     -> nonEmptyText,
      "context"   -> optional(text),
      "locked"    -> boolean
    )(ChannelData.apply)(ChannelData.unapply)
  )

  /**
   * Add an item to a channel
   */
  val itemForm = Form(
    mapping(
      "body"  -> optional(text(maxLength = 1000))
    )(ItemData.apply)(ItemData.unapply)
    verifying ("A comment or image must be present", f => f.body.isDefined)
    verifying ("Text is too big", f => f.body exists (_.length < 512))
  )
}
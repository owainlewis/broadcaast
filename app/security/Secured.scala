package security

import controllers.routes
import repository.UserRepository
import models.User
import play.api.mvc._

trait Secured {

  /**
   * Extract a users email from the session header in a request
   * @param request A HTTP request
   */
  def email(request: RequestHeader) =
    request.session.get("email")

  /**
   * If a user is not authenticated (logged in) then redirect them to the login page
   *
   */
  def onUnauthorized(request: RequestHeader) =
    Results.Redirect(routes.AuthenticationController.login())

  /**
   * Used to wrap a request with authentication
   *
   * @param f A function that checks if a user is currently authenticated
   */
  def withAuth(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(email, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
  }

  /**
   * Used to run a request but explicitly require a user to be present in the request
   *
   */
  def withUser(f: User => Request[AnyContent] => Result) = withAuth { email => implicit request =>
    UserRepository.findOneByEmail(email).map { user =>
      f(user)(request)
    }.getOrElse(onUnauthorized(request))
  }
}


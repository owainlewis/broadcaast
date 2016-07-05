package security

import controllers.routes
import repository.UserRepository
import models.User
import play.api.mvc._

trait Secured {
  
  def email(request: RequestHeader) =
    request.session.get("email")

  def onUnauthorized(request: RequestHeader) =
    Results.Redirect(routes.AuthenticationController.login())

  def withAuth(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(email, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
  }

  def withUser(f: User => Request[AnyContent] => Result) = withAuth { email => implicit request =>
    UserRepository.findOneByEmail(email).map { user =>
      f(user)(request)
    }.getOrElse(onUnauthorized(request))
  }
}


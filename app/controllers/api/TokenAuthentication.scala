package controllers.api

import repository.UserRepository
import play.api.mvc._
import models.User

trait TokenAuthentication { self: Controller =>

  /**
   * Extract an optional token from a request header if it is in the correct format
   *
   * Example:
   *
   *   extractToken("Token token=123") => Some("123")
   *
   * @param authHeader An authentication header i.e "Token token=123"
   */
  def extractToken(authHeader: String): Option[String] = {
    authHeader.split("Token token=") match {
      case Array(_, token) => Some(token)
      case _          => None
    }
  }

  /**
   * Fetch an API token from the request headers
   *
   * If one exists then allow the request else deny it
   *
   * Token request require an authorization header to be set
   *
   * curl -i https://discusslr.com/api/discussions -H "Authorization: Token token=TOKEN"
   *
   */
  def withAPIToken(f: => User => Request[AnyContent] => Result) = Action { implicit request =>
    request.headers.get("Authorization") flatMap { authHeaderToken =>
      extractToken(authHeaderToken) flatMap { token =>
        UserRepository.findOneByToken(token) map { user =>
          f(user)(request)
        }
      }
    } getOrElse Unauthorized("Invalid API token").as("application/json")
  }
}

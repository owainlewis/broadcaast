package filters

import play.api.mvc._
import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits.defaultContext

object HTTPSRedirectFilter extends Filter {

  private val oneDay = 86400

  /**
   * Force HTTPS redirect in AWS ELB
   */
  def apply(nextFilter: (RequestHeader) => Future[Result])(requestHeader: RequestHeader): Future[Result] = {
    requestHeader.headers.get("x-forwarded-proto") match {
      case Some(header) =>
        if ("https" == header) {
          nextFilter(requestHeader).map { result =>
            result.withHeaders(("Strict-Transport-Security", s"max-age=$oneDay"))
          }
        } else {
          Future.successful(Results.Redirect("https://" + requestHeader.host + requestHeader.uri, 301))
        }
      case None => nextFilter(requestHeader)
    }
  }
}

import play.api.mvc._
import filters._

object Global extends WithFilters(HTTPSRedirectFilter)

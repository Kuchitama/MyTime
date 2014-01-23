package mvc

import play.api.mvc._
import scalikejdbc.DBSession

trait BaseAction extends Controller {
  def apply(block: Request[AnyContent] => DBSession => Result):Action[AnyContent]
}

package mvc

import play.api.mvc._
import scalikejdbc.{DB, DBSession}

/**
 * Created by k2 on 2014/01/23.
 */
object NoAuthenticateAction extends BaseAction {
  def apply(block: Request[AnyContent] => DBSession => Result):Action[AnyContent] = {
    Action { request =>
      DB localTx { implicit session =>
        block(request)(session)
      }
    }
  }
}

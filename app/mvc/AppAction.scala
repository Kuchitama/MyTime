package mvc

import play.api.mvc._
import java.sql.DriverManager
import scalikejdbc._
import utils.Loggable
import play.api.Logger

/**
 * Created by Kuchitama on 2014/01/04.
 */
object AppAction extends Controller with Loggable {


  def apply(block: Request[AnyContent] => DBSession => Result):Action[AnyContent] = {
    Action { request =>
      DB localTx { implicit session =>
        block(request)(session)
      }
    }
  }

}

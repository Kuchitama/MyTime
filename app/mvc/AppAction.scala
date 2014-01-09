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
  Class.forName("org.postgresql.Driver")

  private lazy val conf = play.api.Play.current.configuration

  private lazy val url = conf.getString("db.default.url")
  private lazy val user = conf.getString("db.default.user")
  private lazy val password = conf.getString("db.default.password")

  require(List(url, user, password).forall(_.isDefined))

  def apply(block: Request[AnyContent] => DB => Result):Action[AnyContent] = {

    using(DB(DriverManager.getConnection(url.get, user.get, password.get))) { db =>
      Action { request =>
        block(request)(db)
      }
    }
  }

}

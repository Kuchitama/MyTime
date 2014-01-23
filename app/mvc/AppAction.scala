package mvc

import play.api.mvc._
import java.sql.DriverManager
import scalikejdbc._
import utils.Loggable
import play.api.Logger
import java.util.Date
import controllers.routes



/**
 * Created by Kuchitama on 2014/01/04.
 */
object AppAction extends BaseAction with Loggable {

  def apply(block: Request[AnyContent] => DBSession => Result):Action[AnyContent] = {
    Action { request =>
      if (checkAuthenticated(request) == false) {
        // ログイン画面にリダイレクトさせる
        Redirect(routes.Application.login)
      } else {
        DB localTx { implicit session =>
          block(request)(session)
        }
      }
    }
  }

  /**
   * ログイン済みかどうかを判定する
   * @param request
   * @tparam A
   * @return
   */
  private def checkAuthenticated[A](request:Request[A]):Boolean = {
    request.session.get("user_id").map { id =>
      request.session.get("expire_at").exists{t =>
        val expireAt = {
          val date = new Date()
          date.setTime(t.toLong)
          date
        }
        expireAt.after(new Date)
      }
    }.getOrElse(false)
  }
}



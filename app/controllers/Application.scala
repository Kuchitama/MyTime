package controllers

import play.api._
import play.api.mvc._
import scalikejdbc._
import java.sql.DriverManager

object Application extends Controller {
  private lazy val conf = play.api.Play.current.configuration

  def index = Action {
    Class.forName("org.postgresql.Driver")
    val url = conf.getString("db.default.url")
    val user = conf.getString("db.default.user")
    val password = conf.getString("db.default.password")
    Logger.debug(s"url[${url}]")

    using(DB(DriverManager.getConnection(url.get, user.get, password.get))) { db =>
      val name: Option[String] = db readOnly { implicit session =>
        SQL("show tables;").bind(1).map(rs => rs.string("name")).single.apply()
      }
    }
    Ok(views.html.index("MyTime is ready."))
  }

}

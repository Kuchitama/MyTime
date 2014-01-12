package controllers

import mvc.AppAction
import utils.Loggable
import play.api.mvc._
import models.User

object Application extends Controller with Loggable {

  def index = AppAction {
    implicit request =>
      implicit session =>

    User.add("foobar")

    val users = User.findAll()

    Ok(views.html.index(users))
  }

}

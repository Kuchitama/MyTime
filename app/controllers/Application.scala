package controllers

import mvc.AppAction
import utils.Loggable
import play.api.mvc._
import models.{Task, User}

object Application extends Controller with Loggable {

  def index = AppAction {
    implicit request =>
      implicit session =>

    val users = User.findAll()
      val tasks = Task.findAll()

    Ok(views.html.index(users, tasks))
  }

}

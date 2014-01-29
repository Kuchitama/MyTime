package controllers

import mvc.AppAction
import java.util.Date
import utils.Loggable
import play.api.mvc._
import models.{Task, User}
import play.api.data.Form
import play.api.data.Forms._
import controllers.form.TaskForm

object TaskController extends Controller with Loggable {
  val taskForm = Form(mapping("name" -> nonEmptyText(),
    "priority" -> number(min = 0, max = 10),
    "isDone" -> boolean, "time" -> longNumber(min = 0),
    "limitDate" -> optional(date("yyyyMMdd")))(TaskForm.apply)(TaskForm.unapply))

  def index = AppAction {
    implicit request =>
      implicit session =>

    Ok("")
  }

  def form() = AppAction {
    implicit request =>
      implicit session=>

    Ok(views.html.task_form())
  }

  def create() = AppAction {
    implicit request =>
      implicit session =>

    val form = taskForm.bindFromRequest()

    if (form.hasErrors) {
      logger.debug(form.errors.mkString("¥n"))
      Ok(views.html.task_form(None, form.errors))
    } else {
      request.session.get("user_id").map(_.toLong).map { userId =>
        val newTask = form.get.toTask(userId)
        Task.add(newTask)
        Redirect(routes.TaskController.index)
      }.get
    }
  }
}


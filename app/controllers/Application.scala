package controllers

import mvc.AppAction
import utils.Loggable
import play.api.mvc._
import play.api.Logger

object Application extends Controller with Loggable {

  def index = AppAction {
    implicit request =>
      implicit db =>

    logger.info("called Application#index")

    Ok(views.html.index("MyTime is ready."))
  }

}

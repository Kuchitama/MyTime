import play.api._
import utils.Loggable

/**
 * Created by k2 on 2014/01/05.
 */
object Global extends GlobalSettings with Loggable {

  override def onStart(app: Application) {
    logger.info("app start")
  }
}

package utils

import play.api.Logger

/**
 * Created by k2 on 2014/01/04.
 */
trait Loggable {
  protected lazy val logger:Logger = Logger(this.getClass)
}

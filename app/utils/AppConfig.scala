package utils

import play.api.Configuration
import util.control.Exception._

case class AppConfig(conf:Configuration = play.api.Play.current.configuration) {

  def get(key:String, validValues: Set[String] = Set.empty): String = {
    getOpt(key, validValues).getOrElse(throw new IllegalStateException(s"configuration key[${key}}] is not set."))
  }
  def get[A](key: String, f: Configuration => String => Option[A]):A = {
    getOpt[A](key, f).getOrElse(throw new IllegalStateException(s"configuration key[${key}}] is not set."))
  }

  def getOpt(key:String, validValues: Set[String] = Set.empty):Option[String] = {
    conf.getString(key, Option(validValues))
  }
  def getOpt[A](key:String, f: Configuration => String => Option[A]):Option[A] = {
    allCatch.opt(f(conf)(key)).flatten
  }

}


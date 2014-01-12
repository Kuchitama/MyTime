package models

import scalikejdbc._
import scalikejdbc.SQLInterpolation._

import java.util.Date

/**
 * Created by k2 on 2014/01/12.
 */
case class Users(
  id: Long,
  user_id: String,
  createdTime: Date,
  updatedTime:Date)


object Users extends SQLSyntaxSupport[Users] {

}

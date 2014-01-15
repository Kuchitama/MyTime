package models

import scalikejdbc._
import scalikejdbc.SQLInterpolation._

import java.util.Date

/**
 * Created by k2 on 2014/01/12.
 */
case class User(
  id: Long,
  userIdentifier: String,
  name: String,
  createdTime: Date,
  updatedTime:Date)


object User extends SQLSyntaxSupport[User] {

  override val tableName = "users"
  override val columns = Seq("id",
    "user_identifier",
    "name",
    "created_time",
    "updated_time")

  def apply(syntax: SyntaxProvider[User])(rs: WrappedResultSet) = {
    val u = syntax.resultName
    new User(id = rs.long(u.id), userIdentifier = rs.string(u.userIdentifier), name = rs.string(u.name), createdTime = rs.date(u.createdTime), updatedTime = rs.date(u.updatedTime))
  }

  private lazy val u = User.syntax("u")

  def findAll()(implicit s: DBSession): List[User] = withSQL {
    select.from(User as u)
  }.map(User(u)).list.apply()

  def findById(id: Long)(implicit s: DBSession): Option[User] = withSQL {
    select.from(User as u).where.eq(u.id, id)
  }.map(User(u)).headOption().apply()

  def findByIdentifier(identifier: String)(implicit s:DBSession):Option[User] = withSQL {
    select.from(User as u).where.eq(u.userIdentifier, identifier)
  }.map(User(u)).headOption().apply()

  def add(identifier: String, name: String)(implicit s: DBSession): User = {
    val now = new Date()
    val id = withSQL {
      insert.into(User).namedValues(column.userIdentifier -> identifier, column.name -> name, column.createdTime -> now, column.updatedTime -> now)
    }.updateAndReturnGeneratedKey.apply()

    findById(id).get
  }
}

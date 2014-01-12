package models

import scalikejdbc._
import scalikejdbc.SQLInterpolation._

import java.util.Date

case class Task(id: Long,
  userId: Long,
  name: String,
  priority: Int,
  isDone: Boolean,
  time: Long,
  limitDate: Date,
  createdTime: Date,
  updatedTime: Date) {

  def user(implicit s: DBSession): User = User.findById(userId).getOrElse(throw new IllegalStateException(s"User[$userId] is not exsts."))
}

object Task extends SQLSyntaxSupport[Task] {

  override val tableName = "tasks"
  override val columns = Seq("id",
    "user_id",
    "name",
    "priority",
    "is_done",
    "time",
    "limit_date",
    "created_time",
    "updated_time")

  def apply(syntax: SyntaxProvider[Task])(rs: WrappedResultSet) = {
    val u = syntax.resultName
    new Task(id = rs.long(u.id),
      userId = rs.long(u.userId),
      name = rs.string(u.name),
      priority=rs.int(u.priority),
      isDone = rs.boolean(u.isDone),
      time = rs.long(u.time),
      limitDate = rs.date(u.limitDate),
      createdTime = rs.date(u.createdTime),
      updatedTime = rs.date(u.updatedTime))
  }

  private lazy val m = Task.syntax("m")

  def findAll()(implicit s: DBSession): List[Task] = withSQL {
    select.from(Task as m)
  }.map(Task(m)).list.apply()

  // TODO
  def add(identifier: String)(implicit s: DBSession): Task = ???
}

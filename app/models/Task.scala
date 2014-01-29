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
  limitDate: Option[Date],
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
    val t = syntax.resultName
    new Task(id = rs.long(t.id),
      userId = rs.long(t.userId),
      name = rs.string(t.name),
      priority=rs.int(t.priority),
      isDone = rs.boolean(t.isDone),
      time = rs.long(t.time),
      limitDate = rs.dateOpt(t.limitDate),
      createdTime = rs.date(t.createdTime),
      updatedTime = rs.date(t.updatedTime))
  }

  private lazy val m = Task.syntax("m")

  def findAll()(implicit s: DBSession): List[Task] = withSQL {
    select.from(Task as m)
  }.map(Task(m)).list.apply()

  def findById(id: Long)(implicit s: DBSession): Option[Task] = withSQL {
   select.from(Task as m).where.eq(m.id, id)
  }.map(Task(m)).headOption().apply()

  def add(newTask: Task)(implicit s: DBSession): Task = {
    val now = new Date()
    val id = withSQL {
      insert.into(Task).namedValues(column.userId -> newTask.userId, column.name -> newTask.name, column.priority -> newTask.priority, column.isDone -> newTask.isDone, column.time -> newTask.time, column.limitDate -> newTask.limitDate, column.createdTime -> now, column.updatedTime -> now)
    }.updateAndReturnGeneratedKey().apply()
    findById(id).get
  }
}

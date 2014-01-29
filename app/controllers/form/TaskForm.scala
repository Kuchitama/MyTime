package controllers.form

import java.util.Date
import models.Task

/**
 * Created by k2 on 2014/01/25.
 */
case class TaskForm(name: String, priority: Int, isDone: Boolean, time: Long, limitDate: Option[Date]) {
  def toTask(userId: Long, id: Long = 0): Task = {
    new Task(id, userId, name, priority, isDone, 0, limitDate, null, null)
  }
}

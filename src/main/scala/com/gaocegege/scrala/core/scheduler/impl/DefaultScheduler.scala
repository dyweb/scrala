package com.gaocegege.scrala.core.scheduler.impl

import com.gaocegege.scrala.core.scheduler.Scheduler
import scala.collection.mutable.Queue
import com.gaocegege.scrala.core.common.request.impl.HttpRequest

/**
 * Default scheduler
 * @author gaoce
 * @notice Not thread safe
 */
class DefaultScheduler extends Scheduler {
  val queue: Queue[HttpRequest] = new Queue[HttpRequest]()

  def push(request: HttpRequest): Unit = {
    queue.enqueue(request)
  }

  def pop(): HttpRequest = {
    queue.dequeue
  }

  def count(): Int = {
    queue.size
  }
}

package com.gaocegege.scrala.core.scheduler.impl

import com.gaocegege.scrala.core.scheduler.Scheduler
import scala.collection.mutable
import com.gaocegege.scrala.core.common.request.impl.HttpRequest

/**
 * Default scheduler
 * @author gaoce
 * @notice Not thread safe
 */
class DefaultScheduler extends Scheduler {
  val queue: mutable.Queue[HttpRequest] = new mutable.Queue[HttpRequest]()

  def push(request: HttpRequest): Unit = {
    queue enqueue (request)
  }

  def pop(): HttpRequest = {
    queue dequeue
  }

  def count(): Int = {
    queue size
  }
}

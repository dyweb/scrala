package com.gaocegege.scrala.core.scheduler

import com.gaocegege.scrala.core.common.request.Request
import com.gaocegege.scrala.core.common.response.Response
import com.gaocegege.scrala.core.common.request.impl.HttpRequest

/**
 * Scheduler interface
 * @author gaoce
 */
trait Scheduler {
  def push(request: HttpRequest): Unit

  def pop(): HttpRequest

  def count(): Int
}

package com.gaocegege.scrala.core.engine.manager.status

import scala.Enumeration

object Status extends Enumeration {
  type Status = Value
  val Working, Done = Value
}

package com.gaocegege.scrala.core.middleware.filter

trait Filter {
  def filter(url: String): Boolean
}

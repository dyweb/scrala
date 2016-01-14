package com.gaocegege.scrala.core.middleware.filter

/**
 * Filter interface
 */
trait Filter {
  /** if the url passes the filter, return true, else return false */
  def filter(url: String): Boolean
}

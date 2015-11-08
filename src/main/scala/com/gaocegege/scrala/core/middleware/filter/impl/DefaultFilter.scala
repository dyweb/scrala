package com.gaocegege.scrala.core.middleware.filter.impl

import com.gaocegege.scrala.core.middleware.filter.Filter

class DefaultFilter extends Filter {
  def filter(url: String): Boolean = {
    true
  }
}

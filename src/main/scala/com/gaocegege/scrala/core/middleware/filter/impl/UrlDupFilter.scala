package com.gaocegege.scrala.core.middleware.filter.impl

import com.gaocegege.scrala.core.middleware.filter.Filter
import scala.collection.mutable
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.Logger

class UrlDupFilter extends Filter {
  private val urlSeens = mutable.Set[String]()
  private val logger = Logger(LoggerFactory.getLogger("urldupfilter"))

  def filter(url: String): Boolean = {
    if (urlSeens contains url) {
      logger.debug("[UrlDupFilter]-Deumplicated Url: " + url)
      false
    } else {
      urlSeens.add(url)
      true
    }
  }
}

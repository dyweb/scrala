package com.gaocegege.scrala.core.middleware.filter.impl

import com.gaocegege.scrala.core.middleware.filter.Filter
import scala.collection.immutable.Set
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.Logger

class UrlDupFilter extends Filter {
  var urlSeens = Set[String]()
  private val logger = Logger(LoggerFactory.getLogger("urldupfilter"))

  def filter(url: String): Boolean = {
    if (urlSeens contains url) {
      logger.debug("[UrlDupFilter]-Deumplicated Url: " + url)
      false
    } else {
      urlSeens = urlSeens + url
      true
    }
  }
}

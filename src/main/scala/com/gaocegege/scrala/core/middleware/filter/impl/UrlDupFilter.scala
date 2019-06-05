package com.gaocegege.scrala.core.middleware.filter.impl

import com.gaocegege.scrala.core.middleware.filter.Filter
import scala.collection.immutable
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.Logger

/**
 * Url duplicate filter
 */
class UrlDupFilter extends Filter {
  /** record the urls has been crawled */
  var urlSeens: immutable.Set[String] = immutable Set[String]()

  val logger = Logger(LoggerFactory getLogger ("urldupfilter"))

  def filter(url: String): Boolean = {
    if (urlSeens contains url) {
      logger debug ("Duplicate url: " + url)
      false
    } else {
      urlSeens = urlSeens + url
      true
    }
  }
}

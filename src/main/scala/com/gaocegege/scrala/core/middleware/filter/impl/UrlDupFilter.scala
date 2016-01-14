package com.gaocegege.scrala.core.middleware.filter.impl

import com.gaocegege.scrala.core.middleware.filter.Filter
import scala.collection.mutable
import org.slf4j.LoggerFactory
import com.typesafe.scalalogging.Logger

/**
 * Url duplicate filter
 */
class UrlDupFilter extends Filter {
  /** record the urls has been crawled */
  private val urlSeens = mutable Set[String]()

  private val logger = Logger(LoggerFactory getLogger ("urldupfilter"))

  def filter(url: String): Boolean = {
    if (urlSeens contains url) {
      logger debug ("Dumplicate Url: " + url)
      false
    } else {
      urlSeens add (url)
      true
    }
  }
}

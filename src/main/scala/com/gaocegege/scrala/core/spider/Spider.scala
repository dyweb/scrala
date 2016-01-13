package com.gaocegege.scrala.core.spider

import com.gaocegege.scrala.core.common.response.impl.HttpResponse
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory
import com.gaocegege.scrala.core.middleware.filter.impl.DefaultFilter
import com.gaocegege.scrala.core.middleware.filter.Filter

/**
 * Spider Trait
 */
trait Spider {
  /** the start url */
  def startUrl: List[String]
  /** middleware-filter */
  def filter: Filter = new DefaultFilter
  /** delay */
  def delay: Int = 0
  /** worker actor number */
  def workerCount = 4
  /** main function */
  def parse(response: HttpResponse): Unit
  /** run the engine */
  def begin(): Unit
  /** create a new request */
  def request(url: String, callback: (HttpResponse) => Unit): Unit

  val logger = Logger(LoggerFactory getLogger ("spider"))

}

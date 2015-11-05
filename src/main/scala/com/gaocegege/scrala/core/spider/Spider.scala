package com.gaocegege.scrala.core.spider

import com.gaocegege.scrala.core.common.response.impl.HttpResponse
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

trait Spider {
  def startUrl: List[String]
  def parse(response: HttpResponse): Unit

  val logger = Logger(LoggerFactory.getLogger("spider"))
}

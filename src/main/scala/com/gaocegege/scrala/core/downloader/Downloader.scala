package com.gaocegege.scrala.core.downloader

import com.gaocegege.scrala.core.common.response.Response
import com.gaocegege.scrala.core.common.request.Request
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

/**
 * Downloader interface
 * @author gaoce
 */
trait Downloader {
  val logger = Logger(LoggerFactory.getLogger("downloader"))
}

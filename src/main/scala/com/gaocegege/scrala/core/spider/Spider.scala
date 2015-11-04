package com.gaocegege.scrala.core.spider

import com.gaocegege.scrala.core.common.response.Response

trait Spider {
  def startUrl: List[String]
  def parse(response: Response): Unit
}

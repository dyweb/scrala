package com.gaocegege.scrala.example

import com.gaocegege.scrala.core.spider.impl.DefaultSpider
import com.gaocegege.scrala.core.common.response.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import com.gaocegege.scrala.core.common.response.impl.HttpResponse
import com.gaocegege.scrala.core.common.response.impl.HttpResponse

/**
 * @author gaoce
 */
class TestSpider extends DefaultSpider {
  override def workerCount = 1

  override def delay = 100000

  def startUrl = List[String]("http://www.gaocegege.com/resume")

  def parse(response: HttpResponse): Unit = {
    val links = (response getContentParser) select ("a")
    for (i <- 0 to links.size() - 1) {
      request(((links get (i)) attr ("href")), printIt)
    }
  }

  def printIt(response: HttpResponse): Unit = {
    println((response getContentParser) title)
  }
}

object Main {
  def main(args: Array[String]) {
    val test = new TestSpider
    test begin
  }
}

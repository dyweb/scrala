package com.gaocegege.scrala.core.spider.impl

import com.gaocegege.scrala.core.spider.Spider
import com.gaocegege.scrala.core.scheduler.impl.DefaultScheduler
import com.gaocegege.scrala.core.downloader.impl.HttpDownloader
import com.gaocegege.scrala.core.downloader.Downloader
import com.gaocegege.scrala.core.common.request.Request
import org.apache.http.client.methods.HttpGet
import com.gaocegege.scrala.core.common.request.impl.HttpRequest
import com.gaocegege.scrala.core.common.response.impl.HttpResponse
import com.gaocegege.scrala.core.engine.Engine

/**
 * Default spider
 * @author gaoce
 */
abstract class DefaultSpider extends Spider {

  private val engine = new Engine(this, new DefaultScheduler, new HttpDownloader)

  def request(url: String, callback: (HttpResponse) => Unit): Unit = {
    engine.request(url, callback)
  }

  def begin(): Unit = {
    engine.begin()
  }
}

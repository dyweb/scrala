package com.gaocegege.scrala.core.spider.impl

import com.gaocegege.scrala.core.spider.Spider
import com.gaocegege.scrala.core.scheduler.impl.DefaultScheduler
import com.gaocegege.scrala.core.downloader.impl.HttpDownloader
import com.gaocegege.scrala.core.common.request.impl.HttpRequest
import com.gaocegege.scrala.core.common.response.impl.HttpResponse
import com.gaocegege.scrala.core.engine.Engine
import akka.actor.{ ActorSystem, Props }
import com.gaocegege.scrala.core.common.util.Constant

/**
 * Default spider
 * @author gaoce
 */
abstract class DefaultSpider extends Spider {

  private val system = ActorSystem("Spider")

  private val scheduler = new DefaultScheduler

  private val engine = system actorOf (Props(new Engine(this, scheduler)), "engine")

  /**
   * push request to the scheduler
   */
  def request(url: String, callback: (HttpResponse) => Unit): Unit = {
    engine tell ((url, callback), engine)
  }

  def begin(): Unit = {
    engine tell (Constant.startMessage, engine)
  }
}

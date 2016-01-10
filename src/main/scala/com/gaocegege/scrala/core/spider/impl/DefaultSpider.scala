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
import akka.actor.ActorSystem
import akka.actor.Props
import com.gaocegege.scrala.core.common.util.Constant

/**
 * Default spider
 * @author gaoce
 */
abstract class DefaultSpider extends Spider {

  val system = ActorSystem("Spider")
  private val engine = system.actorOf(Props(new Engine(this, new DefaultScheduler)), "engine")

  /**
   * push request to the scheduler
   */
  def request(url: String, callback: (HttpResponse) => Unit): Unit = {
    engine ! (url, callback)
  }

  /**
   * Push Poisons to the scheduler
   * PS: Poison may be more than 1, but only one of them will work.
   */
  def poison(): Unit = {
    engine ! Constant.poisonMessage
  }

  def begin(): Unit = {
    engine ! Constant.startMessage
    system.awaitTermination()
  }
}

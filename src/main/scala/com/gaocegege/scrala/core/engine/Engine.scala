package com.gaocegege.scrala.core.engine

import com.gaocegege.scrala.core.spider.Spider
import com.gaocegege.scrala.core.scheduler.Scheduler
import com.gaocegege.scrala.core.downloader.Downloader
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory
import com.gaocegege.scrala.core.common.request.impl.HttpRequest
import org.apache.http.client.methods.HttpGet
import com.gaocegege.scrala.core.common.response.impl.HttpResponse
import akka.actor.Actor
import com.gaocegege.scrala.core.common.util.Constant
import com.gaocegege.scrala.core.engine.manager.DownloadManager
import akka.actor.Props
import akka.actor.ActorRef
import com.gaocegege.scrala.core.common.response.impl.HttpResponse
import com.gaocegege.scrala.core.engine.manager.DownloadManager

/**
 * Engine, responsable for ?
 * @author gaoce
 */
class Engine(val spider: Spider, val scheduler: Scheduler) extends Actor {

  private val logger = Logger(LoggerFactory.getLogger("engine"))

  private val downloaderManager: ActorRef = context.actorOf(Props(new DownloadManager(16)), "downloadermanager")

  def receive = {
    case (url: String, callback: Function1[HttpResponse, Unit]) => {
      logger.info("[Request-create]-Url: " + url)
      val newRequest = new HttpRequest(new HttpGet(url), callback)
      scheduler.push(newRequest)
      self ! Constant.resumeMessage
    }
    case Constant.endMessage => {
      if (scheduler.count() == 0) {
        context.stop(self)
      }
    }
    case Constant.startMessage => {
      spider.startUrl.map { str => scheduler.push(new HttpRequest(new HttpGet(str), spider.parse)) }
      for (i <- 1 to scheduler.count()) {
        downloaderManager ! scheduler.pop()
      }
    }
    case Constant.resumeMessage => {
      for (i <- 1 to scheduler.count()) {
        downloaderManager ! scheduler.pop()
      }
    }
    case _ => logger.warn("[Engine]-unexpected message")
  }
}

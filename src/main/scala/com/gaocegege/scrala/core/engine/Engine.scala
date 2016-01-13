package com.gaocegege.scrala.core.engine

import com.gaocegege.scrala.core.spider.Spider
import com.gaocegege.scrala.core.scheduler.Scheduler
import com.gaocegege.scrala.core.downloader.Downloader
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory
import com.gaocegege.scrala.core.common.request.impl.HttpRequest
import org.apache.http.client.methods.HttpGet
import com.gaocegege.scrala.core.common.response.impl.HttpResponse
import akka.actor.{ Props, ActorRef, Actor }
import com.gaocegege.scrala.core.common.util.Constant
import com.gaocegege.scrala.core.engine.manager.DownloadManager
import akka.actor.PoisonPill

/**
 * Engine, responsable for ?
 * @author gaoce
 */
class Engine(val spider: Spider, val scheduler: Scheduler) extends Actor {

  private val filter = spider filter

  private val logger = Logger(LoggerFactory getLogger ("Engine"))

  logger info ("thread count-" + (spider workerCount))

  private val downloaderManager: ActorRef = context actorOf (Props(new DownloadManager(self, spider workerCount)), "downloadermanager")

  def receive = {
    // request from the spider class
    case (url: String, callback: Function1[HttpResponse, Unit]) => {
      logger info ("[Request-create]-Url: " + url)
      if (filter filter (url)) {
        scheduler push (new HttpRequest(new HttpGet(url), callback))
        self tell (Constant resumeMessage, self)
      }
    }
    case (Constant.startMessage) => {
      // get all allowable urls
      for (url <- (spider startUrl)) {
        if (filter filter (url)) {
          scheduler push (new HttpRequest(new HttpGet(url), spider parse))
        }
      }
      for (i <- 1 to (scheduler count)) {
        downloaderManager tell (scheduler pop, self)
      }
    }
    case (Constant.resumeMessage) => {
      logger debug ("resume-count of scheduler: " + (scheduler count))
      for (i <- 1 to (scheduler count)) {
        downloaderManager tell (scheduler pop, self)
      }
    }
    case (Constant.workDownMessage) => {
      if ((scheduler count) == 0) {
        (context system) shutdown
      }
    }
    case _ => logger warn ("unexpected message")
  }
}

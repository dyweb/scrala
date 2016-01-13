package com.gaocegege.scrala.core.engine.manager

import akka.actor.Actor
import com.gaocegege.scrala.core.downloader.Downloader
import scala.collection.mutable
import com.gaocegege.scrala.core.downloader.impl.HttpDownloader
import akka.actor.{ Props, ActorRef }
import scala.util.Random
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory
import com.gaocegege.scrala.core.common.util.Constant
import com.gaocegege.scrala.core.engine.manager.status.Status
import com.gaocegege.scrala.core.common.request.impl.HttpRequest
import akka.actor.PoisonPill

/**
 * Downloader manager
 * @author gaoce
 */
class DownloadManager(engine: ActorRef, val threadCount: Int = 4) extends Actor {

  private val logger = Logger(LoggerFactory.getLogger("downloadmanager"))

  private var counter = 0

  /** children */
  private val workers: mutable.ListBuffer[ActorRef] = new mutable.ListBuffer[ActorRef]()

  for (i <- 1 to threadCount) {
    workers.append(context.actorOf(Props[HttpDownloader], "worker-" + i.toString()))
  }

  /**
   * request, work; end, tell me.
   */
  def receive = {
    case request: HttpRequest => {
      val index = Random.nextInt(threadCount)
      logger.info("Worker " + index + " has a new work to do")
      counter = counter - 1
      workers(index) ! (request, index)
    }
    case Constant.workDownMessage => {
      logger.info("Worker down")
      counter = counter + 1
      if (counter == 0) {
        engine tell (Constant.workDownMessage, self)
      }
    }
    case _ => {
      logger.warn("unexpected message")
    }
  }
}

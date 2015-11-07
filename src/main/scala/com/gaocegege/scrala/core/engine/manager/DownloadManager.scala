package com.gaocegege.scrala.core.engine.manager

import akka.actor.Actor
import com.gaocegege.scrala.core.downloader.Downloader
import scala.collection.mutable.ListBuffer
import com.gaocegege.scrala.core.downloader.impl.HttpDownloader
import akka.actor.Props
import akka.actor.ActorRef
import com.gaocegege.scrala.core.common.request.Request
import scala.util.Random
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory
import com.gaocegege.scrala.core.common.util.Constant
import com.gaocegege.scrala.core.engine.manager.status.Status

/**
 * Downloader manager
 * @author gaoce
 */
class DownloadManager(engine: ActorRef, val threadCount: Int = 4) extends Actor {

  private val logger = Logger(LoggerFactory.getLogger("downloadmanager"))

  /** children */
  private val workers: ListBuffer[ActorRef] = new ListBuffer[ActorRef]()

  for (i <- 1 to threadCount) {
    workers.append(context.actorOf(Props[HttpDownloader], "worker-" + i.toString()))
  }

  private val states: ListBuffer[Status.Value] = ListBuffer.fill(threadCount)(Status.Done)

  /**
   * request, work; end, tell me.
   */
  def receive = {
    case request: Request => {
      val index = Random.nextInt(threadCount)
      workers(index) ! (request, index)
      states(index) = Status.Working
    }
    case (Constant.endMessage, index: Int) => {
      states(index) = Status.Done
      if (IsAllDone()) {
        // TODO send to scheduler
        engine ! Constant.endMessage
      }
    }
    case _ => {
      logger.warn("[DownloadManager]-unexpected message")
    }
  }

  def IsAllDone(): Boolean = {
    states.forall { ele => ele == Status.Done }
  }
}

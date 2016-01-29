package com.gaocegege.scrala.core.engine.manager

import com.gaocegege.scrala.core.downloader.Downloader
import scala.collection.immutable
import com.gaocegege.scrala.core.downloader.impl.HttpDownloader
import akka.actor.{ Props, ActorRef, Actor }
import scala.util.Random
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory
import com.gaocegege.scrala.core.common.util.Constant
import com.gaocegege.scrala.core.common.request.impl.HttpRequest
import com.gaocegege.scrala.core.common.response.impl.HttpResponse
import org.apache.http.util.EntityUtils
import java.net.URI

/**
 * Downloader manager
 * @author gaoce
 */
class DownloadManager(engine: ActorRef, val threadCount: Int = 4) extends Actor {

  private val logger = Logger(LoggerFactory getLogger ("Downloadmanager"))

  /** work down count */
  private var counter = 0

  /** worker actor */
  private var workers: immutable.List[ActorRef] = immutable.List[ActorRef]()

  for (i <- 1 to threadCount) {
    workers = (context actorOf (Props[HttpDownloader], "worker-" + (i toString))) :: workers
  }

  /** call back info map */
  private var callBackMap: immutable.Map[URI, (HttpResponse) => Unit] = immutable.Map[URI, (HttpResponse) => Unit]()

  /**
   * request, work; end, tell me.
   */
  def receive = {
    case request: HttpRequest => {
      val index = Random nextInt (threadCount)
      logger info ("Worker " + index + " has a new work to do")

      // push callback function to map
      callBackMap += (((request request) getURI) -> request.callback)

      // if get a new job to do,
      counter = counter - 1

      // tell the worker to do
      workers(index) tell ((request, index), self)
    }
    case (Constant.workDownMessage, uri: URI, response: HttpResponse) => {
      // if a job has done,
      counter = counter + 1

      // do the callback function
      if (!(response isSuccess)) {
        logger.error("Error getting the response for " + uri)
      } else {
        callBackMap(uri)(response)
        val entity = (((response httpResponse) get) getEntity)
        EntityUtils consume (entity)
      }

      if (counter == 0) {
        engine tell (Constant.workDownMessage, self)
      }
    }
    case _ => {
      logger warn ("Unexpected message")
    }
  }
}

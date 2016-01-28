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
import com.gaocegege.scrala.core.common.request.impl.HttpRequest
import com.gaocegege.scrala.core.common.response.impl.HttpResponse
import org.apache.http.util.EntityUtils
import com.sun.org.apache.xpath.internal.operations.String
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
  private val workers: mutable.ListBuffer[ActorRef] = new mutable.ListBuffer[ActorRef]()

  for (i <- 1 to threadCount) {
    workers append (context actorOf (Props[HttpDownloader], "worker-" + (i toString)))
  }

  /** call back info map */
  private var callBackMap: mutable.Map[URI, (HttpResponse) => Unit] = new mutable.HashMap[URI, (HttpResponse) => Unit]()

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
      if (!(response getStatus)) {
        logger.error("Error getting the response for " + uri)
      } else {
        callBackMap(uri)(response)
        val entity = ((response getResponse) getEntity)
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

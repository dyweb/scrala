package com.gaocegege.scrala.core.engine.manager

import scala.collection.immutable
import akka.actor.{ Props, ActorRef, Actor }
import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory
import com.gaocegege.scrala.core.common.response.impl.HttpResponse
import java.net.URI

/**
 * Downloader manager
 * @author gaoce
 */
abstract class DownloadManager(val engine: ActorRef) extends Actor {

  val logger = Logger(LoggerFactory getLogger ("Downloadmanager"))

  /** work down count */
  var counter = 0

  /** worker actor */
  var workers: immutable.List[ActorRef] = immutable.List[ActorRef]()

  /** call back info map */
  var callBackMap: immutable.Map[URI, (HttpResponse) => Unit] = immutable.Map[URI, (HttpResponse) => Unit]()

}

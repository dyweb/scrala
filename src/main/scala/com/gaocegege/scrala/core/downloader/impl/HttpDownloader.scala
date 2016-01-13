package com.gaocegege.scrala.core.downloader.impl

import com.gaocegege.scrala.core.downloader.Downloader
import com.gaocegege.scrala.core.downloader.httpclient.DefaultHttpClient
import com.gaocegege.scrala.core.common.response.Response
import org.apache.http.util.EntityUtils
import org.apache.http.client.ClientProtocolException
import akka.actor.Actor
import com.gaocegege.scrala.core.common.request.impl.HttpRequest
import com.gaocegege.scrala.core.common.response.impl.HttpResponse
import com.gaocegege.scrala.core.common.util.Constant

/**
 * Http downloader
 * @author gaoce
 */
class HttpDownloader extends DefaultHttpClient with Actor with Downloader {

  def receive = {
    case (request: HttpRequest, index: Int) => {
      logger.info("Worker " + index + " working on " + request)
      download(request)
      sender tell (Constant.workDownMessage, self)
    }
    case _ => logger.info("[Downloader]-unexpected message")
  }

  def download(request: HttpRequest): Response = {
    logger.debug("[Downloading]-Url: " + request.request.getURI)
    var response: HttpResponse = new HttpResponse(false)
    try {
      response = new HttpResponse(httpClient.execute(request.request))
      logger.debug("[Downloading]-callback")
      // call back in the downloader
      request.callback(response)
      val entity = response.getResponse().getEntity()
      EntityUtils.consume(entity)
      response
    } catch {
      case e: ClientProtocolException => {
        logger.error("[Downloading]-error: ClientProtocolException")
        logger.error(e.printStackTrace().toString())
        new HttpResponse(false)
      }
    }
  }
}

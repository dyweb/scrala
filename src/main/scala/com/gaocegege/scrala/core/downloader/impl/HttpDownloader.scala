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
import scala.util.Try
import scala.util.Success
import scala.util.Failure

/**
 * Http downloader
 * @author gaoce
 */
class HttpDownloader extends DefaultHttpClient with Actor with Downloader {

  def receive = {
    case (request: HttpRequest, index: Int) => {
      logger info ("Worker " + index + " working on " + ((request request) getURI))
      val response = download(request)
      sender tell ((Constant.workDownMessage, ((request request) getURI), response), self)
    }
    case _ => logger info ("Unexpected message")
  }

  def download(request: HttpRequest): Response = {
    logger debug ("Url: " + ((request request) getURI))
    var response: HttpResponse = null
    Try(httpClient execute (request.request)) match {
      case Success(rawResponse) => {
        response = new HttpResponse(rawResponse)
        response
      }
      case Failure(ex) => {
        logger error (s"Problem rendering URL content: ${ex.getMessage}")
        response = new HttpResponse(false)
        response
      }
    }
  }
}

package com.gaocegege.scrala.core.downloader.impl

import com.gaocegege.scrala.core.downloader.Downloader
import com.gaocegege.scrala.core.common.request.Request
import com.gaocegege.scrala.core.downloader.httpclient.DefaultHttpClient
import com.gaocegege.scrala.core.common.response.Response
import com.gaocegege.scrala.core.common.request.impl.HttpRequest
import com.gaocegege.scrala.core.common.response.impl.HttpResponse

/**
 * Http downloader
 * @author gaoce
 */
class HttpDownloader extends DefaultHttpClient with Downloader {

  def download(request: HttpRequest): Response = {
    logger.debug("[Downloading]-Url: " + request.request.getURI)
    val response = new HttpResponse(httpClient.execute(request.request))
    logger.debug("[Downloading]-callback")
    request.callback(response)
    response
  }
}

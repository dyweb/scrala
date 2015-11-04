package com.gaocegege.scrala.core.downloader.impl

import com.gaocegege.scrala.core.downloader.Downloader
import com.gaocegege.scrala.core.common.request.Request
import com.gaocegege.scrala.core.downloader.httpclient.DefaultHttpClient
import com.gaocegege.scrala.core.common.response.Response
import com.gaocegege.scrala.core.common.request.impl.HttpRequest

/**
 * Http downloader
 * @author gaoce
 */
class HttpDownloader extends DefaultHttpClient with Downloader {

  def download(request: HttpRequest): Response = {
    val response = new Response {
      def httpResponse = httpClient.execute(request.request)
    }

    request.callback(response)
    response
  }
}

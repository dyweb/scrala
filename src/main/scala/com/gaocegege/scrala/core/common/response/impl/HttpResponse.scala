package com.gaocegege.scrala.core.common.response.impl

import com.gaocegege.scrala.core.common.response.Response
import org.apache.http.client.methods.CloseableHttpResponse
import org.jsoup.Jsoup
import com.gaocegege.scrala.core.common.util.ConvertTool
import org.jsoup.nodes.Document

class HttpResponse(private val httpResponse: CloseableHttpResponse) extends Response {

  /** content of the response */
  private lazy val content = ConvertTool.convertResponse2String(httpResponse)

  /** content parser */
  private val contentParser: Document = Jsoup.parse(content)

  def getResponse(): CloseableHttpResponse = {
    httpResponse
  }

  def getContentParser(): Document = {
    contentParser
  }

  def getContent(): String = {
    content
  }
}

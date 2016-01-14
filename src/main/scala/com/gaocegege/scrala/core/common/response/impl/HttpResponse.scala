package com.gaocegege.scrala.core.common.response.impl

import com.gaocegege.scrala.core.common.response.Response
import org.apache.http.client.methods.CloseableHttpResponse
import org.jsoup.Jsoup
import com.gaocegege.scrala.core.common.util.ConvertTool
import org.jsoup.nodes.Document

/**
 * Response class
 * TODO: the code is in bad stayle
 */
class HttpResponse extends Response {

  private var httpResponse: CloseableHttpResponse = _

  /** content of the response */
  private lazy val content = ConvertTool.convertResponse2String(httpResponse)

  /** content parser */
  private lazy val contentParser: Document = Jsoup.parse(content)

  private var isSuccess = true

  def this(_isSuccess: Boolean) = {
    this()
    this.isSuccess = _isSuccess
  }

  def this(_httpResponse: CloseableHttpResponse) = {
    this(true)
    httpResponse = _httpResponse
  }

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

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

  private var httpResponse: Option[CloseableHttpResponse] = None

  /** content of the response */
  private lazy val content = ConvertTool.convertResponse2String(httpResponse get)

  /** content parser */
  private lazy val contentParser: Document = Jsoup.parse(content)

  private var isSuccess = true

  def this(isSuccessParam: Boolean) = {
    this()
    this.isSuccess = isSuccessParam
  }

  def this(httpResponseParam: CloseableHttpResponse) = {
    this(true)
    httpResponse = Some(httpResponseParam)
  }

  def getResponse(): CloseableHttpResponse = {
    httpResponse get
  }

  def getContentParser(): Document = {
    contentParser
  }

  def getContent(): String = {
    content
  }

  def getStatus(): Boolean = {
    isSuccess
  }
}

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
class HttpResponse(val httpResponse: Option[CloseableHttpResponse], val isSuccess: Boolean) extends Response {

  /** content of the response */
  lazy val content = ConvertTool.convertResponse2String(httpResponse get)

  /** content parser */
  lazy val contentParser: Document = Jsoup.parse(content)

  def this(isSuccessParam: Boolean) = {
    this(None, isSuccessParam)
  }

  def this(httpResponseParam: CloseableHttpResponse) = {
    this(Some(httpResponseParam), true)
  }
}

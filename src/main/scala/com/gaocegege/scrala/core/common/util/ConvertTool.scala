package com.gaocegege.scrala.core.common.util

import org.apache.http.client.methods.CloseableHttpResponse
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * convert tool
 * @author gaoce
 */
object ConvertTool {
  /**
   * convert the httpresponse to text
   * @param response
   * @return string
   */
  def convertResponse2String(response: CloseableHttpResponse): String = {
    val rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()))
    var content = ""
    var line = rd.readLine()
    while (line != null) {
      content += line
      line = rd.readLine()
    }
    content
  }
}

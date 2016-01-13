package com.gaocegege.scrala.core.downloader.httpclient

import org.apache.http.impl.client.HttpClients
import org.apache.http.client.methods.HttpUriRequest

/**
 * Default Http Client
 * @author gaoce
 */
class DefaultHttpClient() {
  val httpClient = HttpClients createDefault
}

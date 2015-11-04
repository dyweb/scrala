package com.gaocegege.scrala.core.common.request.impl

import com.gaocegege.scrala.core.common.request.Request
import com.gaocegege.scrala.core.common.response.Response
import org.apache.http.client.methods.HttpUriRequest

class HttpRequest(val request: HttpUriRequest, val callback: (Response) => Unit) extends Request {
}

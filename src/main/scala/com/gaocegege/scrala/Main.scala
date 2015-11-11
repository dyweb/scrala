import com.gaocegege.scrala.core.spider.impl.DefaultSpider
import com.gaocegege.scrala.core.common.response.Response
import java.io.BufferedReader
import java.io.InputStreamReader
import com.gaocegege.scrala.core.common.response.impl.HttpResponse
import com.gaocegege.scrala.core.common.response.impl.HttpResponse
import org.json4s._
import org.json4s.native.JsonMethods
import java.io.PrintWriter
import java.io.File

/**
 * get sjtu coders from github
 * @author gaoce
 */
class SJTUSpider extends DefaultSpider {

  override var threadCount: Int = 1

  def getUrlByLocation(location: String): String = {
    "https://api.github.com/search/users?q=location:%22" + location + "%22"
  }

  def startUrl = List[String](getUrlByLocation("shanghai+jiaotong"), getUrlByLocation("shanghai+jiao+tong"))

  def parse(response: HttpResponse): Unit = {
    val json = JsonMethods.parse(response.getContent())
    (for {
      JObject(child) <- json
      JField("url", JString(url)) <- child
    } yield url).foreach {
      (url: String) =>
        {
          request(url, getUserDetail)
        }
    }
  }

  def getUserDetail(response: HttpResponse): Unit = {
    val json = JsonMethods.parse(response.getContent())
    val writer = new PrintWriter(new File("dump.txt"))
    writer.append((json \ "login").values + "\t" + (json \ "home_url").values + "\t" + (json \ "followers").values + "\n")
    writer.close()
    println((json \ "login") + (json \ "followers").toString() + "\n")
  }
}

object Main {
  def main(args: Array[String]) {
    val test = new SJTUSpider
    test.begin
  }
}

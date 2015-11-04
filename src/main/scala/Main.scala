import com.gaocegege.scrala.core.spider.impl.DefaultSpider
import com.gaocegege.scrala.core.common.response.Response
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * @author gaoce
 */
class TestSpider extends DefaultSpider {
  def startUrl = List[String]("http://www.baidu.com")

  def parse(response: Response): Unit = {
    println(response.httpResponse)
  }
}

object Main {
  def main(args: Array[String]) {
    val test = new TestSpider
    test.begin
  }
}

# scrala

scrala is a web crawling framework for scala, which is inspired by [scrapy](https://github.com/scrapy/scrapy).

## requirements

* scala 2.11.5
* sbt 0.13

## install

### easy way
In your build.sbt:

	libraryDependencies += "com.gaocegege" % "scrala_2.11" % "0.1"

You can get the example project in the `./example/`

### normal way

	git clone https://github.com/gaocegege/scrala.git
	cd ./scrala
	sbt assembly

You will get the jar in `./target/scala-<version>/`

## example

	class TestSpider extends DefaultSpider {
	  def startUrl = List[String]("http://www.gaocegege.com/resume")

	  def parse(response: HttpResponse): Unit = {
	    val links = response.getContentParser().select("a")
	    for (i <- 0 to links.size() - 1) {
	      request(links.get(i).attr("href"), printIt)
	    }
	  }

	  def printIt(response: HttpResponse): Unit = {
	    println(response.getContentParser().title())
	  }
	}

Just like the scrapy, what you need to do is define a `startUrl` to tell me where to start, and override `parse(...)` to parse the response of the startUrl. And `request(...)` function is like `yield scrapy.Request(...)` in scrapy.

You can get the example project in the `./example/`

## for developer

### roadmap

Next version is 0.2, which is also a dev version

1. keep the log output simple and stupid
2. add tests to keep the multi thread downloader high performance 
3. add new feature: rules in the spider
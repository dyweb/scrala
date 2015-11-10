# scrala

[![Join the chat at https://gitter.im/gaocegege/scrala](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/gaocegege/scrala?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

scrala is a web crawling framework for scala, which is inspired by [scrapy](https://github.com/scrapy/scrapy).

## requirements

* scala 2.11.5
* sbt 0.13

## install

### easy way

**Step 1.** Add the JitPack repository to your build file

	resolvers += "jitpack" at "https://jitpack.io"

**Step 2.** Add the dependency in the form

	libraryDependencies += "com.github.gaocegege" % "scrala" % "0.1"

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
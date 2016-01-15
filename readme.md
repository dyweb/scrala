# scrala

[![Codacy Badge](https://api.codacy.com/project/badge/grade/563bbcd12d874610bca7313abe6e6fdd)](https://www.codacy.com/app/gaocegege/scrala)
[![Build Status](https://travis-ci.org/gaocegege/scrala.svg?branch=master)](https://travis-ci.org/gaocegege/scrala)
![License](https://img.shields.io/pypi/l/Django.svg)
[![scrala published](https://jitpack.io/v/gaocegege/scrala.svg)](https://jitpack.io/#gaocegege/scrala)
[![Docker Supported](https://img.shields.io/badge/docker-supported-blue.svg)](https://hub.docker.com/r/gaocegege/scrala/)
[![Join the chat at https://gitter.im/gaocegege/scrala](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/gaocegege/scrala?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

scrala is a web crawling framework for scala, which is inspired by [scrapy](https://github.com/scrapy/scrapy).

## install

### the docker way

[![](https://badge.imagelayers.io/gaocegege/scrala:latest.svg)](https://imagelayers.io/?images=gaocegege/scrala:latest 'Get your own badge on imagelayers.io')

[gaocegege/scrala in dockerhub](https://hub.docker.com/r/gaocegege/scrala/)

#### Create a Dockerfile in your project.

```
FROM gaocegege/scrala:latest

// COPY the build.sbt and the src to the container
```

#### Run a single command in docker

```
docker run -v <your src>:/app/src -v <your ivy2 directory>:/root/.ivy2  gaocegege/scrala
```

### the sbt way

**Step 1.** Add it in your build.sbt at the end of resolvers:

	resolvers += "jitpack" at "https://jitpack.io"

**Step 2.** Add the dependency

	libraryDependencies += "com.github.gaocegege" % "scrala" % "0.1.5"

### normal way

	git clone https://github.com/gaocegege/scrala.git
	cd ./scrala
	sbt assembly

You will get the jar in `./target/scala-<version>/`.

## example

	import com.gaocegege.scrala.core.spider.impl.DefaultSpider
	import com.gaocegege.scrala.core.common.response.Response
	import java.io.BufferedReader
	import java.io.InputStreamReader
	import com.gaocegege.scrala.core.common.response.impl.HttpResponse
	import com.gaocegege.scrala.core.common.response.impl.HttpResponse

	class TestSpider extends DefaultSpider {
	  def startUrl = List[String]("http://www.gaocegege.com/resume")

	  def parse(response: HttpResponse): Unit = {
	    val links = (response getContentParser) select ("a")
	    for (i <- 0 to links.size() - 1) {
	      request(((links get (i)) attr ("href")), printIt)
	    }
	  }

	  def printIt(response: HttpResponse): Unit = {
	    println((response getContentParser) title)
	  }
	}

	object Main {
	  def main(args: Array[String]) {
	    val test = new TestSpider
	    test begin
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

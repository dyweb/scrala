lazy val root = (project in file(".")).
  settings(
    name := "scrala",
    version := "1.0",
    scalaVersion := "2.11.7",
    libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.1",
    libraryDependencies += "org.jsoup" % "jsoup" % "1.8.2",
    libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4-M1",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.2",
    libraryDependencies += "org.json4s" %% "json4s-native" % "3.3.0"
  )
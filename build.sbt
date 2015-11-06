lazy val root = (project in file(".")).
  settings(
    name := "scrala",
    organization := "com.gaocegege",
    version := "0.1",
    scalaVersion := "2.11.6",

    publishMavenStyle := true,
    publishTo <<= version { (v: String) =>
      val nexus = "https://oss.sonatype.org/"
      if (v.trim.endsWith("SNAPSHOT"))
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
    },
    pomIncludeRepository := { _ => false },
    pomExtra := (
      <groupId>com.gaocegege</groupId>
      <url>https://github.com/gaocegege/scrala</url>
      <licenses>
        <license>
          <name>BSD-style</name>
          <url>http://www.opensource.org/licenses/bsd-license.php</url>
          <distribution>repo</distribution>
        </license>
      </licenses>
      <scm>
        <url>git@github.com:gaocegege/scrala.git</url>
        <connection>scm:git:git@github.com:gaocegege/scrala.git</connection>
      </scm>
      <developers>
        <developer>
          <id>gaocegege</id>
          <name>Ce Gao</name>
          <url>http://gaocegege.com</url>
        </developer>
      </developers>),
    credentials += Credentials("Sonatype Nexus Repository Manager",
                           "oss.sonatype.org",
                           "gaocegege",
                           "Zz119911"),

    licenses := Seq("BSD-style" -> url("http://www.opensource.org/licenses/bsd-license.php")),

    homepage := Some(url("https://github.com/gaocegege/scrala")),

    libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.1",
    libraryDependencies += "org.jsoup" % "jsoup" % "1.8.2",
    libraryDependencies += "com.typesafe.akka" % "akka-actor_2.11" % "2.4-M1",
    libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.2"
  )
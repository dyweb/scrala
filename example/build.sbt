lazy val root = (project in file(".")).
  settings(
    name := "scrala-example",
    organization := "com.gaocegege",
    version := "0.1",
    scalaVersion := "2.11.6",

    libraryDependencies += "com.github.gaocegege" % "scrala" % "0.1"
  )

resolvers += "jitpack" at "https://jitpack.io"
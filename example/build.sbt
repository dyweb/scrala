lazy val root = (project in file(".")).
  settings(
    name := "scrala-example",
    organization := "com.gaocegege",
    version := "0.1",
    scalaVersion := "2.11.6",

    libraryDependencies += "com.gaocegege" % "scrala_2.11" % "0.1.1"
  )
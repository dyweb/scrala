lazy val root = (project in file(".")).
  settings(
    name := "scrala-example-github",
    version := "1.0",
    scalaVersion := "2.11.7",
    resolvers += "jitpack" at "https://jitpack.io",
    libraryDependencies += "com.github.gaocegege" % "scrala" % "0.1.5"
  )

name := """Priam"""
version := "1.0-SNAPSHOT"
scalaVersion := "2.11.8"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)


libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "3.0.3" % Test,
  "com.outworkers" % "util-testing_2.11" % "0.30.1" % Test,
  "com.outworkers" % "phantom-dsl_2.11" % "2.9.1",
  "com.typesafe" % "config" % "1.3.1",
  "com.typesafe.play" % "play-json_2.11" % "2.5.15",
  "org.cvogt" % "play-json-extensions_2.11" % "0.8.0",
  "com.typesafe.akka" %% "akka-http" % "10.0.7",
  "com.typesafe.akka" % "akka-http-testkit_2.11" % "10.0.7"
)

PhantomSbtPlugin.projectSettings
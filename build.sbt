name := """Priam"""
version := "1.0-SNAPSHOT"
scalaVersion := "2.12.0"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)

lazy val Versions = new {
  val akka = "10.0.7"
  val phantom = "2.10.0"
}

packageName in Docker := "cassandra_rest_api"
version in Docker := "latest"
maintainer in Docker := "Big Data Spark Ninja Team"
dockerRepository := Some("faiaz")


libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.12" % "3.0.3" % Test,
  "com.outworkers" % "util-testing_2.12" % "0.36.0" % Test,
  "com.outworkers" % "phantom-dsl_2.12" % Versions.phantom,
  "com.outworkers" % "phantom-streams_2.12" % Versions.phantom,
  "com.typesafe" % "config" % "1.3.1",
  "com.typesafe.akka" % "akka-http_2.12" % Versions.akka,
  "com.typesafe.akka" % "akka-http-spray-json_2.12" % Versions.akka,
  "com.typesafe.akka" % "akka-http-testkit_2.12" % Versions.akka,
  "com.typesafe.scala-logging" % "scala-logging_2.12" % "3.5.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

PhantomSbtPlugin.projectSettings
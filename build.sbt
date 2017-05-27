name := """Priam"""
version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "3.0.3" % Test,
  "com.outworkers" % "phantom-dsl_2.11" % "2.9.1",
  "com.typesafe" % "config" % "1.3.1"
)

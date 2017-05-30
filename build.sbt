name := """Priam"""
version := "1.0-SNAPSHOT"
scalaVersion := "2.11.8"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .enablePlugins(JavaAppPackaging)

lazy val Versions = new {
  val phantom = "2.9.1"
  val util = "0.30.1"
}

libraryDependencies ++= Seq(
  "org.scalatestplus" % "play_2.11" % "1.4.0" % Test,
  "com.outworkers" % "util-testing_2.11" % Versions.util % Test,
  "com.outworkers" % "phantom-dsl_2.11" % Versions.phantom,
  "com.typesafe" % "config" % "1.3.1"
)

PhantomSbtPlugin.projectSettings

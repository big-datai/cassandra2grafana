name := """Priam"""
version := "1.0-SNAPSHOT"
scalaVersion := "2.11.8"

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)

lazy val Versions = new {
  val akka = "10.0.7"
}

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.11" % "3.0.3" % Test,
  "com.outworkers" % "util-testing_2.11" % "0.30.1" % Test,
  "com.outworkers" % "phantom-dsl_2.11" % "2.9.1",
  "com.typesafe" % "config" % "1.3.1",
  "com.typesafe.akka" %% "akka-http" % Versions.akka,
  "com.typesafe.akka" %% "akka-http-spray-json" % Versions.akka,
  "com.typesafe.akka" % "akka-http-testkit_2.11" % Versions.akka
)

PhantomSbtPlugin.projectSettings
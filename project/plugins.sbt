def websudosPattern = {
  val pList = List("[organisation]/[module](_[scalaVersion])(_[sbtVersion])/[revision]/[artifact]-[revision](-[classifier]).[ext]")
  Patterns(pList, pList, isMavenCompatible = true)
}

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.typesafeRepo("releases"),
  Resolver.bintrayRepo("websudos", "oss-releases"),
  Resolver.url("Maven ivy Websudos", url(Resolver.DefaultMavenRepositoryRoot))(websudosPattern),
  Resolver.jcenterRepo
)

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.1.6")
addSbtPlugin("com.websudos" %% "phantom-sbt" % "1.27.0")
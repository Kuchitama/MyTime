import CoverallsPlugin.CoverallsKeys._

name := "MyTime"

version := "1.0-SNAPSHOT"

resolvers ++= Seq(
    "Maven Central" at "http://repo1.maven.org/maven2"
)

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "com.github.seratch" %% "scalikejdbc"             % "[1.6,)",
  "com.github.seratch" % "scalikejdbc-play-plugin_2.10" % "[1.6,)",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "com.github.tototoshi" %% "play-flyway" % "0.2.0"
)

play.Project.playScalaSettings

seq(ScctPlugin.instrumentSettings : _*)

seq(CoverallsPlugin.singleProject: _*)


coverallsToken := "05JLWpveqGExazfnNFAcO7GkgLivhh8K0"

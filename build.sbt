import CoverallsPlugin.CoverallsKeys._

name := "MyTime"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  "com.github.seratch" %% "scalikejdbc"             % "[1.6,)",
  "com.github.seratch" %% "scalikejdbc-play-plugin" % "[1.6,)",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
)     

play.Project.playScalaSettings

seq(ScctPlugin.instrumentSettings : _*)

seq(CoverallsPlugin.singleProject: _*)


coverallsToken := "05JLWpveqGExazfnNFAcO7GkgLivhh8K0"

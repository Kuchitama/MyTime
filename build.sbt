import CoverallsPlugin.CoverallsKeys._

name := "MyTime"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache
)     

play.Project.playScalaSettings

seq(ScctPlugin.instrumentSettings : _*)

seq(CoverallsPlugin.singleProject: _*)


coverallsToken := "05JLWpveqGExazfnNFAcO7GkgLivhh8K0"

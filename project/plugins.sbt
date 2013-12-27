// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository 
resolvers ++= Seq(
    DefaultMavenRepository,
    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
    "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
    "Sonatype releases" at "http://oss.sonatype.org/content/repositories/releases/",
    Classpaths.typesafeResolver,
    "scct-github-repository" at "http://mtkopone.github.com/scct/maven-repo"
)


// Use the Play sbt plugin for Play projects
addSbtPlugin("com.typesafe.play" %% "sbt-plugin" % "2.2.1")

// addSbtPlugin("reaktor" %% "sbt-scct" % "0.2.1")
addSbtPlugin("com.github.scct" %% "sbt-scct" % "0.2")

// addSbtPlugin("com.github.theon" % "xsbt-coveralls-plugin_2.9.2_0.12" % "0.0.4")

addSbtPlugin("com.github.theon" %% "xsbt-coveralls-plugin" % "0.0.5-SNAPSHOT")


// Comment to get more information during initialization
logLevel := Level.Warn

//scalaVersion := "2.9.2"

// The Typesafe repository
resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

 
resolvers += "sonatype-public" at "https://oss.sonatype.org/​content/repositories/public"

// Use the Play sbt plugin for Play projects
addSbtPlugin("play" % "sbt-plugin" % "2.0.4")

//addSbtPlugin("com.typesafe.startscript" % "xsbt-start-script-plugin" % "0.5.0")
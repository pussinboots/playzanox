import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {
    val playVersion     = "2.2.3"
    val appName         = "playzanox"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
        "commons-io" % "commons-io" % "1.3.2",
        "net.databinder.dispatch" % "core_2.9.2" % "0.9.1",
        "io.spray" %  "spray-json_2.9.2" % "1.2.3"
    )
    val main = play.Project(appName, appVersion, appDependencies).settings(
      // Add your own project settings here   
       resolvers += "spray repo" at "http://repo.spray.io/"
    )
}

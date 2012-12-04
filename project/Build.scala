import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "playzanox"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
        "commons-io" % "commons-io" % "1.3.2",
        "net.databinder.dispatch" % "core_2.9.2" % "0.9.1",
        "io.spray" %  "spray-json_2.9.2" % "1.2.3"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here   
       resolvers += "spray repo" at "http://repo.spray.io/"
    )
}

import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {
    import sbtscalaxb.Plugin._
    import ScalaxbKeys._
    
    val playVersion     = "2.2.3"
    val appName         = "playzanox"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      // Add your project dependencies here,
        "commons-io" % "commons-io" % "1.3.2",
        "net.databinder.dispatch" %% "dispatch-core" % "0.10.1",
        "io.spray" %% "spray-json" % "1.2.3"
    )
    val main = play.Project(appName, appVersion, appDependencies, settings = playScalaSettings ++ scalaxbSettings)
	.settings(
	      	// Add your own project settings here   
	       	resolvers += "spray repo" at "http://repo.spray.io/",
		sourceGenerators in Compile <+= scalaxb in Compile,
		packageName in scalaxb in Compile := "soap.connect",
		//protocolPackageName in scalaxb in Compile := Some("soap.connect"),
		packageDir in scalaxb in Compile := false,
		sourceManaged in scalaxb in Compile <<= baseDirectory { _ / "app/soap/connect" }
    )
}

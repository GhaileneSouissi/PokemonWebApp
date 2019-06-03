name := """play-scala-forms-example"""

version := "2.7.x"

scalaVersion := "2.11.8"

crossScalaVersions := Seq("2.11.12", "2.12.8")

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies += guice
libraryDependencies += ws
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2" % Test
libraryDependencies += "org.twitter4j" % "twitter4j-core" % "4.0.2"
libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.4.1"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.4.3" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-streaming-twitter" % "1.6.3"
libraryDependencies += "net.liftweb" % "lift-json_2.11" % "2.6.2"

scalacOptions ++= Seq(
    "-feature",
    "-deprecation",
    "-Xfatal-warnings"
)

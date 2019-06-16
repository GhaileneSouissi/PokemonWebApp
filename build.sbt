name := "PokemonApi"

version := "0.1"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % "10.1.8",
  "com.typesafe.akka" %% "akka-stream" % "2.5.19",
  "org.scalaj" %% "scalaj-http" % "2.4.1",
  "com.typesafe.play" %% "play-json" % "2.7.2",
  "com.google.guava" % "guava" % "12.0",
  "net.liftweb" % "lift-json_2.11" % "2.6.2",
  "com.typesafe.akka" %% "akka-http-core" % "2.4.5",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.5",
  "com.typesafe.akka" %% "akka-http-testkit" % "2.4.5" % "test",
  "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.5"

)
name := """Reservation-System"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

//PlayKeys.playVersion := "2.9.4"

scalaVersion := "2.13.16"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.2" % Test
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.6.1"
libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "5.4.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.4.0"
)
libraryDependencies += "org.postgresql" % "postgresql" % "42.7.7"
libraryDependencies += "com.typesafe.play" %% "filters-helpers" % "2.8.22"

libraryDependencies += "com.github.etaty" %% "rediscala" % "1.9.0"


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"

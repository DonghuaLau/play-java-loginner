name := """play-java-loginner"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  evolutions,
  javaJdbc,
  cache,
  javaWs
)

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.11"

libraryDependencies += javaJdbc
libraryDependencies += cache
libraryDependencies += javaWs
libraryDependencies += "javax.persistence" % "persistence-api" % "1.0.2"
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.18"
libraryDependencies += "org.avaje" % "ebean" % "2.7.3"
libraryDependencies += "org.webjars" % "bootstrap" % "3.3.4"
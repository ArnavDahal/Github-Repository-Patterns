name := "CS 441 Final Project"

version := "1.0"

scalaVersion := "2.11.7"
ivyScala := ivyScala.value map {
  _.copy(overrideScalaVersion = true)
}

libraryDependencies += "org.mongodb" %% "casbah" % "3.1.1"
libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.3.0"
libraryDependencies += "io.spray" %% "spray-json" % "1.3.2"
libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.12"
libraryDependencies += "org.eclipse.jgit" % "org.eclipse.jgit" % "4.5.0.201609210915-r"
libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % "test"
libraryDependencies += "mysql" % "mysql-connector-java" % "6.0.5"
libraryDependencies += "org.json" % "json" % "20160810"
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.21"
libraryDependencies += "org.slf4j" % "slf4j-simple" % "1.7.21"
libraryDependencies += "org.clapper" % "grizzled-slf4j_2.10" % "1.3.0"
libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.7"
libraryDependencies += "org.scala-lang.modules" % "scala-xml_2.11" % "1.0.4"
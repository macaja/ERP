import sbt._

object Dependencies {
  lazy val pureConfig = "com.github.pureconfig" %% "pureconfig" % "0.9.1"
  lazy val slick = "com.typesafe.slick" %% "slick" % "3.2.3"
  lazy val mysql  = "mysql" % "mysql-connector-java" % "5.1.34"
  lazy val akkaHttp = "com.typesafe.akka" %% "akka-http"   % "10.1.1"
  lazy val akkaHttpTestKit = "com.typesafe.akka" %% "akka-http-testkit" % "10.1.1"
  lazy val akkaActor = "com.typesafe.akka" %% "akka-actor" % "2.5.12"
  lazy val akkaStream = "com.typesafe.akka" %% "akka-stream" % "2.5.12"
  lazy val akkaHttpCirce = "de.heikoseeberger" %% "akka-http-circe" % "1.20.1"
  lazy val circeGeneric = "io.circe" %% "circe-generic" % "0.9.3"
  lazy val catsCore = "org.typelevel" %% "cats-core" % "1.1.0"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.5"
  lazy val slf4j =   "org.slf4j" % "slf4j-nop" % "1.6.4"
}

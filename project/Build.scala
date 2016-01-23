import sbt._
import Keys._
import Dependencies._

object Build extends Build {

  val buildSettings = Seq(
    scalaVersion := "2.11.7",
    scalacOptions ++= Seq("-feature", "-deprecation", "-language:implicitConversions", "-language:postfixOps"),
    libraryDependencies ++= coreDependencies
  ) ++ Defaults.coreDefaultSettings
  
  lazy val root = project in file(".") aggregate(akka_http_aws, akka_http_dynamodb)
  
  lazy val akka_http_aws = Project(
    id = "akka-http-aws",
    base = file("akka-http-aws"),
    settings = buildSettings
  )
  
  lazy val akka_http_dynamodb = Project(
    id = "akka-http-dynamodb",
    base = file("akka-http-dynamodb"),
    settings = buildSettings ++ Seq(
      resolvers ++= Seq("DynamoDBLocal" at "http://dynamodb-local.s3-website-us-west-2.amazonaws.com/release"),
      libraryDependencies += dynamodb_local
    )
  ).dependsOn(akka_http_aws)

}

object Dependencies {
  val aws_sdk_version = "1.10.49"
  val akka_version = "2.4.1"
  val akka_stream_version = "2.0.2"
  val scalatest_version = "2.2.6"
  val aws = "com.amazonaws" % "aws-java-sdk" % aws_sdk_version % "compile"
  val dynamodb_local = "com.amazonaws" % "DynamoDBLocal" % "1.10.5.1" % "test"
  val akka = "com.typesafe.akka" %% "akka-actor" % akka_version % "compile"
  val akka_stream = "com.typesafe.akka" %% "akka-stream-experimental" % akka_stream_version % "compile"
  val akka_http_core = "com.typesafe.akka" %% "akka-http-core-experimental" % akka_stream_version % "compile"
  val akka_testkit = "com.typesafe.akka" %% "akka-testkit" % akka_version % "test"
  val scalaTest   = "org.scalatest" %% "scalatest" % scalatest_version % "test"

  val coreDependencies = Seq(aws, akka, akka_stream, akka_http_core, akka_testkit, scalaTest)
}

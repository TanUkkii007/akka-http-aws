import sbt._

object Dependencies {
  val aws_sdk_version = "1.11.31"
  val akka_version = "2.4.9"
  val akka_stream_version = "2.0.2"
  val scalatest_version = "2.2.6"
  val awssdk_core = "com.amazonaws" % "aws-java-sdk-core" % aws_sdk_version % "compile"
  val awssdk_elb = "com.amazonaws" % "aws-java-sdk-elasticloadbalancing" % aws_sdk_version % "compile"
  val awssdk_dynamodb = "com.amazonaws" % "aws-java-sdk-dynamodb" % aws_sdk_version % "compile"
  val akka = "com.typesafe.akka" %% "akka-actor" % akka_version % "compile"
  val akka_stream = "com.typesafe.akka" %% "akka-stream" % akka_version % "compile"
  val akka_http_core = "com.typesafe.akka" %% "akka-http-core" % akka_version % "compile"
  val akka_testkit = "com.typesafe.akka" %% "akka-testkit" % akka_version % "test"
  val scalaTest   = "org.scalatest" %% "scalatest" % scalatest_version % "test"

  val coreDependencies = Seq(awssdk_core, akka, akka_stream, akka_http_core)
  val testDependencies = Seq(akka_testkit, scalaTest)
}
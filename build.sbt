import Dependencies._

val commonSettings = Seq(
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-feature", "-deprecation", "-language:implicitConversions", "-language:postfixOps"),
  organization := "github.com/TanUkkii007",
  licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
  homepage := Some(url("https://github.com/TanUkkii007/akka-http-aws"))
)


val noPublishSettings = Seq(
  publish := (),
  publishArtifact in Compile := false
)

lazy val root = project in file(".") settings(noPublishSettings) aggregate(`akka-http-aws-core`, `akka-http-aws-elasticloadbalancing`, `akka-http-aws-dynamodb`)

lazy val `akka-http-aws-core` = (project in file("akka-http-aws-core"))
  .settings(commonSettings)
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .settings(
    libraryDependencies ++= coreDependencies ++ testDependencies
  )

lazy val `akka-http-aws-elasticloadbalancing` = (project in file("akka-http-aws-elasticloadbalancing"))
  .settings(commonSettings)
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .settings(
    Seq(
      libraryDependencies ++= Seq(
        awssdk_elb
      ) ++ testDependencies
    )
  ).dependsOn(`akka-http-aws-core`)

lazy val `akka-http-aws-dynamodb` = (project in file("akka-http-aws-dynamodb"))
  .settings(commonSettings)
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .settings(
    Seq(
    libraryDependencies ++= Seq(
      awssdk_dynamodb
    ) ++ testDependencies
  ) ++ DynamoDBLocal.settings ++ Seq(
    DynamoDBLocal.Keys.dynamoDBLocalDownloadDirectory := file("dynamodb-local"),
    test in Test <<= (test in Test).dependsOn(DynamoDBLocal.Keys.startDynamoDBLocal)
  )
).dependsOn(`akka-http-aws-core`)
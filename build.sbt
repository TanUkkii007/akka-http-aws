import Dependencies._

val commonSettings = Seq(
  scalaVersion := "2.11.8",
  scalacOptions ++= Seq("-feature", "-deprecation", "-language:implicitConversions", "-language:postfixOps")
)

lazy val root = project in file(".") aggregate(`akka-http-aws-core`, `akka-http-elasticloadbalancing`, `akka-http-dynamodb`)

lazy val `akka-http-aws-core` = (project in file("akka-http-aws-core"))
  .settings(commonSettings)
  .configs(IntegrationTest)
  .settings(Defaults.itSettings: _*)
  .settings(
    libraryDependencies ++= coreDependencies ++ testDependencies
  )

lazy val `akka-http-elasticloadbalancing` = (project in file("akka-http-elasticloadbalancing"))
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

lazy val `akka-http-dynamodb` = (project in file("akka-http-dynamodb"))
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
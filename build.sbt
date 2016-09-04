import Dependencies._

val commonSettings = Seq(
  scalaVersion := "2.11.7",
  scalacOptions ++= Seq("-feature", "-deprecation", "-language:implicitConversions", "-language:postfixOps"),
  libraryDependencies ++= coreDependencies
)

lazy val root = project in file(".") aggregate(`akka-http-aws`, `akka-http-dynamodb`)

lazy val `akka-http-aws` = (project in file("akka-http-aws"))
  .settings(commonSettings)

lazy val `akka-http-dynamodb` = (project in file("akka-http-dynamodb"))
  .settings(commonSettings)
  .settings(
    Seq(
    libraryDependencies ++= Seq(
      awssdk_dynamodb
    )
  ) ++ DynamoDBLocal.settings ++ Seq(
    DynamoDBLocal.Keys.dynamoDBLocalDownloadDirectory := file("dynamodb-local"),
    test in Test <<= (test in Test).dependsOn(DynamoDBLocal.Keys.startDynamoDBLocal)
  )
).dependsOn(`akka-http-aws`)
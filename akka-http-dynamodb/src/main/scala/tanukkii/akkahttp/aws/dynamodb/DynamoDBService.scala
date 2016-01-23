package tanukkii.akkahttp.aws.dynamodb

import tanukkii.akkahttp.aws.AWSService

case class DynamoDBService() extends AWSService {
  val name: String = "dynamodb"
}

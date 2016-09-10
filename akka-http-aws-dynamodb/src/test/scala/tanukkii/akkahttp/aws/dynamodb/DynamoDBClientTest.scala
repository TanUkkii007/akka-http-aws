package tanukkii.akkahttp.aws.dynamodb

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.testkit.TestKit
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.internal.StaticCredentialsProvider
import com.amazonaws.services.dynamodbv2.model._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Span}
import org.scalatest.{Matchers, DiagrammedAssertions, WordSpecLike, BeforeAndAfterAll}
import tanukkii.akkahttp.aws.{ConnectionSettings, HttpConnectionFlow}
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.collection.JavaConverters._

class DynamoDBClientTest extends TestKit(ActorSystem("DynamoDBClientTest"))
with WordSpecLike
with DiagrammedAssertions
with Matchers
with BeforeAndAfterAll
with ScalaFutures {


  override implicit def patienceConfig: PatienceConfig = PatienceConfig(Span(3000, Millis), Span(500, Millis))

  implicit val materializer = ActorMaterializer()

  implicit var connectionFlow: HttpConnectionFlow = _

  override def beforeAll() = {
    connectionFlow = HttpConnectionFlow(
      ConnectionSettings(
        scheme = "http",
        host = "localhost",
        port = 8000,
        credentialsProvider = new StaticCredentialsProvider(new BasicAWSCredentials("aws_access_key_id", "aws_secret_access_key"))
      ),
      DynamoDBService()
    )
  }

  override def afterAll() = {

  }

  "DynamoDBClient" must {

    val tableName = "test-table"

    "createTable" in {
      val keySchema: List[KeySchemaElement] = List(
        new KeySchemaElement().withAttributeName("Id").withKeyType(KeyType.HASH)
      )
      val attributes = List(
        new AttributeDefinition().withAttributeName("Id").withAttributeType("N")
      )
      val result = DynamoDBClient.createTable(
        new CreateTableRequest(tableName, keySchema.asJava)
          .withAttributeDefinitions(attributes.asJava)
          .withProvisionedThroughput(new ProvisionedThroughput()
            .withReadCapacityUnits(5L)
            .withWriteCapacityUnits(6L))
      ).futureValue
      val desc = result.getTableDescription
      desc.getKeySchema should be(keySchema.asJava)
      desc.getAttributeDefinitions should be(attributes.asJava)
    }

    "listTables" in {
      DynamoDBClient.listTables(new ListTablesRequest()).futureValue.getTableNames.asScala should be(List(tableName))
    }

    "scan" in {
      import system.dispatcher

      val requests = 1 to 10 map { i =>
        DynamoDBClient.putItem(new PutItemRequest(tableName, Map("Id" -> new AttributeValue().withN(i.toString)).asJava))
      }
      Future.sequence(requests).futureValue

      val scanResult = DynamoDBClient.scan(new ScanRequest(tableName)).futureValue
      scanResult.getCount should be(10)
      scanResult.getItems.asScala.map(_.get("Id").getN).toSet should be(1 to 10 map (_.toString) toSet)
    }
  }
}

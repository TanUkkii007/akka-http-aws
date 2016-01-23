package tanukkii.akkahttp.aws.dynamodb

import java.net.InetSocketAddress

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.testkit.TestKit
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.internal.StaticCredentialsProvider
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer
import com.amazonaws.services.dynamodbv2.model.{ScanRequest, ListTablesRequest}
import org.scalatest.{DiagrammedAssertions, WordSpecLike, BeforeAndAfterAll}
import tanukkii.akkahttp.aws.{ConnectionSettings, HttpConnectionFlow}
import scala.concurrent.duration._
import scala.concurrent.Await

class DynamoDBClientTest extends TestKit(ActorSystem("DynamoDBClientTest"))
with WordSpecLike
with DiagrammedAssertions
with BeforeAndAfterAll
with DynamoDBTestSupport
with MarshallersAndUnmarshallers {

  var dynamoDBServer: DynamoDBProxyServer = _
  var dynamoDBServerAddress: InetSocketAddress = _
  implicit var connectionFlow: HttpConnectionFlow = _

  override def beforeAll() = {
    val tmp = createDynamoDBProxyServer()
    dynamoDBServer = tmp._1
    dynamoDBServerAddress = tmp._2
    dynamoDBServer.start()

    connectionFlow = HttpConnectionFlow(
      ConnectionSettings(
        dynamoDBServerAddress.getHostName,
        dynamoDBServerAddress.getPort,
        credentialsProvider = new StaticCredentialsProvider(new BasicAWSCredentials("aws_access_key_id", "aws_secret_access_key"))
      ),
      DynamoDBService()
    )
  }

  override def afterAll() = {
    dynamoDBServer.stop()
  }

  "DynamoDBClient" must {
    implicit val materializer = ActorMaterializer()

    "listTables" in {
      assert(Await.result(DynamoDBClient.listTables(new ListTablesRequest()), 5 seconds).getTableNames.size() == 0)
    }

    "scan" in {
      assert(Await.result(DynamoDBClient.scan(new ScanRequest("table")), 5 seconds).getCount == 0)
    }
  }
}

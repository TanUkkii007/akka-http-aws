package tanukkii.akkahttp.aws.util

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.{ServerBinding, OutgoingConnection}
import akka.http.scaladsl.model.{HttpResponse, Uri, HttpRequest}
import akka.http.scaladsl.model.HttpMethods._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import akka.testkit.TestKit
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Span}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import scala.concurrent.duration._

import scala.concurrent.{Future, Await}

class EC2MetaDataClientTest extends TestKit(ActorSystem("EC2MetaDataClient"))
  with WordSpecLike with Matchers with BeforeAndAfterAll with ScalaFutures {


  override implicit def patienceConfig: PatienceConfig = PatienceConfig(scaled(Span(1000, Millis)), scaled(Span(200, Millis)))

  var serverBinding: ServerBinding = _

  var client: EC2MetaDataClient = _

  implicit val materializer = ActorMaterializer()

  "EC2MetaDataClient" must {
    import system.dispatcher

    "getAmiId" in {
      client.getAmiId()(materializer, dispatcher).futureValue should be("ami-2bb65342")
    }

    "getAmiLaunchIndex" in {
      client.getAmiLaunchIndex()(materializer, dispatcher).futureValue should be("0")
    }

    "getAmiManifestPath" in {
      client.getAmiManifestPath()(materializer, dispatcher).futureValue should be("unknown")
    }

    "getAncestorAmiIds" ignore {
      client.getAncestorAmiIds()(materializer, dispatcher).futureValue should be("ami-2bb65342")
    }

    "getInstanceAction" in {
      client.getInstanceAction()(materializer, dispatcher).futureValue should be("none")
    }

    "getInstanceId" in {
      client.getInstanceId()(materializer, dispatcher).futureValue should be("i-abc123abc456abc78")
    }

    "getInstanceType" in {
      client.getInstanceType()(materializer, dispatcher).futureValue should be("t2.micro")
    }

    "getLocalHostName" in {
      client.getLocalHostName()(materializer, dispatcher).futureValue should be("ip-10-251-50-35.ec2.internal")
    }

    "getMacAddress" in {
      client.getMacAddress()(materializer, dispatcher).futureValue should be("02:29:96:8f:6a:2d")
    }

    "getPrivateIpAddress" in {
      client.getPrivateIpAddress()(materializer, dispatcher).futureValue should be("10.251.50.35")
    }

    "getAvailabilityZone" in {
      client.getAvailabilityZone()(materializer, dispatcher).futureValue should be("us-east-1a")
    }
  }

  override protected def beforeAll(): Unit = {
    val bindFuture = Http().bindAndHandleSync({
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/ami-id")=>
        HttpResponse(entity = "ami-2bb65342")
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/ami-launch-index")=>
        HttpResponse(entity = "0")
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/ami-manifest-path")=>
        HttpResponse(entity = "unknown")
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/ancestor-ami-ids")=>
        HttpResponse(entity = "ami-2bb65342")
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/instance-action") =>
        HttpResponse(entity = "none")
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/instance-id") =>
        HttpResponse(entity = "i-abc123abc456abc78")
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/instance-type") =>
        HttpResponse(entity = "t2.micro")
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/local-hostname") =>
        HttpResponse(entity = "ip-10-251-50-35.ec2.internal")
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/mac") =>
        HttpResponse(entity = "02:29:96:8f:6a:2d")
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/local-ipv4") =>
        HttpResponse(entity = "10.251.50.35")
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/placement/availability-zone") =>
        HttpResponse(entity = "us-east-1a")
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/product-codes") =>
        HttpResponse(entity = "a\nb\nc")
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/public-keys/0/openssh-key")=>
        HttpResponse(entity =
          """
            |ssh-rsa MIICiTCCAfICCQD6m7oRw0uXOjANBgkqhkiG9w0BAQUFADCBiDELMAkGA1UEBhMC
            |VVMxCzAJBgNVBAgTAldBMRAwDgYDVQQHEwdTZWF0dGxlMQ8wDQYDVQQKEwZBbWF6
            |b24xFDASBgNVBAsTC0lBTSBDb25zb2xlMRIwEAYDVQQDEwlUZXN0Q2lsYWMxHzAd
            |BgkqhkiG9w0BCQEWEG5vb25lQGFtYXpvbi5jb20wHhcNMTEwNDI1MjA0NTIxWhcN
            |MTIwNDI0MjA0NTIxWjCBiDELMAkGA1UEBhMCVVMxCzAJBgNVBAgTAldBMRAwDgYD
            |VQQHEwdTZWF0dGxlMQ8wDQYDVQQKEwZBbWF6b24xFDASBgNVBAsTC0lBTSBDb25z
            |b2xlMRIwEAYDVQQDEwlUZXN0Q2lsYWMxHzAdBgkqhkiG9w0BCQEWEG5vb25lQGFt
            |YXpvbi5jb20wgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAMaK0dn+a4GmWIWJ
            |21uUSfwfEvySWtC2XADZ4nB+BLYgVIk60CpiwsZ3G93vUEIO3IyNoH/f0wYK8m9T
            |rDHudUZg3qX4waLG5M43q7Wgc/MbQITxOUSQv7c7ugFFDzQGBzZswY6786m86gpE
            |Ibb3OhjZnzcvQAaRHhdlQWIMm2nrAgMBAAEwDQYJKoZIhvcNAQEFBQADgYEAtCu4
            |nUhVVxYUntneD9+h8Mg9q6q+auNKyExzyLwaxlAoo7TJHidbtS4J5iNmZgXL0Fkb
            |FFBjvSfpJIlJ00zbhNYS5f6GuoEDmFJl0ZxBHjJnyp378OD8uTs7fLvjx79LjSTb
            |NYiytVbZPQUQ5Yaxu2jXnimvw3rrszlaEXAMPLE my-public-key
          """.stripMargin)
    }, interface = "localhost", port = 0)

    serverBinding = Await.result(bindFuture, 10 seconds)

    client = new EC2MetaDataClient {
      override val connectionFlow: Flow[HttpRequest, HttpResponse, Future[OutgoingConnection]] = Http().outgoingConnection(serverBinding.localAddress.getHostName, serverBinding.localAddress.getPort)
    }

    super.beforeAll()
  }

  override protected def afterAll(): Unit = {
    Await.result(serverBinding.unbind(), 10 seconds)
    super.afterAll()
  }
}

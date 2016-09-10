package tanukkii.akkahttp.aws.util

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.{ServerBinding, OutgoingConnection}
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.HttpMethods._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import akka.testkit.TestKit
import com.amazonaws.util.EC2MetadataUtils.{IAMInfo, InstanceInfo}
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

    "getAncestorAmiIds" in {
      client.getAncestorAmiIds()(materializer, dispatcher).futureValue should be(Vector("ami-2bb65342", "ami-2bb65342"))
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

    "getProductCodes" in {
      client.getProductCodes()(materializer, dispatcher).futureValue should be(Vector())
    }

    "getPublicKey" in {
      client.getPublicKey()(materializer, dispatcher).futureValue should be("""|ssh-rsa MIICiTCCAfICCQD6m7oRw0uXOjANBgkqhkiG9w0BAQUFADCBiDELMAkGA1UEBhMC
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
                                                                              |NYiytVbZPQUQ5Yaxu2jXnimvw3rrszlaEXAMPLE my-public-key""".stripMargin)
    }

    "getRamdiskId" in {
      client.getRamdiskId()(materializer, dispatcher).futureValue should be(None)
    }

    "getReservationId" in {
      client.getReservationId()(materializer, dispatcher).futureValue should be("r-fea54097")
    }

    "getSecurityGroups" in {
      client.getSecurityGroups()(materializer, dispatcher).futureValue should be(Vector("sg-51530134"))
    }

    "getIAMInstanceProfileInfo" in {
      val result = client.getIAMInstanceProfileInfo()(materializer, dispatcher).futureValue
      result.code should be("code")
      result.message should be("message")
      result.lastUpdated should be("lastUpdated")
      result.instanceProfileArn should be("instanceProfileArn")
      result.instanceProfileId should be("instanceProfileId")
    }

    "getInstanceInfo" in {
      val info = new InstanceInfo("2015-11-19T16:32:11Z", "t1.micro", "ami-5fb8c835", "i-1234567890abcdef0", null, "x86_64", "123456789012", "aki-919dcaf8", null, "us-east-1", "2010-08-31", "us-east-1d", "10.158.112.84", null)
      val result = client.getInstanceInfo()(materializer, dispatcher).futureValue
      result.getPendingTime should be(info.getPendingTime)
      result.getInstanceType should be(info.getInstanceType)
      result.getImageId should be(info.getImageId)
      result.getInstanceType should be(info.getInstanceType)
      result.getBillingProducts should be(info.getBillingProducts)
      result.getArchitecture should be(info.getArchitecture)
      result.getAccountId should be(info.getAccountId)
      result.getKernelId should be(info.getKernelId)
      result.getRamdiskId should be(info.getRamdiskId)
      result.getRegion should be(info.getRegion)
      result.getVersion should be(info.getVersion)
      result.getAvailabilityZone should be(info.getAvailabilityZone)
      result.getPrivateIp should be(info.getPrivateIp)
      result.getDevpayProductCodes should be(info.getDevpayProductCodes)
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
        HttpResponse(entity = "ami-2bb65342\nami-2bb65342")
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
        HttpResponse()
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/public-keys/0/openssh-key")=>
        HttpResponse(entity =
          """|ssh-rsa MIICiTCCAfICCQD6m7oRw0uXOjANBgkqhkiG9w0BAQUFADCBiDELMAkGA1UEBhMC
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
            |NYiytVbZPQUQ5Yaxu2jXnimvw3rrszlaEXAMPLE my-public-key""".stripMargin)
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/ramdisk-id") =>
        HttpResponse()
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/reservation-id") =>
        HttpResponse(entity = "r-fea54097")
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/security-groups") =>
        HttpResponse(entity = "sg-51530134")
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/iam/info") =>
        HttpResponse(entity =
          HttpEntity(ContentTypes.`application/json`, """
            |{
            |  "Code": "code",
            |  "Message": "message",
            |  "LastUpdated": "lastUpdated",
            |  "InstanceProfileArn": "instanceProfileArn",
            |  "InstanceProfileId": "instanceProfileId"
            |}
          """.stripMargin))
      case HttpRequest(GET, uri, _, _, _) if uri.path == Uri.Path("/latest/meta-data/latest/dynamic/instance-identity/document") =>
        HttpResponse(entity =
          HttpEntity(ContentTypes.`application/json`, """
            |{
            |  "devpayProductCodes" : null,
            |  "availabilityZone" : "us-east-1d",
            |  "privateIp" : "10.158.112.84",
            |  "version" : "2010-08-31",
            |  "region" : "us-east-1",
            |  "instanceId" : "i-1234567890abcdef0",
            |  "billingProducts" : null,
            |  "instanceType" : "t1.micro",
            |  "accountId" : "123456789012",
            |  "pendingTime" : "2015-11-19T16:32:11Z",
            |  "imageId" : "ami-5fb8c835",
            |  "kernelId" : "aki-919dcaf8",
            |  "ramdiskId" : null,
            |  "architecture" : "x86_64"
            |}
          """.stripMargin))
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

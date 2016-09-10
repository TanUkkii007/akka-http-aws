package tanukkii.akkahttp.aws.elasticloadbalancing

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.testkit.TestKit
import com.amazonaws.regions.{Regions, Region}
import com.amazonaws.services.elasticloadbalancing.model._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Millis, Span}
import org.scalatest.{Matchers, WordSpecLike}
import tanukkii.akkahttp.aws.HttpConnectionFlow
import scala.collection.JavaConverters._

class ElasticLoadBalancingClientTest extends TestKit(ActorSystem("ElasticLoadBalancingClientTest"))
with WordSpecLike with Matchers with ScalaFutures {

  override implicit def patienceConfig: PatienceConfig = PatienceConfig(Span(3000, Millis), Span(500, Millis))

  implicit val materializer = ActorMaterializer()

  val service = ElasticLoadBalancingService()

  val conf = system.settings.config.getConfig("elb-test")
  val lbName = conf.getString("lb-name")
  val subnetId = conf.getString("subnet-id")
  val lbSecurityGroup = conf.getString("lb-security-group-id")
  val instanceId = conf.getString("instance-id")

  implicit val connectionFlow: HttpConnectionFlow = ElasticLoadBalancingConnection(Region.getRegion(Regions.AP_NORTHEAST_1))

  "ElasticLoadBalancingClient" must {

    "create load balancer" in {
      val listener = new Listener()
      listener.setInstancePort(80)
      listener.setInstanceProtocol("HTTP")
      listener.setLoadBalancerPort(80)
      listener.setProtocol("HTTP")

      val request = new CreateLoadBalancerRequest()
      request.setLoadBalancerName(lbName)
      request.setListeners(List(
        listener
      ).asJava)
      request.setScheme("internet-facing")
      request.setSubnets(List(subnetId).asJava)
      request.setSecurityGroups(List(lbSecurityGroup).asJava)

      val result = ElasticLoadBalancingClient.createLoadBalancer(request).futureValue
      println(result.getDNSName)
    }

    "register instances" in {
      val request = new RegisterInstancesWithLoadBalancerRequest()
        .withLoadBalancerName(lbName)
        .withInstances(new Instance().withInstanceId(instanceId))
      val result = ElasticLoadBalancingClient.registerInstancesWithLoadBalancer(request).futureValue
    }

    "configure health check" in {
      val request = new ConfigureHealthCheckRequest()
      request.setLoadBalancerName(lbName)
      val healthCheck = new HealthCheck()
        .withHealthyThreshold(3)
        .withInterval(10)
        .withTarget("HTTP:80/index.html")
        .withTimeout(5)
        .withUnhealthyThreshold(2)
      request.setHealthCheck(healthCheck)
      val result = ElasticLoadBalancingClient.configureHealthCheck(request).futureValue
    }

    "describe instance health" in {
      val request = new DescribeInstanceHealthRequest()
      request.setLoadBalancerName(lbName)
      val result = ElasticLoadBalancingClient.describeInstanceHealth(request).futureValue
      println(result.getInstanceStates)
    }

    "delete load balancer" in {
      val request = new DeleteLoadBalancerRequest()
      request.setLoadBalancerName(lbName)
      val result = ElasticLoadBalancingClient.deleteLoadBalancer(request).futureValue
    }


  }
}

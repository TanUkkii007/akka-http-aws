package tanukkii.akkahttp.aws.elasticloadbalancing

import akka.actor.ActorSystem
import akka.http.scaladsl.settings.ClientConnectionSettings
import akka.stream.Materializer
import com.amazonaws.auth.{DefaultAWSCredentialsProviderChain, AWSCredentialsProvider}
import com.amazonaws.internal.DefaultServiceEndpointBuilder
import com.amazonaws.regions.Region
import tanukkii.akkahttp.aws.{ConnectionSettings, HttpConnectionFlow}

object ElasticLoadBalancingConnection {
  def apply(region: Region, credentialsProvider: AWSCredentialsProvider = new DefaultAWSCredentialsProviderChain(), settings: Option[ClientConnectionSettings] = None)(implicit system: ActorSystem, materializer: Materializer): HttpConnectionFlow = {
    val uri = new DefaultServiceEndpointBuilder(ElasticLoadBalancingServiceContext.service.name, "https")
      .withRegion(region)
    HttpConnectionFlow(ConnectionSettings(host = uri.getServiceEndpoint.getHost, credentialsProvider = credentialsProvider, settings = settings), ElasticLoadBalancingServiceContext.service)
  }
}

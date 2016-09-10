package tanukkii.akkahttp.aws.elasticloadbalancing

import akka.http.scaladsl.model.{HttpResponse, HttpRequest}
import akka.stream.Materializer
import com.amazonaws.services.elasticloadbalancing.model._
import tanukkii.akkahttp.aws.{AWSClientConversions, AWSClient, ConnectionFlow}

import scala.concurrent.Future

trait ElasticLoadBalancingClient extends AWSClient {
  import AWSClientConversions._
  import MarshallersAndUnmarshallers._

  type Service = ElasticLoadBalancingService

  def addTags(addTagsRequest: AddTagsRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[AddTagsResult] = {
    sendRequest(addTagsRequest).flatMap(response => convertFromHttpResponse[AddTagsResult, Service](response))(mat.executionContext)
  }

  def applySecurityGroupsToLoadBalancer(applySecurityGroupsToLoadBalancerRequest: ApplySecurityGroupsToLoadBalancerRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[ApplySecurityGroupsToLoadBalancerResult] = {
    sendRequest(applySecurityGroupsToLoadBalancerRequest).flatMap(response => convertFromHttpResponse[ApplySecurityGroupsToLoadBalancerResult, Service](response))(mat.executionContext)
  }

  def attachLoadBalancerToSubnets(attachLoadBalancerToSubnetsRequest: AttachLoadBalancerToSubnetsRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[AttachLoadBalancerToSubnetsResult] = {
    sendRequest(attachLoadBalancerToSubnetsRequest).flatMap(response => convertFromHttpResponse[AttachLoadBalancerToSubnetsResult, Service](response))(mat.executionContext)
  }

  def configureHealthCheck(configureHealthCheckRequest: ConfigureHealthCheckRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[ConfigureHealthCheckResult] = {
    sendRequest(configureHealthCheckRequest).flatMap(response => convertFromHttpResponse[ConfigureHealthCheckResult, Service](response))(mat.executionContext)
  }

  def createAppCookieStickinessPolicy(createAppCookieStickinessPolicyRequest: CreateAppCookieStickinessPolicyRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[CreateAppCookieStickinessPolicyResult] = {
    sendRequest(createAppCookieStickinessPolicyRequest).flatMap(response => convertFromHttpResponse[CreateAppCookieStickinessPolicyResult, Service](response))(mat.executionContext)
  }

  def createLBCookieStickinessPolicy(createLBCookieStickinessPolicyRequest: CreateLBCookieStickinessPolicyRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[CreateLBCookieStickinessPolicyResult] = {
    sendRequest(createLBCookieStickinessPolicyRequest).flatMap(response => convertFromHttpResponse[CreateLBCookieStickinessPolicyResult, Service](response))(mat.executionContext)
  }

  def createLoadBalancer(createLoadBalancerRequest: CreateLoadBalancerRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[CreateLoadBalancerResult] = {
    sendRequest(createLoadBalancerRequest).flatMap(response => convertFromHttpResponse[CreateLoadBalancerResult, Service](response))(mat.executionContext)
  }

  def createLoadBalancerListeners(createLoadBalancerListenersRequest: CreateLoadBalancerListenersRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[CreateLoadBalancerListenersResult] = {
    sendRequest(createLoadBalancerListenersRequest).flatMap(response => convertFromHttpResponse[CreateLoadBalancerListenersResult, Service](response))(mat.executionContext)
  }

  def createLoadBalancerPolicy(createLoadBalancerPolicyRequest: CreateLoadBalancerPolicyRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[CreateLoadBalancerPolicyResult] = {
    sendRequest(createLoadBalancerPolicyRequest).flatMap(response => convertFromHttpResponse[CreateLoadBalancerPolicyResult, Service](response))(mat.executionContext)
  }

  def deleteLoadBalancer(deleteLoadBalancerRequest: DeleteLoadBalancerRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DeleteLoadBalancerResult] = {
    sendRequest(deleteLoadBalancerRequest).flatMap(response => convertFromHttpResponse[DeleteLoadBalancerResult, Service](response))(mat.executionContext)
  }

  def deleteLoadBalancerListeners(deleteLoadBalancerListenersRequest: DeleteLoadBalancerListenersRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DeleteLoadBalancerListenersResult] = {
    sendRequest(deleteLoadBalancerListenersRequest).flatMap(response => convertFromHttpResponse[DeleteLoadBalancerListenersResult, Service](response))(mat.executionContext)
  }

  def deleteLoadBalancerPolicy(deleteLoadBalancerPolicyRequest: DeleteLoadBalancerPolicyRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DeleteLoadBalancerPolicyResult] = {
    sendRequest(deleteLoadBalancerPolicyRequest).flatMap(response => convertFromHttpResponse[DeleteLoadBalancerPolicyResult, Service](response))(mat.executionContext)
  }

  def deregisterInstancesFromLoadBalancer(deregisterInstancesFromLoadBalancerRequest: DeregisterInstancesFromLoadBalancerRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DeregisterInstancesFromLoadBalancerResult] = {
    sendRequest(deregisterInstancesFromLoadBalancerRequest).flatMap(response => convertFromHttpResponse[DeregisterInstancesFromLoadBalancerResult, Service](response))(mat.executionContext)
  }

  def describeInstanceHealth(describeInstanceHealthRequest: DescribeInstanceHealthRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DescribeInstanceHealthResult] = {
    sendRequest(describeInstanceHealthRequest).flatMap(response => convertFromHttpResponse[DescribeInstanceHealthResult, Service](response))(mat.executionContext)
  }

  def describeLoadBalancerAttributes(describeLoadBalancerAttributesRequest: DescribeLoadBalancerAttributesRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DescribeLoadBalancerAttributesResult] = {
    sendRequest(describeLoadBalancerAttributesRequest).flatMap(response => convertFromHttpResponse[DescribeLoadBalancerAttributesResult, Service](response))(mat.executionContext)
  }

  def describeLoadBalancerPolicies(describeLoadBalancerPoliciesRequest: DescribeLoadBalancerPoliciesRequest = new DescribeLoadBalancerPoliciesRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DescribeLoadBalancerPoliciesResult] = {
    sendRequest(describeLoadBalancerPoliciesRequest).flatMap(response => convertFromHttpResponse[DescribeLoadBalancerPoliciesResult, Service](response))(mat.executionContext)
  }

  def describeLoadBalancerPolicyTypes(describeLoadBalancerPolicyTypesRequest: DescribeLoadBalancerPolicyTypesRequest = new DescribeLoadBalancerPolicyTypesRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DescribeLoadBalancerPolicyTypesResult] = {
    sendRequest(describeLoadBalancerPolicyTypesRequest).flatMap(response => convertFromHttpResponse[DescribeLoadBalancerPolicyTypesResult, Service](response))(mat.executionContext)
  }

  def describeLoadBalancers(describeLoadBalancersRequest: DescribeLoadBalancersRequest = new DescribeLoadBalancersRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DescribeLoadBalancersResult] = {
    sendRequest(describeLoadBalancersRequest).flatMap(response => convertFromHttpResponse[DescribeLoadBalancersResult, Service](response))(mat.executionContext)
  }

  def describeTags(describeTagsRequest: DescribeTagsRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DescribeTagsResult] = {
    sendRequest(describeTagsRequest).flatMap(response => convertFromHttpResponse[DescribeTagsResult, Service](response))(mat.executionContext)
  }

  def detachLoadBalancerFromSubnets(detachLoadBalancerFromSubnetsRequest: DetachLoadBalancerFromSubnetsRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DetachLoadBalancerFromSubnetsResult] = {
    sendRequest(detachLoadBalancerFromSubnetsRequest).flatMap(response => convertFromHttpResponse[DetachLoadBalancerFromSubnetsResult, Service](response))(mat.executionContext)
  }

  def disableAvailabilityZonesForLoadBalancer(disableAvailabilityZonesForLoadBalancerRequest: DisableAvailabilityZonesForLoadBalancerRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DisableAvailabilityZonesForLoadBalancerResult] = {
    sendRequest(disableAvailabilityZonesForLoadBalancerRequest).flatMap(response => convertFromHttpResponse[DisableAvailabilityZonesForLoadBalancerResult, Service](response))(mat.executionContext)
  }

  def enableAvailabilityZonesForLoadBalancer(enableAvailabilityZonesForLoadBalancerRequest: EnableAvailabilityZonesForLoadBalancerRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[EnableAvailabilityZonesForLoadBalancerResult] = {
    sendRequest(enableAvailabilityZonesForLoadBalancerRequest).flatMap(response => convertFromHttpResponse[EnableAvailabilityZonesForLoadBalancerResult, Service](response))(mat.executionContext)
  }

  def modifyLoadBalancerAttributes(modifyLoadBalancerAttributesRequest: ModifyLoadBalancerAttributesRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[ModifyLoadBalancerAttributesResult] = {
    sendRequest(modifyLoadBalancerAttributesRequest).flatMap(response => convertFromHttpResponse[ModifyLoadBalancerAttributesResult, Service](response))(mat.executionContext)
  }

  def registerInstancesWithLoadBalancer(registerInstancesWithLoadBalancerRequest: RegisterInstancesWithLoadBalancerRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[RegisterInstancesWithLoadBalancerResult] = {
    sendRequest(registerInstancesWithLoadBalancerRequest).flatMap(response => convertFromHttpResponse[RegisterInstancesWithLoadBalancerResult, Service](response))(mat.executionContext)
  }

  def removeTags(removeTagsRequest: RemoveTagsRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[RemoveTagsResult] = {
    sendRequest(removeTagsRequest).flatMap(response => convertFromHttpResponse[RemoveTagsResult, Service](response))(mat.executionContext)
  }

  def setLoadBalancerListenerSSLCertificate(setLoadBalancerListenerSSLCertificateRequest: SetLoadBalancerListenerSSLCertificateRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[SetLoadBalancerListenerSSLCertificateResult] = {
    sendRequest(setLoadBalancerListenerSSLCertificateRequest).flatMap(response => convertFromHttpResponse[SetLoadBalancerListenerSSLCertificateResult, Service](response))(mat.executionContext)
  }

  def setLoadBalancerPoliciesForBackendServer(setLoadBalancerPoliciesForBackendServerRequest: SetLoadBalancerPoliciesForBackendServerRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[SetLoadBalancerPoliciesForBackendServerResult] = {
    sendRequest(setLoadBalancerPoliciesForBackendServerRequest).flatMap(response => convertFromHttpResponse[SetLoadBalancerPoliciesForBackendServerResult, Service](response))(mat.executionContext)
  }

  def setLoadBalancerPoliciesOfListener(setLoadBalancerPoliciesOfListenerRequest: SetLoadBalancerPoliciesOfListenerRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[SetLoadBalancerPoliciesOfListenerResult] = {
    sendRequest(setLoadBalancerPoliciesOfListenerRequest).flatMap(response => convertFromHttpResponse[SetLoadBalancerPoliciesOfListenerResult, Service](response))(mat.executionContext)
  }
}

object ElasticLoadBalancingClient extends ElasticLoadBalancingClient
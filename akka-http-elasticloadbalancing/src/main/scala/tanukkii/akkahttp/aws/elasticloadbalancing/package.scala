package tanukkii.akkahttp.aws

import java.util

import com.amazonaws.AmazonServiceException
import com.amazonaws.http.{DefaultErrorResponseHandler, HttpResponseHandler}
import com.amazonaws.services.elasticloadbalancing.model.transform._
import com.amazonaws.transform.{Unmarshaller, StandardErrorUnmarshaller}
import org.w3c.dom.Node
import scala.collection.JavaConverters._

package object elasticloadbalancing {

  implicit object ElasticLoadBalancingServiceContext extends AWSServiceContext[ElasticLoadBalancingService] {

    private val exceptionUnmarshallers: util.List[Unmarshaller[AmazonServiceException, Node]] = {
      val unmarshallers = new util.ArrayList[Unmarshaller[AmazonServiceException, Node]]
      List(
        new UnsupportedProtocolExceptionUnmarshaller,
        new LoadBalancerAttributeNotFoundExceptionUnmarshaller,
        new LoadBalancerNotFoundExceptionUnmarshaller,
        new TooManyLoadBalancersExceptionUnmarshaller,
        new InvalidConfigurationRequestExceptionUnmarshaller,
        new InvalidSecurityGroupExceptionUnmarshaller,
        new DuplicateLoadBalancerNameExceptionUnmarshaller,
        new DependencyThrottleExceptionUnmarshaller,
        new PolicyNotFoundExceptionUnmarshaller,
        new CertificateNotFoundExceptionUnmarshaller,
        new DuplicateTagKeysExceptionUnmarshaller,
        new TooManyTagsExceptionUnmarshaller,
        new ListenerNotFoundExceptionUnmarshaller,
        new TooManyPoliciesExceptionUnmarshaller,
        new DuplicatePolicyNameExceptionUnmarshaller,
        new SubnetNotFoundExceptionUnmarshaller,
        new DuplicateListenerExceptionUnmarshaller,
        new InvalidSchemeExceptionUnmarshaller,
        new PolicyTypeNotFoundExceptionUnmarshaller,
        new InvalidSubnetExceptionUnmarshaller,
        new InvalidInstanceExceptionUnmarshaller,
        new StandardErrorUnmarshaller(
          classOf[com.amazonaws.services.elasticloadbalancing.model.AmazonElasticLoadBalancingException]
        )
      ).foreach(m => unmarshallers.add(m))
      unmarshallers
    }

    override val service: ElasticLoadBalancingService = ElasticLoadBalancingService()

    override val errorResponseHandler: HttpResponseHandler[AmazonServiceException] = new DefaultErrorResponseHandler(
      exceptionUnmarshallers
    )
  }
}

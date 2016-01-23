package tanukkii.akkahttp.aws

import com.amazonaws.AmazonServiceException
import com.amazonaws.http.{HttpResponseHandler, JsonErrorResponseHandlerV2}
import com.amazonaws.transform.JsonErrorUnmarshallerV2

import scala.collection.JavaConversions._

package object dynamodb {
  implicit object DynamoDBServiceContext extends AWSServiceContext[DynamoDBService] {
    val service: DynamoDBService = DynamoDBService()

    private val dynamoExceptionUnmarshallers = List(
      new JsonErrorUnmarshallerV2(
        classOf[com.amazonaws.services.dynamodbv2.model.ResourceInUseException],
        "ResourceInUseException"
      ),
      new JsonErrorUnmarshallerV2(
        classOf[com.amazonaws.services.dynamodbv2.model.ItemCollectionSizeLimitExceededException],
        "ItemCollectionSizeLimitExceededException"
      ),
      new JsonErrorUnmarshallerV2(
        classOf[com.amazonaws.services.dynamodbv2.model.LimitExceededException],
        "LimitExceededException"
      ),
      new JsonErrorUnmarshallerV2(
        classOf[com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException],
        "ConditionalCheckFailedException"
      ),
      new JsonErrorUnmarshallerV2(
        classOf[com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputExceededException],
        "ProvisionedThroughputExceededException"
      ),
      new JsonErrorUnmarshallerV2(
        classOf[com.amazonaws.services.dynamodbv2.model.InternalServerErrorException],
        "InternalServerError"
      ),
      new JsonErrorUnmarshallerV2(
        classOf[com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException],
        "ResourceNotFoundException"
      ),
      JsonErrorUnmarshallerV2.DEFAULT_UNMARSHALLER)

    val errorResponseHandler: HttpResponseHandler[AmazonServiceException] = new JsonErrorResponseHandlerV2(dynamoExceptionUnmarshallers)
  }
}

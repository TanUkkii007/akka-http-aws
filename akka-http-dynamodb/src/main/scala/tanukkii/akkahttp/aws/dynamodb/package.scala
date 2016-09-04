package tanukkii.akkahttp.aws

import com.amazonaws.AmazonServiceException
import com.amazonaws.http.HttpResponseHandler
import com.amazonaws.protocol.json.{JsonErrorResponseMetadata, JsonErrorShapeMetadata, JsonClientMetadata, SdkJsonProtocolFactory}

package object dynamodb {
  implicit object DynamoDBServiceContext extends AWSServiceContext[DynamoDBService] {

    override val service: DynamoDBService = DynamoDBService()

    val protocolFactory: SdkJsonProtocolFactory = new SdkJsonProtocolFactory(
      new JsonClientMetadata()
        .withProtocolVersion("1.0")
        .withSupportsCbor(false)
        .addErrorMetadata(
          new JsonErrorShapeMetadata()
            .withErrorCode(
              "ItemCollectionSizeLimitExceededException")
            .withModeledClass(
              classOf[com.amazonaws.services.dynamodbv2.model.ItemCollectionSizeLimitExceededException]))
        .addErrorMetadata(
          new JsonErrorShapeMetadata()
            .withErrorCode("ResourceInUseException")
            .withModeledClass(
              classOf[com.amazonaws.services.dynamodbv2.model.ResourceInUseException]))
        .addErrorMetadata(
          new JsonErrorShapeMetadata()
            .withErrorCode("ResourceNotFoundException")
            .withModeledClass(
              classOf[com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException]))
        .addErrorMetadata(
          new JsonErrorShapeMetadata()
            .withErrorCode(
              "ProvisionedThroughputExceededException")
            .withModeledClass(
              classOf[com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputExceededException]))
        .addErrorMetadata(
          new JsonErrorShapeMetadata()
            .withErrorCode(
              "ConditionalCheckFailedException")
            .withModeledClass(
              classOf[com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException]))
        .addErrorMetadata(
          new JsonErrorShapeMetadata()
            .withErrorCode("InternalServerError")
            .withModeledClass(
              classOf[com.amazonaws.services.dynamodbv2.model.InternalServerErrorException]))
        .addErrorMetadata(
          new JsonErrorShapeMetadata()
            .withErrorCode("LimitExceededException")
            .withModeledClass(
              classOf[com.amazonaws.services.dynamodbv2.model.LimitExceededException]))
        .withBaseServiceExceptionClass(
          classOf[com.amazonaws.services.dynamodbv2.model.AmazonDynamoDBException]))

    override val errorResponseHandler: HttpResponseHandler[AmazonServiceException] = protocolFactory
      .createErrorResponseHandler(new JsonErrorResponseMetadata())
  }
}

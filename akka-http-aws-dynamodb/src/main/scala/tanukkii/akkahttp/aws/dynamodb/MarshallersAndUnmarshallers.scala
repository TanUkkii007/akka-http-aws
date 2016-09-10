package tanukkii.akkahttp.aws.dynamodb

import com.amazonaws.AmazonWebServiceResponse
import com.amazonaws.http.{HttpResponseHandler, JsonResponseHandler}
import com.amazonaws.protocol.json.{JsonOperationMetadata, JsonErrorShapeMetadata, JsonClientMetadata, SdkJsonProtocolFactory}
import com.amazonaws.services.dynamodbv2.model._
import com.amazonaws.services.dynamodbv2.model.transform._

class MarshallersAndUnmarshallers(protocolFactory: SdkJsonProtocolFactory) {

  implicit val batchWriteM = new BatchWriteItemRequestMarshaller(protocolFactory)
  implicit val batchWriteU: HttpResponseHandler[AmazonWebServiceResponse[BatchWriteItemResult]] = protocolFactory
    .createResponseHandler(new JsonOperationMetadata()
      .withPayloadJson(true)
      .withHasStreamingSuccessResponse(false),
      new BatchWriteItemResultJsonUnmarshaller())
  implicit val putItemM = new PutItemRequestMarshaller(protocolFactory)
  implicit val putItemU: HttpResponseHandler[AmazonWebServiceResponse[PutItemResult]] = protocolFactory
    .createResponseHandler(new JsonOperationMetadata()
      .withPayloadJson(true)
      .withHasStreamingSuccessResponse(false),
      new PutItemResultJsonUnmarshaller())
  implicit val delItemM = new DeleteItemRequestMarshaller(protocolFactory)
  implicit val delItemU: HttpResponseHandler[AmazonWebServiceResponse[DeleteItemResult]] = protocolFactory
    .createResponseHandler(new JsonOperationMetadata()
      .withPayloadJson(true)
      .withHasStreamingSuccessResponse(false),
      new DeleteItemResultJsonUnmarshaller())
  implicit val batchGetM = new BatchGetItemRequestMarshaller(protocolFactory)
  implicit val batchGetU: HttpResponseHandler[AmazonWebServiceResponse[BatchGetItemResult]] = protocolFactory.createResponseHandler(new JsonOperationMetadata()
    .withPayloadJson(true)
    .withHasStreamingSuccessResponse(false),
    new BatchGetItemResultJsonUnmarshaller())
  implicit val listTablesM = new ListTablesRequestMarshaller(protocolFactory)
  implicit val listTablesU: HttpResponseHandler[AmazonWebServiceResponse[ListTablesResult]] = protocolFactory
    .createResponseHandler(new JsonOperationMetadata()
      .withPayloadJson(true)
      .withHasStreamingSuccessResponse(false),
      new ListTablesResultJsonUnmarshaller())
  implicit val queryM = new QueryRequestMarshaller(protocolFactory)
  implicit val queryU: HttpResponseHandler[AmazonWebServiceResponse[QueryResult]] = protocolFactory
    .createResponseHandler(new JsonOperationMetadata()
      .withPayloadJson(true)
      .withHasStreamingSuccessResponse(false),
      new QueryResultJsonUnmarshaller())
  implicit val updateItemM = new UpdateItemRequestMarshaller(protocolFactory)
  implicit val updateItemU = protocolFactory
    .createResponseHandler(new JsonOperationMetadata()
      .withPayloadJson(true)
      .withHasStreamingSuccessResponse(false),
      new UpdateItemResultJsonUnmarshaller())
  implicit val dM = new DescribeTableRequestMarshaller(protocolFactory)
  implicit val dU = protocolFactory
    .createResponseHandler(new JsonOperationMetadata()
      .withPayloadJson(true)
      .withHasStreamingSuccessResponse(false),
      new DescribeTableResultJsonUnmarshaller())
  implicit val describeLimitM = new DescribeLimitsRequestMarshaller(protocolFactory)
  implicit val describeLimitU = protocolFactory
    .createResponseHandler(new JsonOperationMetadata()
      .withPayloadJson(true)
      .withHasStreamingSuccessResponse(false),
      new DescribeLimitsResultJsonUnmarshaller())
  implicit val scanM = new ScanRequestMarshaller(protocolFactory)
  implicit val scanU: HttpResponseHandler[AmazonWebServiceResponse[ScanResult]] = protocolFactory
    .createResponseHandler(new JsonOperationMetadata()
      .withPayloadJson(true)
      .withHasStreamingSuccessResponse(false),
      new ScanResultJsonUnmarshaller())
  implicit val createTableM = new CreateTableRequestMarshaller(protocolFactory)
  implicit val createTableU: HttpResponseHandler[AmazonWebServiceResponse[CreateTableResult]] = protocolFactory
    .createResponseHandler(new JsonOperationMetadata()
      .withPayloadJson(true)
      .withHasStreamingSuccessResponse(false),
      new CreateTableResultJsonUnmarshaller())
  implicit val updateTableM = new UpdateTableRequestMarshaller(protocolFactory)
  implicit val updateTableU = protocolFactory
    .createResponseHandler(new JsonOperationMetadata()
      .withPayloadJson(true)
      .withHasStreamingSuccessResponse(false),
      new UpdateTableResultJsonUnmarshaller())
  implicit val deleteTableM = new DeleteTableRequestMarshaller(protocolFactory)
  implicit val deleteTableU: HttpResponseHandler[AmazonWebServiceResponse[DeleteTableResult]] = protocolFactory
    .createResponseHandler(new JsonOperationMetadata()
      .withPayloadJson(true)
      .withHasStreamingSuccessResponse(false),
      new DeleteTableResultJsonUnmarshaller())
  implicit val getItemM = new GetItemRequestMarshaller(protocolFactory)
  implicit val getItemU: HttpResponseHandler[AmazonWebServiceResponse[GetItemResult]] = protocolFactory
    .createResponseHandler(new JsonOperationMetadata()
      .withPayloadJson(true)
      .withHasStreamingSuccessResponse(false),
      new GetItemResultJsonUnmarshaller())
}

object MarshallersAndUnmarshallers {
  def apply(protocolFactory: SdkJsonProtocolFactory): MarshallersAndUnmarshallers = new MarshallersAndUnmarshallers(protocolFactory)
}
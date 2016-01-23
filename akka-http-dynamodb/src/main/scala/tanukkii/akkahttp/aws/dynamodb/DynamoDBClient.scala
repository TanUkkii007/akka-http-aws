package tanukkii.akkahttp.aws
package dynamodb

import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer
import com.amazonaws.services.dynamodbv2.model._
import scala.concurrent.Future

trait DynamoDBClient extends AWSClient {
  import AWSClientConversions._
  import MarshallersAndUnmarshallers._

  def listTables(request: ListTablesRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[ListTablesResult] = {
    sendRequest(request).flatMap(response => convertFromHttpResponse[ListTablesResult, DynamoDBService](response))(mat.executionContext)
  }

  def query(request: QueryRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[QueryResult] = {
    sendRequest(request).flatMap(response => convertFromHttpResponse[QueryResult, DynamoDBService](response))(mat.executionContext)
  }

  def scan(request: ScanRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[ScanResult] = {
    sendRequest(request).flatMap(response => convertFromHttpResponse[ScanResult, DynamoDBService](response))(mat.executionContext)
  }

  def updateItem(request: UpdateItemRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[UpdateItemResult] = {
    sendRequest(request).flatMap(response => convertFromHttpResponse[UpdateItemResult, DynamoDBService](response))(mat.executionContext)
  }

  def putItem(request: PutItemRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[PutItemResult] = {
    sendRequest(request).flatMap(response => convertFromHttpResponse[PutItemResult, DynamoDBService](response))(mat.executionContext)
  }

  def describeTable(request: DescribeTableRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DescribeTableResult] = {
    sendRequest(request).flatMap(response => convertFromHttpResponse[DescribeTableResult, DynamoDBService](response))(mat.executionContext)
  }

  def createTable(request: CreateTableRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[CreateTableResult] = {
    sendRequest(request).flatMap(response => convertFromHttpResponse[CreateTableResult, DynamoDBService](response))(mat.executionContext)
  }

  def updateTable(request: UpdateTableRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[UpdateTableResult] = {
    sendRequest(request).flatMap(response => convertFromHttpResponse[UpdateTableResult, DynamoDBService](response))(mat.executionContext)
  }

  def deleteTable(request: DeleteTableRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DeleteTableResult] = {
    sendRequest(request).flatMap(response => convertFromHttpResponse[DeleteTableResult, DynamoDBService](response))(mat.executionContext)
  }

  def getItem(request: GetItemRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[GetItemResult] = {
    sendRequest(request).flatMap(response => convertFromHttpResponse[GetItemResult, DynamoDBService](response))(mat.executionContext)
  }

  def batchWriteItem(request: BatchWriteItemRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[BatchWriteItemResult] = {
    sendRequest(request).flatMap(response => convertFromHttpResponse[BatchWriteItemResult, DynamoDBService](response))(mat.executionContext)
  }

  def batchGetItem(request: BatchGetItemRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[BatchGetItemResult] = {
    sendRequest(request).flatMap(response => convertFromHttpResponse[BatchGetItemResult, DynamoDBService](response))(mat.executionContext)
  }

  def sendDeleteItem(request: DeleteItemRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DeleteItemResult] = {
    sendRequest(request).flatMap(response => convertFromHttpResponse[DeleteItemResult, DynamoDBService](response))(mat.executionContext)
  }

  def deleteItem(request: DeleteItemRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[DeleteItemResult] = {
    sendRequest(request).flatMap(response => convertFromHttpResponse[DeleteItemResult, DynamoDBService](response))(mat.executionContext)
  }


}

object DynamoDBClient extends DynamoDBClient
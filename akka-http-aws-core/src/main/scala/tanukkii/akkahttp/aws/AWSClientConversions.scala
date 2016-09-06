package tanukkii.akkahttp.aws

import akka.http.scaladsl.model.HttpHeader.ParsingResult
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model._
import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source, Flow}
import akka.util.ByteString
import com.amazonaws.{DefaultRequest, AmazonWebServiceResponse, Request}
import com.amazonaws.http.{HttpResponseHandler, HttpMethodName, HttpResponse => AWSHttpResponse}
import com.amazonaws.transform.Marshaller
import scala.collection.JavaConversions._
import java.net.URI
import scala.collection.mutable
import scala.concurrent.Future
import scala.collection.JavaConverters._

trait AWSClientConversions {
  val defaultMediaType = "application/x-amz-json-1.0"

  implicit def convertToHttpMethod(method: HttpMethodName): HttpMethod = {
    method match {
      case HttpMethodName.GET => HttpMethods.GET
      case HttpMethodName.POST => HttpMethods.POST
      case HttpMethodName.PUT => HttpMethods.PUT
      case HttpMethodName.DELETE => HttpMethods.DELETE
      case HttpMethodName.HEAD => HttpMethods.HEAD
      case HttpMethodName.PATCH => HttpMethods.PATCH
    }
  }

  implicit def convertToHttpRequest[T](t: T)(implicit marshaller: Marshaller[Request[T], T], connectionFlow: ConnectionFlow[HttpRequest, HttpResponse]): HttpRequest = {
    val awsRequest = marshaller.marshall(t)
    awsRequest.setEndpoint(new URI(connectionFlow.endpoint))
    connectionFlow.signer.sign(awsRequest, connectionFlow.credentialsProvider.getCredentials)
    val parameter = awsRequest.getParameters.asScala.flatMap(kv => kv._2.asScala.map(v => kv._1 -> v)).toSeq.sorted
    val entity = if (awsRequest.getContent != null) {
      val body = Stream.continually(awsRequest.getContent.read).takeWhile(-1 != _).map(_.toByte).toArray
      val contentType = MediaType.custom(Option(awsRequest.getHeaders.get("Content-Type")).getOrElse(defaultMediaType), binary = false)
      HttpEntity(ContentType(contentType, () => HttpCharsets.`UTF-8`), body)
    } else HttpEntity.Empty
    val uri = Option(awsRequest.getResourcePath).filter(_.length > 0).getOrElse("/")
    val headers = awsRequest.getHeaders.toList.withFilter {
      case ("Host", _) => false
      case ("User-Agent", _) => false
      case ("Content-Length", _) => false
      case ("Content-Type", _) => false
      case (_, _) => true
    }.map { kv =>
      val (name, value) = kv
      HttpHeader.parse(name, value)
    }.collect {
      case ParsingResult.Ok(header, _) => header
    }
    HttpRequest(method = awsRequest.getHttpMethod, uri = Uri(uri).withQuery(Query(parameter: _*)), headers = headers, entity = entity)
  }

  implicit def convertFromHttpResponseToSource[T, S <: AWSService](response: HttpResponse)(implicit handler: HttpResponseHandler[AmazonWebServiceResponse[T]], serviceContext: AWSServiceContext[S]): Source[T, Any] = {
    response.entity.dataBytes.via(byteBufferInputStreamFlow).map { inputStream =>
      val req = new DefaultRequest[T](serviceContext.service.name)
      val awsResp = new AWSHttpResponse(req, null)
      awsResp.setContent(inputStream)
      awsResp.setStatusCode(response.status.intValue)
      awsResp.setStatusText(response.status.defaultMessage)
      if (200 <= response.status.intValue && response.status.intValue < 300) {
        val handle: AmazonWebServiceResponse[T] = handler.handle(awsResp)
        handle.getResult
      } else {
        response.headers.foreach {
          h => awsResp.addHeader(h.name, h.value)
        }
        throw serviceContext.errorResponseHandler.handle(awsResp)
      }
    }
  }

  implicit def convertFromHttpResponse[T, S <: AWSService](response: HttpResponse)(implicit handler: HttpResponseHandler[AmazonWebServiceResponse[T]], serviceContext: AWSServiceContext[S], materializer: Materializer): Future[T] = {
    convertFromHttpResponseToSource(response).runWith(Sink.head)(materializer)
  }

  private val byteBufferInputStreamFlow = Flow[ByteString].fold(ByteString())(_ ++ _).map(_.iterator.asInputStream)
}

object AWSClientConversions extends AWSClientConversions
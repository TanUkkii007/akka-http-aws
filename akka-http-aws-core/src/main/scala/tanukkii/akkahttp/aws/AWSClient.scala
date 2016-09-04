package tanukkii.akkahttp.aws

import akka.http.scaladsl.model.{HttpResponse, HttpRequest}
import akka.stream.Materializer
import com.amazonaws.Request
import com.amazonaws.transform.Marshaller

import scala.concurrent.Future

trait AWSClient {
  import AWSClientConversions._

  def sendRequest[T](t: T)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer, marshaller: Marshaller[Request[T], T]): Future[HttpResponse] = {
    HttpClient.sendRequest(t)
  }

}

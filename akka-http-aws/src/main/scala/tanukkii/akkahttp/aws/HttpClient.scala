package tanukkii.akkahttp.aws

import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer
import akka.stream.scaladsl.Sink

import scala.concurrent.Future

trait HttpClient {
  def sendRequest(httpRequest: HttpRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[HttpResponse] = {
    HttpRequestSource.single(httpRequest).runWith(Sink.head)(mat)
  }

  def sendRequestWithFuture(httpRequest: Future[HttpRequest])(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], mat: Materializer): Future[HttpResponse] = {
    HttpRequestSource.fromFuture(httpRequest).runWith(Sink.head)(mat)
  }
}

object HttpClient extends HttpClient
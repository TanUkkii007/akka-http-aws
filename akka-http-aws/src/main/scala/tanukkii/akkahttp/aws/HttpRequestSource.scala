package tanukkii.akkahttp.aws

import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.scaladsl.Source

import scala.collection.immutable
import scala.concurrent.Future

object HttpRequestSource {
  def single(httpRequest: HttpRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse]): Source[HttpResponse, Unit] = {
    Source.single(httpRequest).via(cf.connectionFlow)
  }

  def apply(httpRequests: immutable.Iterable[HttpRequest])(implicit cf: ConnectionFlow[HttpRequest, HttpResponse]): Source[HttpResponse, Unit] = {
    Source.apply(httpRequests).via(cf.connectionFlow)
  }

  def fromFuture(httpRequest: Future[HttpRequest])(implicit cf: ConnectionFlow[HttpRequest, HttpResponse]): Source[HttpResponse, Unit] = {
    Source.fromFuture(httpRequest).via(cf.connectionFlow)
  }
}
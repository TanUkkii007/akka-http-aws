package tanukkii.akkahttp.aws

import akka.NotUsed
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.scaladsl.Source

import scala.collection.immutable
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

object HttpRequestSource {
  def single(httpRequest: HttpRequest)(implicit cf: ConnectionFlow[HttpRequest, HttpResponse]): Source[HttpResponse, NotUsed] = {
    Source.single(httpRequest -> 1).via(cf.connectionFlow).map(_._1.get)
  }

  def apply(httpRequests: immutable.Iterable[HttpRequest])(implicit cf: ConnectionFlow[HttpRequest, HttpResponse]): Source[(Try[HttpResponse], Int), NotUsed] = {
    Source.apply(httpRequests.zipWithIndex).via(cf.connectionFlow)
  }

  def fromFuture(httpRequest: Future[HttpRequest])(implicit cf: ConnectionFlow[HttpRequest, HttpResponse], ec: ExecutionContext): Source[HttpResponse, NotUsed] = {
    Source.fromFuture(httpRequest.map(_ -> 1)).via(cf.connectionFlow).map(_._1.get)
  }
}

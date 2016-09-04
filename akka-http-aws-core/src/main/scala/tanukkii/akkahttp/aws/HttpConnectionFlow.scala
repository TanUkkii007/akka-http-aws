package tanukkii.akkahttp.aws

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.OutgoingConnection
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.scaladsl.Flow
import com.amazonaws.auth.AWSCredentialsProvider

import scala.concurrent.Future

case class HttpConnectionFlow(connectionSettings: ConnectionSettings, service: AWSService)(implicit system: ActorSystem) extends ConnectionFlow[HttpRequest, HttpResponse]{
  val connectionFlow: Flow[HttpRequest, HttpResponse, Future[OutgoingConnection]] = {
    Http()(system).outgoingConnection(connectionSettings.host, connectionSettings.port)
  }

  val doubleUrlEncoding: Boolean = connectionSettings.doubleUrlEncoding

  val credentialsProvider: AWSCredentialsProvider = connectionSettings.credentialsProvider

  val endpoint: String = s"http://${connectionSettings.host}:${connectionSettings.port}"
}

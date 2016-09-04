package tanukkii.akkahttp.aws

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.HostConnectionPool
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.Materializer
import akka.stream.scaladsl.Flow
import com.amazonaws.auth.AWSCredentialsProvider

import scala.concurrent.Future
import scala.util.Try

case class HttpConnectionFlow(connectionSettings: ConnectionSettings, service: AWSService)(implicit system: ActorSystem, materializer: Materializer) extends ConnectionFlow[HttpRequest, HttpResponse]{

  val connectionFlow: Flow[(HttpRequest, Int), (Try[HttpResponse], Int), HostConnectionPool] = {
    Http()(system).cachedHostConnectionPoolHttps[Int](connectionSettings.host, connectionSettings.port)
  }

  val doubleUrlEncoding: Boolean = connectionSettings.doubleUrlEncoding

  val credentialsProvider: AWSCredentialsProvider = connectionSettings.credentialsProvider

  val endpoint: String = s"https://${connectionSettings.host}:${connectionSettings.port}"
}

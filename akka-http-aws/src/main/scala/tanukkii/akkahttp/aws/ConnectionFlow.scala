package tanukkii.akkahttp.aws

import akka.http.scaladsl.Http.OutgoingConnection
import akka.stream.scaladsl.Flow
import com.amazonaws.auth.{AWS4Signer, Signer, AWSCredentialsProvider}

import scala.concurrent.Future

trait ConnectionFlow[In, Out] {
  val doubleUrlEncoding: Boolean

  val service: AWSService

  def credentialsProvider: AWSCredentialsProvider

  val endpoint: String

  private [aws] lazy val signer: Signer = {
    val s = new AWS4Signer(doubleUrlEncoding)
    s.setServiceName(service.name)
    s
  }

  val connectionFlow: Flow[In, Out, Future[OutgoingConnection]]
}

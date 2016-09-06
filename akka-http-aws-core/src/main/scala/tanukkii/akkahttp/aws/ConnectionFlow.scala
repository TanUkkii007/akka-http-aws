package tanukkii.akkahttp.aws

import akka.http.scaladsl.Http.{HostConnectionPool, OutgoingConnection}
import akka.stream.scaladsl.Flow
import com.amazonaws.auth.{SignerFactory, AWS4Signer, Signer, AWSCredentialsProvider}
import com.amazonaws.regions.Regions
import scala.util.Try

trait ConnectionFlow[In, Out] {
  val doubleUrlEncoding: Boolean

  val service: AWSService

  def credentialsProvider: AWSCredentialsProvider

  val endpoint: String

  private [aws] lazy val signer: Signer = {
    SignerFactory.getSigner(service.name, Regions.AP_NORTHEAST_1.getName)
  }

  val connectionFlow: Flow[(In, Int), (Try[Out], Int), HostConnectionPool]
}

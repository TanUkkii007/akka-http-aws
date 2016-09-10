package tanukkii.akkahttp.aws

import akka.http.scaladsl.Http.HostConnectionPool
import akka.stream.scaladsl.Flow
import com.amazonaws.auth.{AWS4Signer, Signer, AWSCredentialsProvider}
import scala.util.Try

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

  val connectionFlow: Flow[(In, Int), (Try[Out], Int), HostConnectionPool]
}

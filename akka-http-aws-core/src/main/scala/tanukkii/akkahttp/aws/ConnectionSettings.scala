package tanukkii.akkahttp.aws

import java.net.InetSocketAddress

import akka.event.LoggingAdapter
import akka.http.scaladsl.settings.ClientConnectionSettings
import com.amazonaws.auth.AWSCredentialsProvider

case class ConnectionSettings(scheme: String = "https",
                              host: String,
                              port: Int = 443,
                              credentialsProvider: AWSCredentialsProvider,
                              localAddress: Option[InetSocketAddress] = None,
                              settings: Option[ClientConnectionSettings] = None,
                              log: Option[LoggingAdapter] = None,
                              doubleUrlEncoding: Boolean = true) {
  require(scheme == "https" || scheme == "http")
}

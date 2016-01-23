package tanukkii.akkahttp.aws

import java.net.InetSocketAddress

import akka.event.LoggingAdapter
import akka.http.ClientConnectionSettings
import com.amazonaws.auth.AWSCredentialsProvider

case class ConnectionSettings(host: String,
                              port: Int = 80,
                              credentialsProvider: AWSCredentialsProvider,
                              localAddress: Option[InetSocketAddress] = None,
                              settings: Option[ClientConnectionSettings] = None,
                              log: Option[LoggingAdapter] = None,
                              doubleUrlEncoding: Boolean = true)

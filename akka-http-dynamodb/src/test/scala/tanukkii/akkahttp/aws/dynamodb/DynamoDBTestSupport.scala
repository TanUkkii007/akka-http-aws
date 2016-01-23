package tanukkii.akkahttp.aws.dynamodb

import java.net.InetSocketAddress
import java.nio.channels.ServerSocketChannel
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded
import com.amazonaws.services.dynamodbv2.local.server.{DynamoDBProxyServer, LocalDynamoDBRequestHandler, LocalDynamoDBServerHandler}

trait DynamoDBTestSupport {

  def temporaryServerAddress(interface: String = "127.0.0.1"): InetSocketAddress = {
    val serverSocket = ServerSocketChannel.open()
    try {
      serverSocket.socket.bind(new InetSocketAddress(interface, 0))
      val port = serverSocket.socket.getLocalPort
      new InetSocketAddress(interface, port)
    } finally serverSocket.close()
  }

  def createDynamoDBProxyServer(): (DynamoDBProxyServer, InetSocketAddress) = {
    val address = temporaryServerAddress()
    new DynamoDBProxyServer(
      address.getPort,
      new LocalDynamoDBServerHandler(
        new LocalDynamoDBRequestHandler(0, true, null, false, false),
        null
      )
    ) -> address
  }
}

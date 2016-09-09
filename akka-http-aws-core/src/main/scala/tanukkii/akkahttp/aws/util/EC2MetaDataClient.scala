package tanukkii.akkahttp.aws.util

import java.nio.charset.StandardCharsets

import akka.NotUsed
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.OutgoingConnection
import akka.http.scaladsl.model._
import akka.http.scaladsl.settings.ClientConnectionSettings
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Framing, Sink, Source}
import akka.util.ByteString
import com.amazonaws.util.EC2MetadataUtils.{IAMInfo, InstanceInfo}
import com.amazonaws.util.json.Jackson
import com.fasterxml.jackson.databind.{DeserializationFeature, ObjectMapper, PropertyNamingStrategy}

import scala.collection.immutable.Seq
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

object EC2MetaDataResources {
  val serviceAddress = "169.254.169.254"
  val metadataRoot = "/latest/meta-data"
  val instanceIdentityDocument = "instance-identity/document"
  val ec2DynamicdataRoot = "/latest/dynamic/"
}


trait EC2MetaDataClient {
  import EC2MetaDataResources._

  val connectionFlow: Flow[HttpRequest, HttpResponse, Future[OutgoingConnection]]

  private val mapper: ObjectMapper = {
    val m = new ObjectMapper()
    m.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    m.setPropertyNamingStrategy(PropertyNamingStrategy.PASCAL_CASE_TO_CAMEL_CASE)
    m
  }

  def getAmiId()(implicit mat: Materializer, ec: ExecutionContext): Future[String] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/ami-id")))
      .via(connectionFlow)
      .via(stringResponseFlow())
      .runWith(Sink.head)
  }

  def getAmiLaunchIndex()(implicit mat: Materializer, ec: ExecutionContext): Future[String] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/ami-launch-index")))
      .via(connectionFlow)
      .via(stringResponseFlow())
      .runWith(Sink.head)
  }

  def getAmiManifestPath()(implicit mat: Materializer, ec: ExecutionContext): Future[String] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/ami-manifest-path")))
      .via(connectionFlow)
      .via(stringResponseFlow())
      .runWith(Sink.head)
  }

  def getAncestorAmiIds()(implicit mat: Materializer, ec: ExecutionContext): Future[Seq[String]] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/ancestor-ami-ids")))
      .via(connectionFlow)
      .via(stringListResponseFlow())
      .runWith(Sink.head)
  }

  def getInstanceAction()(implicit mat: Materializer, ec: ExecutionContext): Future[String] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/instance-action")))
      .via(connectionFlow)
      .via(stringResponseFlow())
      .runWith(Sink.head)
  }

  def getInstanceId()(implicit mat: Materializer, ec: ExecutionContext): Future[String] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/instance-id")))
      .via(connectionFlow)
      .via(stringResponseFlow())
      .runWith(Sink.head)
  }

  def getInstanceType()(implicit mat: Materializer, ec: ExecutionContext): Future[String] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/instance-type")))
      .via(connectionFlow)
      .via(stringResponseFlow())
      .runWith(Sink.head)
  }

  def getLocalHostName()(implicit mat: Materializer, ec: ExecutionContext): Future[String] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/local-hostname")))
      .via(connectionFlow)
      .via(stringResponseFlow())
      .runWith(Sink.head)
  }

  def getMacAddress()(implicit mat: Materializer, ec: ExecutionContext): Future[String] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/mac")))
      .via(connectionFlow)
      .via(stringResponseFlow())
      .runWith(Sink.head)
  }

  def getPrivateIpAddress()(implicit mat: Materializer, ec: ExecutionContext): Future[String] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/local-ipv4")))
      .via(connectionFlow)
      .via(stringResponseFlow())
      .runWith(Sink.head)
  }

  def getAvailabilityZone()(implicit mat: Materializer, ec: ExecutionContext): Future[String] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/placement/availability-zone")))
      .via(connectionFlow)
      .via(stringResponseFlow())
      .runWith(Sink.head)
  }

  def getProductCodes()(implicit mat: Materializer, ec: ExecutionContext): Future[Seq[String]] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/product-codes")))
      .via(connectionFlow)
      .via(stringListResponseFlow())
      .runWith(Sink.head)
  }

  def getPublicKey()(implicit mat: Materializer, ec: ExecutionContext): Future[String] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/public-keys/0/openssh-key")))
      .via(connectionFlow)
      .via(stringResponseFlow())
      .runWith(Sink.head)
  }

  def getRamdiskId()(implicit mat: Materializer, ec: ExecutionContext): Future[String] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/ramdisk-id")))
      .via(connectionFlow)
      .via(stringResponseFlow())
      .runWith(Sink.head)
  }

  def getReservationId()(implicit mat: Materializer, ec: ExecutionContext): Future[String] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/reservation-id")))
      .via(connectionFlow)
      .via(stringResponseFlow())
      .runWith(Sink.head)
  }

  def getSecurityGroups()(implicit mat: Materializer, ec: ExecutionContext): Future[Seq[String]] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/security-groups")))
      .via(connectionFlow)
      .via(stringListResponseFlow())
      .runWith(Sink.head)
  }

  def getIAMInstanceProfileInfo()(implicit mat: Materializer, ec: ExecutionContext): Future[IAMInfo] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + "/iam/info")))
      .via(connectionFlow)
      .via(stringResponseFlow())
      .map(json => mapper.readValue(json, classOf[IAMInfo]))
      .runWith(Sink.head)
  }

  def getInstanceInfo()(implicit mat: Materializer, ec: ExecutionContext): Future[InstanceInfo] = {
    Source.single(HttpRequest(uri = Uri(metadataRoot + ec2DynamicdataRoot + instanceIdentityDocument)))
      .via(connectionFlow)
      .via(stringResponseFlow())
      .map(json => Jackson.fromJsonString(json, classOf[InstanceInfo]))
      .runWith(Sink.head)
  }

  def stringResponseFlow()(implicit mat: Materializer, ec: ExecutionContext): Flow[HttpResponse, String, NotUsed] = Flow[HttpResponse].mapAsync(1) { response =>
    if (response.entity.isKnownEmpty()) {
      response.discardEntityBytes().future().map(_ => "")
    } else {
      val charset = response.entity.contentType.charsetOption.map(_.nioCharset()).getOrElse(StandardCharsets.UTF_8)
      response.entity.toStrict(3 seconds).flatMap { e =>
        e.dataBytes
          .fold(ByteString.empty)(_ ++ _)
          .map(_.decodeString(charset)).runWith(Sink.head)
      }
    }
  }

  def stringListResponseFlow()(implicit mat: Materializer, ec: ExecutionContext): Flow[HttpResponse, Seq[String], NotUsed] = Flow[HttpResponse].mapAsync(1) { response =>
    if (response.entity.isKnownEmpty()) {
      response.discardEntityBytes().future().map(_ => Nil)
    } else {
      val charset = response.entity.contentType.charsetOption.map(_.nioCharset()).getOrElse(StandardCharsets.UTF_8)
      response.entity.toStrict(3 seconds).flatMap { e =>
        e.dataBytes
          .via(Framing.delimiter(ByteString("\n"), maximumFrameLength = 256))
          .map(_.decodeString(charset)).runWith(Sink.seq)
      }
    }
  }
}

object EC2MetaDataClient {
  def apply(settings: Option[ClientConnectionSettings] = None)(implicit system: ActorSystem) = new EC2MetaDataClient {
    override val connectionFlow: Flow[HttpRequest, HttpResponse, Future[OutgoingConnection]] = Http().outgoingConnection(EC2MetaDataResources.serviceAddress, settings = settings.getOrElse(ClientConnectionSettings(system)))
  }
}
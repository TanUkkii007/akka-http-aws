package tanukkii.akkahttp.aws

import com.amazonaws.AmazonServiceException
import com.amazonaws.http.HttpResponseHandler

trait AWSServiceContext[S <: AWSService] {
  val service: S
  val errorResponseHandler: HttpResponseHandler[AmazonServiceException]
}

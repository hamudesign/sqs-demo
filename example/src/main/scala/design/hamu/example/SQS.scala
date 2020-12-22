package design.hamu.example

import cats.effect.{ContextShift, IO}
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import software.amazon.awssdk.services.sqs.model._

object SQS {
  def getMessages(implicit cs: ContextShift[IO], sqs: SqsAsyncClient): IO[ReceiveMessageResponse] = {
    val request = ReceiveMessageRequest
      .builder
      .queueUrl("https://sqs.us-east-1.amazonaws.com/567207639264/kai-test")
      .maxNumberOfMessages(10)
      .waitTimeSeconds(2)
      .messageAttributeNames("All")
      .build

    IOUtil.fromJavaFuture(sqs.receiveMessage(request))
  }

  def deleteMessage(message: Message)(implicit cs: ContextShift[IO], sqs: SqsAsyncClient): IO[DeleteMessageResponse] = {
    val request = DeleteMessageRequest
      .builder
      .queueUrl("https://sqs.us-east-1.amazonaws.com/567207639264/kai-test")
      .receiptHandle(message.receiptHandle)
      .build

    IOUtil.fromJavaFuture(sqs.deleteMessage(request))
  }
}

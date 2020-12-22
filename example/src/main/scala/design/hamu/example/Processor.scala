package design.hamu.example

import cats.implicits._
import cats.effect.{ContextShift, IO}
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import software.amazon.awssdk.services.sqs.model.{Message, ReceiveMessageResponse}
import sphynx.LazyLogging

import scala.collection.convert.DecorateAsScala

object Processor extends LazyLogging[IO] with DecorateAsScala {
  def process(response: ReceiveMessageResponse)(implicit cs: ContextShift[IO], sqs: SqsAsyncClient): IO[Unit] =
    for {
      _ <- logger.info("Received batch of messages...")
      _ <- response
        .messages
        .asScala
        .toList
        .map(process)
        .sequence
      _ <- logger.info("Done processing batch.")
    } yield ()

  def process(message: Message)(implicit cs: ContextShift[IO], sqs: SqsAsyncClient): IO[Unit] =
    unsafeProcess(message).handleError {
      case e =>
        logger.error(s"Error encountered when processing message with id ${message.messageId}", e)
    }

  def unsafeProcess(message: Message)(implicit cs: ContextShift[IO], sqs: SqsAsyncClient): IO[Unit] =
    for {
      _ <- logger.info(s"Processing message with id ${message.messageId}...")
      _ <- logger.info(message.body)
      _ <- logger.info(message.messageAttributes.toString)
      _ <- IO.raiseError(new Exception("Something went boom!"))
      _ <- logger.info(s"Done procesing. Deleting message with id ${message.messageId}...")
      _ <- SQS.deleteMessage(message)
      _ <- logger.info(s"Done deleting message with id ${message.messageId}...")
    } yield ()
}

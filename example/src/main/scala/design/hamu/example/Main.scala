package design.hamu.example

import cats.implicits._
import cats.effect.{ExitCode, IO, IOApp}
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import sphynx.LazyLogging

object Main extends IOApp with LazyLogging[IO] {

  implicit val sqs = SqsAsyncClient.builder.build

  def run(args: List[String]): IO[ExitCode] =
    for {
      _ <- consume
    } yield ExitCode.Success

  def consume: IO[Unit] =
    for {
      _ <- logger.info("Sending request to sqs to get messages...")
      response <- SQS.getMessages
      _ <- logger.info("Received response from sqs to get messages...")
      _ <- Processor.process(response)
      _ <- consume
    } yield ()
}

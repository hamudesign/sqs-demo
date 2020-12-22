package design.hamu.example

import java.util.concurrent.CompletableFuture

import cats.effect.{Async, ContextShift, IO}

object IOUtil {
  def fromJavaFuture[A](f: CompletableFuture[A])(implicit cs: ContextShift[IO]): IO[A] = {
    val io = IO.async[A] { cb =>
      f.whenComplete {
        case (res, null) => cb(Right(res))
        case (_, e) => cb(Left(e))
      }
    }
    Async[IO].guarantee(io)(cs.shift)
  }
}

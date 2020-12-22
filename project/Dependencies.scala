package design.hamu

import sbt._

object Dependencies {
  object FS2 {
    private val version = "2.4.0"
    val core = "co.fs2" %% "fs2-core" % version
  }

  object Logback {
    private val version = "1.2.3"
    val classic = "ch.qos.logback" %  "logback-classic" % version
  }

  object Sphynx {
    private val version = "0.0.1"
    val core = "design.hamu" %% "sphynx-core" % version
    val circe = "design.hamu" %% "sphynx-circe" % version
  }

  object Cats {
    private val version = "2.1.1"
    val core = "org.typelevel" %% "cats-core" % version
    val effect = "org.typelevel" %% "cats-effect" % version
  }

  object Circe {
    private val version = "0.13.0"
    val core =  "io.circe" %% "circe-core" % version
    val generic = "io.circe" %% "circe-generic" % version
    val parser = "io.circe" %% "circe-parser" % version
  }

  object Http4s {
    private val version = "0.20.22"
    val core = "org.http4s" %% "http4s-dsl" % version
    val server = "org.http4s" %% "http4s-blaze-server" % version
    val client = "org.http4s" %% "http4s-blaze-client" % version
    val circe = "org.http4s" %% "http4s-circe" % version
  }

  object SCodec {
    private val version = "1.11.7"
    val core = "org.scodec" %% "scodec-core" % version
    val stream = "org.scodec" %% "scodec-stream" % "2.0.0"
  }

  object Scalatest {
    private val version = "3.2.0"
    val core: ModuleID = "org.scalatest" %% "scalatest" % version
  }

  object AWSSDKV2 {
    private val version = "2.15.9"
    val core: ModuleID = "software.amazon.awssdk" % "core" % version
    val sts: ModuleID = "software.amazon.awssdk" % "sts" % version
    val dynamodb: ModuleID = "software.amazon.awssdk" % "dynamodb" % version
    val sqs: ModuleID = "software.amazon.awssdk" % "sqs" % version
  }
}

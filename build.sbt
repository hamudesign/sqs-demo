import microsites._
import design.hamu.{CompilerPlugins, Dependencies}
import sbtassembly.{AssemblyKeys}

lazy val commonSettings = Seq(
  scalaVersion := "2.12.10",
  organization := "design.hamu",
  version := "0.0.1",
  scalacOptions := Seq("-Xlint", "-Ywarn-unused", "-deprecation"),
  dependencyUpdatesFailBuild := true
)

lazy val root = project
  .in(file("."))

lazy val example = project
  .in(file("example"))
  .settings(
    name := "example",
    commonSettings,
    assemblySettings,
    libraryDependencies ++= Seq(
      Dependencies.Sphynx.core,
      Dependencies.Sphynx.circe,
      Dependencies.AWSSDKV2.core,
      Dependencies.AWSSDKV2.sts,
      Dependencies.AWSSDKV2.sqs,
      Dependencies.Logback.classic,
      Dependencies.Cats.core,
      Dependencies.Circe.core,
      Dependencies.Circe.parser,
      Dependencies.Circe.generic,
      Dependencies.Http4s.core,
      Dependencies.Http4s.server,
      Dependencies.Http4s.circe
    ) ++ Seq(
      Dependencies.Scalatest.core
    ).map(_ % "test"),
    addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)
  )

lazy val assemblySettings = Seq(
  test in AssemblyKeys.assembly := {},
  AssemblyKeys.assemblyJarName in AssemblyKeys.assembly := name.value + ".jar",
  AssemblyKeys.assemblyMergeStrategy in AssemblyKeys.assembly := {
    case PathList(ps @ _*) if Assembly.isConfigFile(ps.last) =>
      MergeStrategy.concat
    case "deriving.conf" => MergeStrategy.concat
    case PathList("META-INF", "io.netty.versions.properties") =>
      MergeStrategy.filterDistinctLines
    case PathList("io", "netty", _ @_*) => MergeStrategy.first
    case PathList("META-INF", "MANIFEST.MF") => MergeStrategy.discard
    case PathList(ps @ _*)
      if Set(
        "service-2.json",
        "waiters-2.json",
        "customization.config",
        "paginators-1.json",
        "examples-1.json",
        "module-info.class",
        "mime.types"
      ).contains(ps.last) =>
      MergeStrategy.discard
    case x => MergeStrategy.defaultMergeStrategy(x)
  }
)

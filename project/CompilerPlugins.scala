package design.hamu

import sbt._

object CompilerPlugins {
  object MacroParadise {
    private val version = "2.1.1"
    val core = "org.scalamacros" % "paradise" % version
  }
}

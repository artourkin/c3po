import sbt._
import Keys._
import sbtassembly.AssemblyPlugin.autoImport._

object BuildSettings {

  val buildOrganization = "com.petpet"
  val buildVersion = "0.5.0"

  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := buildOrganization,
    version := buildVersion
    //shellPrompt  := ShellPrompt.buildShellPrompt
  )
}

//assemblyJarName in assembly := "c3po-cmd.jar"
//mainClass in assembly := Some("play.core.server.ProdServerStart")
//fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)


object C3PO extends Build {

  val commonSettings = Seq(
    assemblyMergeStrategy in assembly := {
      case PathList("reference.conf") => MergeStrategy.concat
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case x => MergeStrategy.first
      },
    publishArtifact in (Compile, packageDoc) := false,
    publishArtifact in packageDoc := false,
    sources in (Compile,doc) := Seq.empty,
    scalaVersion := "2.11.8"
  )

  lazy val c3po = project.in(file(".")).settings(commonSettings)
    .aggregate(c3pocmd, c3powebapi)
    .dependsOn(c3pocmd, c3powebapi)

  lazy val c3powebapi = Project(
    "c3po-webapi",
    file("c3po-webapi")).enablePlugins(play.PlayJava).settings( //play.sbt.Play
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-mailer" % "3.0.1"
    )).settings(commonSettings) dependsOn (c3pocore)

  lazy val c3pocmd = Project(
    "c3po-cmd",
    file("c3po-cmd"),
    settings = Seq(libraryDependencies ++=Seq(
      "com.beust" % "jcommander" % "1.30"
    ))).settings(commonSettings) dependsOn (c3pocore)

  lazy val c3pocore = Project(
    "c3po-core",
    file("c3po-core"),
    settings =  Seq(libraryDependencies ++= Seq(
      "com.opencsv" % "opencsv" % "3.3",
      "com.fasterxml.jackson.core" % "jackson-databind" % "2.5.1",
      "com.google.code.gson" % "gson" % "2.3.1",
      "org.mongodb" % "mongo-java-driver" % "3.4.0",
      "org.apache.commons" % "commons-compress" % "1.2",
      "org.apache.commons" % "commons-digester3" % "3.2",
      "dom4j" % "dom4j" % "1.6.1",
      "org.simpleframework" % "simple-xml" % "2.7.1",
      "log4j" % "log4j" % "1.2.17",
      "junit" % "junit" % "4.12",
      "org.mockito" % "mockito-all" % "1.10.19"
    ))).settings(commonSettings) dependsOn (c3poapi)

  lazy val c3poapi = Project(
    "c3po-api",
    file("c3po-api"),
    settings =  Seq(
      libraryDependencies ++= Seq(
      "org.slf4j" % "slf4j-api" % "1.7.21",
      "commons-io" % "commons-io" % "2.4"
    ))).settings(commonSettings)


}
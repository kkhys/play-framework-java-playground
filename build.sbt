name := """play-framework-java-playground"""
organization := "me.kkhys"
maintainer := "hi@kkhys.me"

ThisBuild / scalaVersion := "3.3.1"

ThisBuild / version := "1.0.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .enablePlugins(SbtWeb)
  .settings(
    libraryDependencies ++= Seq(
      guice,
      "com.google.inject" % "guice" % "5.1.0",
      "com.google.inject.extensions" % "guice-assistedinject" % "5.1.0"
    )
  )

PlayKeys.playRunHooks += baseDirectory.map(PlayDevRunHook.apply).value

Assets / pipelineStages := Seq(digest)

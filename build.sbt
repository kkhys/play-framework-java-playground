import scala.sys.process.Process

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

lazy val frontEndBuild = taskKey[Unit]("Execute frontend build command")

val frontendPath = "frontend"
val frontEndFile = file(frontendPath)

frontEndBuild := {
  println(Process("pnpm install", frontEndFile).!!)
  println(Process("pnpm vite:build", frontEndFile).!!)
  println(Process("pnpm tailwind:build", frontEndFile).!!)
}

dist := (dist dependsOn frontEndBuild).value
stage := (stage dependsOn dist).value

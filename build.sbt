ThisBuild / scalaVersion := "3.3.1"
ThisBuild / version := "1.0.0-SNAPSHOT"

PlayKeys.playRunHooks += baseDirectory.map(PlayDevRunHook.apply).value

lazy val root = (project in file("."))
  .enablePlugins(PlayJava)
  .enablePlugins(SbtWeb)
  .settings(
    name := """play-framework-java-playground""",
    libraryDependencies ++= Seq(
      guice,
      "com.google.inject" % "guice" % "5.1.0",
      "com.google.inject.extensions" % "guice-assistedinject" % "5.1.0"
    )
  )

Assets / pipelineStages := Seq(digest)

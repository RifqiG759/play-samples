import play.core.PlayVersion.{ current => playVersion }
lazy val flyway = (project in file("modules/flyway"))
  .enablePlugins(FlywayPlugin)
  .settings(Common.scalaSettings)

lazy val api = (project in file("modules/api"))
  .settings(Common.projectSettings)

lazy val slick = (project in file("modules/slick"))
  .enablePlugins(CodegenPlugin)
  .settings(Common.projectSettings)
  .aggregate(api)
  .dependsOn(api)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """play-scala-isolated-slick-example""",
    version := "1.1-SNAPSHOT",
    scalaVersion := "2.13.1",
    TwirlKeys.templateImports += "com.example.user.User",
    libraryDependencies ++= Seq(
      guice,
      "com.h2database" % "h2" % "1.4.199",
      "org.flywaydb" % "flyway-core" % "6.1.0",
      ws % Test,
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
    ),
    fork in Test := true
  )
  .aggregate(slick)
  .dependsOn(slick)

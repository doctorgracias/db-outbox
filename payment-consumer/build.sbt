ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.14"

lazy val root = (project in file("."))
  .settings(
    name := "payment-polling"
  )

val zioVersion   = "2.1.6"
val slickVersion = "3.5.1"
val tapirVersion = "1.13.1"

libraryDependencies ++= Seq(
  // ZIO
  "dev.zio" %% "zio"         % zioVersion,
  "dev.zio" %% "zio-streams" % zioVersion,

  // Slick
  "com.typesafe.slick" %% "slick"          % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,

  // PostgreSQL
  "org.postgresql" % "postgresql" % "42.7.3",

  // Логирование
  "ch.qos.logback" % "logback-classic" % "1.5.6",

  // Tapir + Netty (всё в одном)
  "com.softwaremill.sttp.tapir" %% "tapir-netty-server-zio" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe"       % tapirVersion,

  "com.softwaremill.sttp.model" %% "core" % "1.7.11"

)
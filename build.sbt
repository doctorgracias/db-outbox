ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.16"

val zioVersion   = "2.1.6"
val slickVersion = "3.5.1"
val tapirVersion = "1.13.1"

val commonDependencies = Seq(
  "dev.zio" %% "zio"         % zioVersion,
  "dev.zio" %% "zio-streams" % zioVersion,
  "com.typesafe.slick" %% "slick"          % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "org.postgresql" % "postgresql" % "42.7.3",
  "ch.qos.logback" % "logback-classic" % "1.5.6",
  "com.softwaremill.sttp.tapir" %% "tapir-netty-server-zio" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-json-circe"       % tapirVersion,
  "com.softwaremill.sttp.model" %% "core" % "1.7.11"
)

lazy val serviceDbSaver = (project in file("service-db-saver"))
  .settings(
    name := "service-db-saver",
    libraryDependencies ++= commonDependencies
  )

lazy val serviceEventMaker = (project in file("service-event-maker"))
  .settings(
    name := "service-event-maker",
    libraryDependencies ++= commonDependencies
  )

lazy val root = (project in file("."))
  .aggregate(serviceDbSaver, serviceEventMaker)
  .settings(
    name := "db-outbox-root"
  )
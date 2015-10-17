name := """broadcaast"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  filters,
  "org.mindrot"            % "jbcrypt"    % "0.3m",
  "joda-time"              % "joda-time"  % "2.7",
  "postgresql"             % "postgresql" % "9.1-901.jdbc4"
)

maintainer in Docker := "Owain Lewis <owain@owainlewis.com>"

dockerExposedPorts in Docker := Seq(9000, 9443)

dockerBaseImage := "dockerfile/java:oracle-java8"

dockerEntrypoint := Seq("bin/discusslr", "-Dconfig.resource=application.prod.conf", "-DapplyEvolutions.default=true")

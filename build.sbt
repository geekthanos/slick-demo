

name := """slick-demo"""

version := sys.props.getOrElse("version", default = "1.0.0")

lazy val root = (project in file("."))
  .enablePlugins(PlayJava,PlayScala)


scalaVersion := "2.12.2"

resolvers ++= Seq(
  "Local Maven" at Path.userHome.asFile.toURI.toURL + ".m2/repository",
  "Sonatype OSS Repository" at "https://oss.sonatype.org/content/groups/public",
  "Sonatype OSS Snapshot" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Gradle M2 Repository" at "https://plugins.gradle.org/m2/"
)
libraryDependencies += guice
libraryDependencies ++= Seq(
  //Slick is a Functional Relational Mapping for Scala
  "com.typesafe.play" %% "play-slick" % "3.0.1",
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.0.0" % "test",

  //slick postgres extension for data types
  "com.github.tminglei" %% "slick-pg" % "0.16.1",
  "com.github.tminglei" %% "slick-pg_play-json" % "0.16.1",
  //postgres driver
  "org.postgresql" % "postgresql" % "42.1.4",

  //embedded postgres
  //"com.opentable.components" % "otj-pg-embedded" % "0.10.0",
  "ru.yandex.qatools.embed" % "postgresql-embedded" % "2.5",
)
import scala.util.Properties
// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.6.7")

resolvers += Resolver.url(

  "bintray-sbt-plugin-releases",
  url("https://dl.bintray.com/en-japan/sbt"))(
  Resolver.ivyStylePatterns)


resolvers += "Looppay Snapshot" at Properties.envOrElse("SPAY_SNAPSHOT_REPO", "http://devmvnrepo.looppay.com/repository/snapshots/")
resolvers += "Looppay Repository" at Properties.envOrElse("SPAY_REPO", "http://devmvnrepo.looppay.com/repository/internal/")


resolvers += Resolver.url("Typesafe Ivy Snapshots Repository", url("https://oss.sonatype.org/content/repositories/snapshots/"))(Resolver.ivyStylePatterns)

resolvers += "En Japan" at "https://raw.github.com/en-japan/repository/master/releases"

resolvers += "JAnalyse Repository" at "http://www.janalyse.fr/repository/"

//Adds support for building debian and docker deployments
addSbtPlugin("com.typesafe.sbt" % "sbt-play-enhancer" % "1.2.2")
resolvers += Resolver.url("gatling",url("http://dl.bintray.com/content/gatling/sbt-plugins/"))(Resolver.ivyStylePatterns)
resolvers += "Era7 maven releases" at "https://s3-eu-west-1.amazonaws.com/releases.era7.com"

addSbtPlugin("io.gatling" % "gatling-build-plugin" % "2.0.2")

addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "0.8.0")

addSbtPlugin("ohnosequences" % "sbt-s3-resolver" % "0.16.0")

addMavenResolverPlugin

import sbt._
import sbt.Keys._
import io.gatling.build.license._
import io.gatling.build.LicenseKeys._
import io.gatling.build.ReleaseProcessKeys._
import io.gatling.build.MavenPublishKeys._

object BuildSettings {

  lazy val basicSettings = Seq(
    organization            := "io.gatling.highcharts",
    githubPath              := "gatling/gatling-highcharts",
    projectDevelopers       := developers,
    license                 := HighCharts,
    useSonatypeRepositories := true,
    skipSnapshotDepsCheck   := true,
    isSnapshot              := version.value.endsWith("-SNAPSHOT"),
    pushToPrivateNexus      := false
  )

  lazy val publishSettings = {
    import ohnosequences.sbt.SbtS3Resolver.autoImport._

    val repoSuffix = "mvn-repo.miuinsights.com"
    val releaseRepo: s3 = s3(s"releases.$repoSuffix")
    val snapshotRepo: s3 = s3(s"snapshots.$repoSuffix")

    Seq(
      resolvers ++= {
        val releases = s3resolver.value("Releases resolver", releaseRepo).withIvyPatterns
        val snapshots = s3resolver.value("Snapshots resolver", snapshotRepo).withIvyPatterns
        Seq(releases, snapshots)
      },
      publishMavenStyle := false,
      publishTo := {
        val repo = if (isSnapshot.value) snapshotRepo else releaseRepo
        Some(s3resolver.value(s"$repo S3 bucket", repo).withIvyPatterns)
      },
      publishArtifact in(Compile, packageDoc) := false
    )
  }

  val developers = Seq(
    GatlingDeveloper("slandelle@gatling.io", "Stephane Landelle", isGatlingCorp = true),
    GatlingDeveloper("gcorre@gatling.io", "Guillaume Corré", isGatlingCorp = true)
  )

  lazy val gatlingHighchartsModuleSettings = basicSettings ++ publishSettings

  lazy val noArtifactToPublish =
    publishArtifact in Compile := false
}

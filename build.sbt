val scala3Version = "3.2.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "CryptographyAndSecurityLaboratoryWorks",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := scala3Version,

    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.29" % Test,
      "dev.zio" %% "zio" % "2.0.0",
      "io.d11" %% "zhttp" % "2.0.0-RC11",

      // Java Dependencies
      "de.taimos" % "totp" % "1.0",
      "commons-codec" % "commons-codec" % "1.10"
    )
  )

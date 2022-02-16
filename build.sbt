val scala3Version = "3.1.1"
val sttpClient3Version = "3.4.1"
val zioVersion = "2.0.0-RC2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "WeatherServer",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,

    libraryDependencies ++= List(
      "dev.zio" % "zio-test_3" % zioVersion,
      "dev.zio" %% "zio-test" % zioVersion % "test",
      "dev.zio" %% "zio-test-sbt" % zioVersion % "test",
      "com.novocode" % "junit-interface" % "0.11" % "test",
      "com.softwaremill.sttp.client3" %% "circe" % "3.4.1",
      "io.d11" % "zhttp_3" % "2.0.0-RC3",
      "io.circe" %% "circe-generic" % "0.14.1"
    ),

    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )



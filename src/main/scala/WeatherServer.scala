
// for scala-cli
//> using lib "io.d11:zhttp_3:2.0.0-RC3"

import zhttp.http._
import zhttp.service.Server
import zio._

object WeatherServer extends App {

  // Create HTTP route, just a data structure (an effect)
  val app: HttpApp[Any, Nothing] = Http.collect[Request] {
    case req @ Method.GET -> !! / "weatherQuery" =>
      val params = req.url.queryParams

      // error checking by me is missing here
      val lat = params("lat").head.toDouble
      val lon = params("lon").head.toDouble

      weatherQuery(lat, lon) match
        case Right(weatherData) =>
          Response.json(weatherData.toString)
        case Left(error) =>
          Response.text(error.toString)
  }

  // Run it like any simple app
  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    Server.start(8090, app).exitCode
}

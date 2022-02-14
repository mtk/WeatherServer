
// for scala-cli
//> using lib "com.softwaremill.sttp.client3::core:3.4.1"
//> using lib "com.softwaremill.sttp.client3::circe:3.4.1"
//> using lib "io.d11:zhttp_3:2.0.0-RC3"
//> using lib "io.circe::circe-generic:0.14.1"

// todo
// - pretty stacktraces
// - logging

import sttp.client3.quick._
import sttp.client3.circe._
import io.circe.syntax._
import io.circe.generic.auto._

// the model

// openweather guys have NOT documented the API.  the encoding below is surely flawed.
// every Option[blah] was an empirical discovery.
// i have submitted a pair of bug reports to them and a suggestion that they document
// what is optional and what is always present.  from a safety point of view, you could
// argue that every field should be Option'al but that would be tedious.

type TS = Int                           // unix time.  but
                                        // org.joda.time.DateTime?
                                        // DateTime would need circe
                                        // codecs.

case class Coord (lon: Double, lat: Double)
case class Weather (id: Int, main: String, description: String, icon: String)
type Base = String                      // internal parameter??
case class WMain (temp: Double, feels_like: Double, temp_min: Option[Double], temp_max: Option[Double], pressure: Int, humidity: Int, sea_level: Option[Int], grnd_level: Option[Int])
type Visibility = Int                   // undocumented??? examples seem to be Int?
case class Wind (speed: Double, deg: Int, gust: Option[Double])
case class Clouds (all: Double)
case class Rain (`1h`: Option[Double], `3h`: Option[Double])
case class Snow (`1h`: Option[Double], `3h`: Option[Double]) 
type Dt = TS                           // unix time
// type, id, and message are 'internal' parameters??
// sunrise and sunset are unix times
case class Sys (`type`: Option[Int], id: Option[Int], message: Option[Double], country: Option[String], sunrise: TS, sunset: TS)
type WTimeZone = Int                    // shift in seconds from UTC
type Id = Int                           // city ID
type Name = String                      // city name
type Cod = Int                          // internal parameter??

case class WeatherResponse (
  coord: Coord,
  weather: List[Weather],
  base: Base,
  main: WMain,
  visibility: Visibility,
  wind: Wind,
  clouds: Clouds,
  rain: Option[Rain],
  snow: Option[Snow],
  dt: Dt,
  sys: Sys,
  timezone: WTimeZone,
  id: Id,
  name: Name,
  cod: Cod
)

def weatherQuery(lat: Double, lon: Double) =
  // units=kelvin, imperial, metric
  // appid needs to come from a config, not part of the git repo
  val request = basicRequest
    .get(uri"https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&units=imperial&appid=e1f275e3a303b94e45642c452568ea22")
    .response(asJson[WeatherResponse])

  // synchronous (on purpose)
  val response = request.send(backend)

  response.body


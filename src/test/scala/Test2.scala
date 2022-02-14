package zhttp.http.middleware

import zhttp.http._
import zio.test.Assertion.equalTo
import zio.test._

object Spec extends DefaultRunnableSpec {

  def spec = suite("http")(
    testM("1 + 1 = 2") {
      val app: Http[Any, Nothing, Int, Int] = Http.fromFunction[Int](_ + 1)
      assertM(app(1))(equalTo(2))
    }
  )
}

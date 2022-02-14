I considered a number of Scala 3 web frameworks.  Play was oldest but
it doesn't have a future and its examples already generate strange
Guice errors, at least on my desktop.

I looked at zio-http but its documentation said that its client was
under development.  So I settled on sttp from the SoftwareMill people
for the client.  They run a large microservice-based web farm and sttp
looked simple.  But their web server stuff seemed complicated in
comparison to zio-http.  So I wound up writing a simple synchronous
client using sttp and a server using zio-http.  If the client ever
needed to be parallel for performance reasons, ZIO's support for
concurrency should make this straightforward.  Later I discovered that
zio-http does have a client but did not want to revisit this in the
short term.

Circe is the underlying JSON codec for sttp.  Circe documentation is
terrible.  Circe's error messages are terrible.  But it works.  The
OpenWeather API is difficult to use with strongly typed languages.
Their documentation doesn't list what is optional and what isn't.
Every Option[Blah] field in the core model was something I stumbled
over while writing the code, not something that was documented.  I
have even observed fields that are not documented at all.  Both of
these issues have been reported as bugs to them by
me.

This project was ambitious because of its use of Scala 3, of ZIO
2.0.0-RC2, and zio-http 2.0.0-RC3.  It was primarily developed in GNU
Emacs using metals (which worked well) and sbt.  But i also used the
relatively new scala-cli tool for quick runs.  And in spite of
throwing more Exceptions than the universe should allow, the EAC
release of Intellij along with the nightly Scala plugin managed to let
me debug things.  It all works.

I have never written a program with *any* of these API's before.
Climbing the learning curve under time pressure wasn't fun or easy.
But having passed through this gauntlet, I could write this in an
order of magnutude less time in a second round.

"sbt run" will fire up the server running on local host.  You can test
it with the URL
"http://localhost:8090/weatherQuery?lat=40.730610&lon=-73.935242" (New
York City).

- Mark Kennedy
  mtk@acm.org
  
  

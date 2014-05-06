package scalaxb

import play.libs.WS
import play.libs.WS.Response

trait PlayHttpClients extends HttpClients {
  val httpClient = new PlayHttpClient {}

  trait PlayHttpClient extends HttpClient {
    import dispatch._

    def request(in: String, address: java.net.URI, headers: Map[String, String]): String = {
      val ws = WS.url(address.toString)
      headers.map { case(key, value) =>
        ws.setHeader(key, value)
      }
      ws.post(in).get().getBody()
    }
  }
}

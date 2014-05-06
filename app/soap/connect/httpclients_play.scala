package scalaxb

import play.libs.WS
import play.libs.WS.Response

trait PlayHttpClients extends HttpClients {
  val httpClient = new PlayHttpClient {}

  trait PlayHttpClient extends HttpClient {

    def request(webMethod: String, in: String, address: java.net.URI, headers: Map[String, String]): String = {
      val ws = WS.url(address.toString())
      headers.map { case(key, value) =>
        ws.setHeader(key, value)
      }
      //ws.post(in).get().getBody()
      ws.post(in).get().getBody()
    }
  }
}

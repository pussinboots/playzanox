package scalaxb

import play.api.libs.ws.WS
import play.api.libs.ws.Response

trait PlayHttpClients extends HttpClients {
  val httpClient = new PlayHttpClient {}

  trait PlayHttpClient extends HttpClient {

    def request(webMethod: String, in: String, address: java.net.URI, headers: Map[String, String]): String = {
      val ws = WS.url(address.toString())
      headers.map { case(key, value) =>
        ws.withHeaders(key -> value)
      }
      import play.api.libs.concurrent.Execution.Implicits._
      val result = ws.post(in).map{
        response =>
          response.body
      }
      result.value.get.get
    }
  }
}

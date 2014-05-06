package scalaxb

trait HttpClients {
  val httpClient: HttpClient

  trait HttpClient {
    def request(webMethod: String, in: String, address: java.net.URI, headers: Map[String, String]): String
  }
}

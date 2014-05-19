package controllers

import java.io.File
import java.io.IOException
import org.apache.commons.io.FileUtils
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.Play.current
import views._
import dispatch._, Defaults._
import soap.connect.utils.ConnectClient
import spray.json._
import DefaultJsonProtocol._
import soap.connect.GetSessionResponse
import soap.connect.SessionType
import dispatch.as.File
import java.io.File
import java.io.FileWriter
import java.io.FileReader

object MyJsonProtocol extends DefaultJsonProtocol {
  implicit val sessionType = jsonFormat5(SessionType)
  implicit val getSessionResponse = jsonFormat1(GetSessionResponse)
}

object Application extends Controller {

  def index = Action {
    Ok(html.index("Your new application is ready."))
  }

  def phones(phoneId: String) = Action {
    print(phoneId)
    val jsonFile = Play.getFile("public/phones/" + phoneId)
    val json = FileUtils.readFileToString(jsonFile)
    Ok(json).as("application/json")
  }

  def proxy(fullUrl: String) = Action {

    val localResult = loadFromDisk(fullUrl)
    val result = localResult.getOrElse({
      val response = Http(url(fullUrl) OK as.String)
      println("call " + fullUrl)
      response()
    })
    writeResponseToDisk(fullUrl, result)
    Ok(result).as("application/json").withHeaders("Cache-Control" -> "public, max-age=300, s-maxage=60")
  }
  
  def proxyPut(fullUrl: String) = Action { request =>
     println("put data " + request.body.asJson.get)
     
    val response = Http(url(fullUrl).PUT << request.body.asJson.get.toString OK as.String)
    val result = response()
    Ok(result).as("application/json").withHeaders("Cache-Control" -> "public, max-age=300, s-maxage=60")
  }

  def loadFromDisk(fullUrl: String): Option[String] = {
    return None
    val file = localFile(fullUrl)
    if (file.exists()) {
      return Option(FileUtils.readFileToString(file))
    }
    return None
  }

  def writeResponseToDisk(fullUrl: String, result: String) {
	return
    val file = localFile(fullUrl)
    if (!file.exists()) {
      file.createNewFile();
    }
    println("file " + file.getAbsolutePath())
    val fw = new FileWriter(file)
    fw.write(result)
    fw.close()
  }

  def localFile(fullUrl: String) = {
    val urlI = url(fullUrl);
    val queryParams = urlI.build().getQueryParams()
    queryParams.remove("connectId")
    queryParams.remove("date")
    queryParams.remove("nonce")
    queryParams.remove("signature")
    val fileName = urlI.build().getUrl().replace("/", "_")
    new File("dump/" + fileName.substring(38) + ".json")
  }

  def connect(authToken: String) = Action {
    val resp = ConnectClient.getSession(authToken)
    import MyJsonProtocol._
    Ok(resp.toJson.toString).as("application/json")
  }
}
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
import dispatch._
import soap.connect.utils.ConnectClient
import spray.json._
import DefaultJsonProtocol._
import org.zanox.connect.GetSessionResponse
import org.zanox.connect.SessionType

object MyJsonProtocol extends DefaultJsonProtocol {
	implicit val sessionType = jsonFormat5(SessionType)
  implicit val getSessionResponse= jsonFormat1(GetSessionResponse)
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
  
  def proxy(fullUrl : String) = Action {
    println("call" + fullUrl)
    val response = Http(url(fullUrl) OK as.String)
    Ok(response()).as("application/json").withHeaders("Cache-Control" -> "public, max-age=60, s-maxage=60")
//    Ok("").as("application/json")
  }

  def connect(authToken : String) = Action {
    val resp = ConnectClient.getSession(authToken)
    import MyJsonProtocol._
    Ok(resp.toJson.toString).as("application/json")
  }
}
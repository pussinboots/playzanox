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
    println(fullUrl)
    val response = Http(url(fullUrl) OK as.String)
    Ok(response()).as("application/json")
  }

}
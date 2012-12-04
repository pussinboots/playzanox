package soap.connect

import scalaxb._
import org.zanox.connect.ConnectServiceBindings
import soap.connect.utils.SecurityUtils
import java.util.Calendar
import java.net.URLEncoder
import java.net.URLDecoder
import dispatch._
//import com.zanox.api.version._2011_03_01.ApiClient
//import com.zanox.api.lib.constants.ApiConst

object Main {
  def main(args: Array[String]) {

//    val CONNECT_ID = "989D3B84527BA8613921";
//    val SECRET_KEY = "3bAF75504b684b+8a2f5dda65bdd79/8fD751742";
//
//    val api = new ApiClient("json").getService();
//
//    api.setConnectId(CONNECT_ID);
//    api.setSecretKey(SECRET_KEY);
//
//    api.setHttpProtocol(ApiConst.HTTPS);
//    
//    println(api.getProfile());

    val connect = new ConnectServiceBindings with Soap11Clients with DispatchHttpClients {}

    println {

      val timeStamp = "Tue, 04 Dec 2012 21:17:03 GMT";
      val connectId = "46126FA4B29AF678D770";
      val nonce = "13546558232241354655823224" //System.nanoTime().toString + System.nanoTime().toString;
      val now = Calendar.getInstance();
      //      val timeStamp = SecurityUtils.parseRestDateCalendarToString(now);

      val signature = SecurityUtils.createRestSignature("59262213C1dd49+5a38b11132764A9/FAEcc7d41", nonce, "/profiles/", "GET", timeStamp)
      //      val signature = SecurityUtils.createSoapSignature("b4443E372b5647+db92f42efb73aEf/ae1a8864f", nonce, "/profiles/", "GET", timeStamp)

      val fullUrl = "https://api.zanox.com/json/profiles/?date=" + URLEncoder.encode(timeStamp, "UTF-8") + "&connectid="+connectId + "&nonce="+nonce+ "&signature="+ URLEncoder.encode(signature, "UTF-8")
      val headers = Map(("Date" ->  URLEncoder.encode(timeStamp, "UTF-8")), "Authorization" -> ("ZXWS " + connectId + ":" + signature), "Nonce" -> nonce)
      println(fullUrl)
      println(headers)

      val zurl = url(fullUrl) // <:< headers
      //
      val response = Http(zurl OK as.String)
      println("Ende " + response())
    }
  }
}
		

package soap.connect.utils

import soap.connect.ConnectServiceBindings
import scalaxb.Soap11Clients
import scalaxb.DispatchHttpClients
import java.util.Calendar

/**
 * @author frank
 *
 */
object ConnectClient {
  val secretKey = System.getenv("secretKey");
  val appId = System.getenv("appid");
  println("appid " + appId)
  println("key " + secretKey)
  val connect = new ConnectServiceBindings with Soap11Clients with DispatchHttpClients {}

  def getSession(authToken: String): soap.connect.GetSessionResponse = {

    val now = Calendar.getInstance();
    val nonce = System.nanoTime().toString + System.nanoTime().toString;
    val timeStamp = SecurityUtils.parseSoapDateCalendarToString(now);
    val signature = SecurityUtils.createSoapSignature(secretKey, nonce, "ConnectService", "getSession", timeStamp)
    println("sig " + signature)
    connect.service.getSession(
        authToken, 
        appId, 
        signature, 
        nonce, 
        timeStamp).right.get
  }
}

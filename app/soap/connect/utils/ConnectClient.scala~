
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
  val secretKey = System.getenv('secretKey')
  val connect = new ConnectServiceBindings with Soap11Clients with DispatchHttpClients {}

  def getSession(authToken: String): soap.connect.GetSessionResponse = {

    val now = Calendar.getInstance();
    val nonce = System.nanoTime().toString + System.nanoTime().toString;
    val timeStamp = SecurityUtils.parseSoapDateCalendarToString(now);
    val signature = SecurityUtils.createSoapSignature(secretKey, nonce, "ConnectService", "getSession", timeStamp)

    connect.service.getSession(
        authToken, 
        "D9E5219494DBF9D44831", 
        signature, 
        nonce, 
        timeStamp).right.get
  }
}

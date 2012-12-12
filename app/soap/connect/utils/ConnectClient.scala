
package soap.connect.utils

import org.zanox.connect.ConnectServiceBindings
import scalaxb.Soap11Clients
import scalaxb.DispatchHttpClients
import java.util.Calendar

/**
 * @author frank
 *
 */
object ConnectClient {

  val connect = new ConnectServiceBindings with Soap11Clients with DispatchHttpClients {}

  def getSession(authToken: String): org.zanox.connect.GetSessionResponse = {

    val now = Calendar.getInstance();
    val nonce = System.nanoTime().toString + System.nanoTime().toString;
    val timeStamp = SecurityUtils.parseSoapDateCalendarToString(now);
    val signature = SecurityUtils.createSoapSignature("b4443E372b5647+db92f42efb73aEf/ae1a8864f", nonce, "ConnectService", "getSession", timeStamp)

    connect.service.getSession(
        authToken, 
        "D9E5219494DBF9D44831", 
        signature, 
        nonce, 
        timeStamp).right.get
  }
}
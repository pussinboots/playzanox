package soap.connect.utils

import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.regex.Pattern
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import sun.misc.BASE64Encoder

final object SecurityUtils {
  def parseSoapDateCalendarToString(now: Calendar): String = {
    val sdfSoap: SimpleDateFormat = new SimpleDateFormat(DATE_FORMAT_SOAP_1)
    sdfSoap.setTimeZone(TimeZone.getTimeZone("GMT"))
    return sdfSoap.format(now.getTime)
  }

  def parseRestDateCalendarToString(now: Calendar): String = {
    val sdfSoap: SimpleDateFormat = new SimpleDateFormat(DATE_FORMAT_REST, Locale.US)
    sdfSoap.setTimeZone(TimeZone.getTimeZone("GMT"))
    return sdfSoap.format(now.getTime)
  }

  /**
   * This method parses the soap dateString to a Calendar Object.
   *
   * @param dateInput the input string
   * @return Calendar the parsed date
   * @throws AuthorizationException thrown on various authentication errors
   * @throws ParseException is thrown if an error by parsing the dateInput string occur
   */
  def parseSoapDateStringToCalendar(dateInput: String): Calendar = {
    if (!Pattern.matches(REGEX_SIMPLE, dateInput)) {
      throw new RuntimeException("The Timestamp is in a wrong format! Has to be like this 2007-12-11T13:16:00 (yyyy-MM-dd'T'HH:mm:ss).")
    }
    val sdfSoap: SimpleDateFormat = new SimpleDateFormat
    sdfSoap.setTimeZone(TimeZone.getTimeZone("GMT"))
    dateInput.length match {
      case INPUT_DATE_LENGTH_1 =>
        sdfSoap.applyPattern(DATE_FORMAT_SOAP_1)
      case INPUT_DATE_LENGTH_2 =>
        sdfSoap.applyPattern(DATE_FORMAT_SOAP_2)
      case INPUT_DATE_LENGTH_3 =>
        sdfSoap.applyPattern(DATE_FORMAT_SOAP_3)
      case INPUT_DATE_LENGTH_4 =>
        sdfSoap.applyPattern(DATE_FORMAT_SOAP_4)
      case _ =>
        return null
    }
    return parseStringToCalendar(sdfSoap, dateInput)
  }

  /**
   * This method parses the soap dateString to a Calendar Object.
   *
   * @param dateInput the input string
   * @return Calendar
   * @throws ParseException is thrown if an error by parsing the dateInput string occur
   */
  def parseRestDateStringToCalendar(dateInput: String): Calendar = {
    val sdfRest: SimpleDateFormat = new SimpleDateFormat(DATE_FORMAT_REST, Locale.US)
    return parseStringToCalendar(sdfRest, dateInput)
  }

  /**
   * This method parses a given dateString to a Calendar Object.
   *
   * @param dateInput the input string
   * @param sdf the specified simpleDateFormat
   * @return the parsed date
   * @throws ParseException is thrown if an error by parsing the dateInput string occur
   */
  private[utils] def parseStringToCalendar(sdf: SimpleDateFormat, dateInput: String): Calendar = {
    var timeStamp: Calendar = null
    sdf.setLenient(false)
    if (dateInput != null) {
      try {
        val dat: Date = sdf.parse(dateInput)
        timeStamp = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
        timeStamp.setTime(dat)
      }
      catch {
        case pe: ParseException => {
          System.err.println("Wrong dateFormat: " + pe.getMessage)
          throw pe
        }
      }
    }
    return timeStamp
  }

  /**
   * Create the soap signature for the specified wsAuthentication, the serviceName, the methodName
   * and the given timeStamp. The soapSignature consists of the sharedKey encoded with the hMac
   * algorithm and a complex string of the serviceName, the invoked methodName and the timeStamp.
   *
   * @param userContext the { @link UserContext} instance that contains the authentication data for
   *                                the current user
   * @param nonce the nonce value submitted from the client
   * @param serviceName the name of the invoked service
   * @param methodName the invoked methodName
   * @param timeStampStr the timeStamp as a string
   * @return String the created secure SOAP signature
   */
  def createSoapSignature(secretKey: String, nonce: String, serviceName: String, methodName: String, timeStampStr: String): String = {
    System.out.println(serviceName.toLowerCase + methodName.toLowerCase + timeStampStr + nonce)
    return generateHmacKey(secretKey.getBytes, serviceName.toLowerCase + methodName.toLowerCase + timeStampStr + nonce)
  }

  def createRestSignature(secretKey: String, nonce: String, uriPath: String, httpVerb: String, timeStampStr: String): String = {
    System.out.println(secretKey + "   " + httpVerb + uriPath.toLowerCase + timeStampStr + nonce)
    return generateHmacKey(secretKey.getBytes, httpVerb + uriPath + timeStampStr + nonce)
  }

  /**
   * The method, to create and build the signature.
   *
   * @param sharedKeyBytes sharedKeyBytes
   * @param canonicalString canonicalString
   * @return the signature
   */
  private def generateHmacKey(sharedKeyBytes: Array[Byte], canonicalString: String): String = {
    val signingKey: SecretKeySpec = new SecretKeySpec(sharedKeyBytes, HMAC_SHA1_ALGORITHM)
    var mac: Mac = null
    try {
      mac = Mac.getInstance(HMAC_SHA1_ALGORITHM)
    }
    catch {
      case nsae: NoSuchAlgorithmException => {
        throw new RuntimeException("Could not find " + HMAC_SHA1_ALGORITHM + " algorithm", nsae)
      }
    }
    try {
      mac.init(signingKey)
    }
    catch {
      case ike: InvalidKeyException => {
        throw new RuntimeException("Could not initialize the MAC algorithm", ike)
      }
    }
    return new BASE64Encoder().encode(mac.doFinal(canonicalString.getBytes))
  }

  private final val HMAC_SHA1_ALGORITHM: String = "HmacSHA1"
  private final val INPUT_DATE_LENGTH_1: Int = 19
  private final val INPUT_DATE_LENGTH_2: Int = 20
  private final val INPUT_DATE_LENGTH_3: Int = 23
  private final val INPUT_DATE_LENGTH_4: Int = 24
  private final val DATE_FORMAT_SOAP_1: String = "yyyy-MM-dd'T'HH:mm:ss"
  private final val DATE_FORMAT_SOAP_2: String = "yyyy-MM-dd'T'HH:mm:ss'Z'"
  private final val DATE_FORMAT_SOAP_3: String = "yyyy-MM-dd'T'HH:mm:ss.SSS"
  private final val DATE_FORMAT_SOAP_4: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
  private final val DATE_FORMAT_REST: String = "EEE, dd MMM yyyy HH:mm:ss Z"
  private final val REGEX_SIMPLE: String = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{3})?Z?"
}
package soap.connect.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

/**
 * <p>
 * Collection of methods for the user authenticating.
 * </p>
 * <p>
 * HeadURL: $HeadURL:
 * http://z-bld-02:8080/zxapp/zxapp_wsapi/trunk/common/src/main/java/com/zanox/webservices
 * /utils/SecurityUtils.java $<br />
 * Date: $Date: 2010-06-08 14:09:16 +0200 (Di, 08 Jun 2010) $<br />
 * Revision: $Revision: 33943 $<br />
 * </p>
 * 
 * @author $Author: ZANOX-COM\fit $
 * 
 */
public final class SecurityUtils {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    private static final int    INPUT_DATE_LENGTH_1 = 19;

    private static final int    INPUT_DATE_LENGTH_2 = 20;

    private static final int    INPUT_DATE_LENGTH_3 = 23;

    private static final int    INPUT_DATE_LENGTH_4 = 24;

    private static final String DATE_FORMAT_SOAP_1  = "yyyy-MM-dd'T'HH:mm:ss";                                  // "2007-12-11T13:16:00"

    // 19

    private static final String DATE_FORMAT_SOAP_2  = "yyyy-MM-dd'T'HH:mm:ss'Z'";                               // "2007-12-11T13:16:00Z"

    // 20

    private static final String DATE_FORMAT_SOAP_3  = "yyyy-MM-dd'T'HH:mm:ss.SSS";                              // "2007-12-11T13:16:00.000"

    // 23

    private static final String DATE_FORMAT_SOAP_4  = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";                           // "2007-12-11T13:16:00.000Z"

    // 24

    private static final String DATE_FORMAT_REST    = "EEE, dd MMM yyyy HH:mm:ss Z";                            // "Fri, 04 Jan 2008 13:19:53 GMT"

    // private static final String REGEX_COMPLEX =
    // "(20)\\d\\d(-)(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01])T(\\d\\d:\\d\\d:\\d\\d)";

    private static final String REGEX_SIMPLE        = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{3})?Z?";

    /**
     * private constructor in order that no instance from this class can instantiated.
     */
    private SecurityUtils() {
    }
    
    public static String parseSoapDateCalendarToString(Calendar now) {

        SimpleDateFormat sdfSoap = new SimpleDateFormat(DATE_FORMAT_SOAP_1);
        sdfSoap.setTimeZone(TimeZone.getTimeZone("GMT"));

        return sdfSoap.format(now.getTime());
    }
    
    public static String parseRestDateCalendarToString(Calendar now) {

        SimpleDateFormat sdfSoap = new SimpleDateFormat(DATE_FORMAT_REST, Locale.US);
        sdfSoap.setTimeZone(TimeZone.getTimeZone("GMT"));

        return sdfSoap.format(now.getTime());
    }

    /**
     * This method parses the soap dateString to a Calendar Object.
     * 
     * @param dateInput the input string
     * @return Calendar the parsed date
     * @throws AuthorizationException thrown on various authentication errors
     * @throws ParseException is thrown if an error by parsing the dateInput string occur
     */
    public static Calendar parseSoapDateStringToCalendar(String dateInput)
        throws ParseException {

        if (!Pattern.matches(REGEX_SIMPLE, dateInput)) {
            throw new RuntimeException(
                "The Timestamp is in a wrong format! Has to be like this 2007-12-11T13:16:00 (yyyy-MM-dd'T'HH:mm:ss).");
        }

        SimpleDateFormat sdfSoap = new SimpleDateFormat();
        sdfSoap.setTimeZone(TimeZone.getTimeZone("GMT"));

        switch (dateInput.length()) {
            case INPUT_DATE_LENGTH_1:
                sdfSoap.applyPattern(DATE_FORMAT_SOAP_1);
                break;
            case INPUT_DATE_LENGTH_2:
                sdfSoap.applyPattern(DATE_FORMAT_SOAP_2);
                break;
            case INPUT_DATE_LENGTH_3:
                sdfSoap.applyPattern(DATE_FORMAT_SOAP_3);
                break;
            case INPUT_DATE_LENGTH_4:
                sdfSoap.applyPattern(DATE_FORMAT_SOAP_4);
                break;
            default:
                return null;
        }

        return parseStringToCalendar(sdfSoap, dateInput);
    }

    /**
     * This method parses the soap dateString to a Calendar Object.
     * 
     * @param dateInput the input string
     * @return Calendar
     * @throws ParseException is thrown if an error by parsing the dateInput string occur
     */
    public static Calendar parseRestDateStringToCalendar(String dateInput)
        throws ParseException {

        SimpleDateFormat sdfRest = new SimpleDateFormat(DATE_FORMAT_REST, Locale.US);
        return parseStringToCalendar(sdfRest, dateInput);
    }

    /**
     * This method parses a given dateString to a Calendar Object.
     * 
     * @param dateInput the input string
     * @param sdf the specified simpleDateFormat
     * @return the parsed date
     * @throws ParseException is thrown if an error by parsing the dateInput string occur
     */
    static Calendar parseStringToCalendar(SimpleDateFormat sdf, String dateInput)
        throws ParseException {

        Calendar timeStamp = null;

        // no other interpretation of the date pattern are allowed
        sdf.setLenient(false);

        if (dateInput != null) {

            try {
                Date dat = sdf.parse(dateInput);
                timeStamp = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
                timeStamp.setTime(dat);
            } catch (ParseException pe) {
                System.err.println("Wrong dateFormat: " + pe.getMessage());
                throw pe;
            }
        }
        return timeStamp;
    }

    /**
     * Create the soap signature for the specified wsAuthentication, the serviceName, the methodName
     * and the given timeStamp. The soapSignature consists of the sharedKey encoded with the hMac
     * algorithm and a complex string of the serviceName, the invoked methodName and the timeStamp.
     * 
     * @param userContext the {@link UserContext} instance that contains the authentication data for
     *            the current user
     * @param nonce the nonce value submitted from the client
     * @param serviceName the name of the invoked service
     * @param methodName the invoked methodName
     * @param timeStampStr the timeStamp as a string
     * @return String the created secure SOAP signature
     */
    public static String createSoapSignature(String secretKey, String nonce,
        String serviceName, String methodName, String timeStampStr) {
    	System.out.println(serviceName.toLowerCase() + methodName.toLowerCase() + timeStampStr + nonce);
        return generateHmacKey(secretKey.getBytes(),
            serviceName.toLowerCase() + methodName.toLowerCase() + timeStampStr + nonce);
    }
    
    public static String createRestSignature(String secretKey, String nonce, String uriPath,
            String httpVerb, String timeStampStr) {
    	System.out.println(secretKey + "   " + httpVerb + uriPath
                .toLowerCase()
                + timeStampStr
                + nonce);
            return generateHmacKey(secretKey.getBytes(), httpVerb + uriPath
                + timeStampStr
                + nonce);
        }

    /**
     * The method, to create and build the signature.
     * 
     * @param sharedKeyBytes sharedKeyBytes
     * @param canonicalString canonicalString
     * @return the signature
     */
    private static String generateHmacKey(byte[] sharedKeyBytes, String canonicalString) {

        // Acquire an HMAC/SHA1 from the raw key bytes.
        SecretKeySpec signingKey = new SecretKeySpec(sharedKeyBytes, HMAC_SHA1_ALGORITHM);

        // Acquire the MAC instance and initialize with the signing key.
        Mac mac = null;

        try {

            mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
        } catch (NoSuchAlgorithmException nsae) {

            // should not happen
            throw new RuntimeException("Could not find " + HMAC_SHA1_ALGORITHM + " algorithm", nsae);
        }

        try {

            mac.init(signingKey);
        } catch (InvalidKeyException ike) {

            // also should not happen
            throw new RuntimeException("Could not initialize the MAC algorithm", ike);
        }

        // Compute the HMAC on the digest, then set it and return the complete encoded signature
        return new BASE64Encoder().encode(mac.doFinal(canonicalString.getBytes()));
    }
}

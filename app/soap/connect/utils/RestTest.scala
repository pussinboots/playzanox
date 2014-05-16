package soap.connect.utils

//import com.zanox.api.version._2011_03_01.ApiInterface
//import com.zanox.api.version._2011_03_01.ApiClient
//import com.zanox.api.lib.constants.ApiConst

object RestTest {
  val connectId = System.getenv("connectId")
  val secretKey = System.getenv("secretKey")
  val publicKey = System.getenv("publicKey")

  def main(args: Array[String]) {
//	  val api = new ApiClient(ApiConst.PROTOCOL_JSON).getService();
//	  api.setConnectId(connectId);
//	  api.setSecretKey(secretKey);
//	  
//	  val profile = api.getProfile();
//	  profile.getProfileItem().setMobile(null);
//	  api.updateProfile(profile.getProfileItem());
//	  println(profile.getProfileItem().getMobile())
    ConnectClient.getSession("33468654D4C838123868A5E47F399B61E9356754BE9AF2A4E88D337703733EC8")
  }
}
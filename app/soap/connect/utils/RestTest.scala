package soap.connect.utils

//import com.zanox.api.version._2011_03_01.ApiInterface
//import com.zanox.api.version._2011_03_01.ApiClient
//import com.zanox.api.lib.constants.ApiConst

object RestTest {
  def main(args: Array[String]) {
//	  val api = new ApiClient(ApiConst.PROTOCOL_JSON).getService();
//	  api.setConnectId("D1EDB234699A6C05CFE9");
//	  api.setSecretKey("f34E4F44c0cf45+A8596f2779c76b3/8c9aD324c");
//	  
//	  val profile = api.getProfile();
//	  profile.getProfileItem().setMobile(null);
//	  api.updateProfile(profile.getProfileItem());
//	  println(profile.getProfileItem().getMobile())
    ConnectClient.getSession("33468654D4C838123868A5E47F399B61E9356754BE9AF2A4E88D337703733EC8")
  }
}
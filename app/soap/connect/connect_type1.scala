// Generated by <a href="http://scalaxb.org/">scalaxb</a>.
package soap.connect


case class GetSession(authToken: String,
  publicKey: String,
  signature: String,
  nonce: String,
  timestamp: String)


case class GetSessionResponse(session: Option[soap.connect.SessionType] = None)


case class GetSessionForMarketplace(marketplaceSessionId: String,
  publisherId: Option[Int] = None,
  programId: Option[Int] = None,
  publicKey: String,
  signature: String,
  nonce: String,
  timestamp: String)


case class GetSessionForMarketplaceResponse(session: Option[soap.connect.SessionType] = None)


case class SessionType(connectId: String,
  sessionKey: Option[String] = None,
  secretKey: Option[String] = None,
  sessionExpires: Int,
  offlineToken: Option[String] = None)


case class OfflineSessionType(connectId: String,
  sessionKey: Option[String] = None,
  secretKey: Option[String] = None,
  connectSessionToken: Option[String] = None)


case class CreateConnectRequest(applicationId: String,
  connectId: Option[String] = None,
  loginId: Option[Int] = None,
  role: Option[soap.connect.RoleType] = None,
  publicKey: String,
  timestamp: String,
  nonce: String,
  signature: String)


case class CreateConnectResponse(connectId: String)

trait RoleType

object RoleType {
  def fromString(value: String, scope: scala.xml.NamespaceBinding): RoleType = value match {
    case "DEVELOPER" => DEVELOPER
    case "CUSTOMER" => CUSTOMER
    case "TESTER" => TESTER

  }
}

case object DEVELOPER extends RoleType { override def toString = "DEVELOPER" }
case object CUSTOMER extends RoleType { override def toString = "CUSTOMER" }
case object TESTER extends RoleType { override def toString = "TESTER" }


case class GetUiUrl(connectId: String,
  sessionKey: String,
  timestamp: String,
  nonce: String,
  signature: String)


case class GetUiUrlResponse(url: String)


case class SoapException(message: String,
  code: Int)


case class CloseSession(connectId: String,
  publicKey: String,
  timestamp: String,
  nonce: String,
  signature: String)


case class CloseSessionResponse()


case class PromoteSessionRequest(connectId: String,
  promoteType: soap.connect.PromoteTypeEnum,
  publicKey: String,
  timestamp: String,
  nonce: String,
  signature: String)


case class PromoteSessionResponse(wsSessionExpires: Int,
  uiSessionExpires: Int)

trait PromoteTypeEnum

object PromoteTypeEnum {
  def fromString(value: String, scope: scala.xml.NamespaceBinding): PromoteTypeEnum = value match {
    case "UISESSION" => UISESSION
    case "WSSESSION" => WSSESSION
    case "BOTH" => BOTH

  }
}

case object UISESSION extends PromoteTypeEnum { override def toString = "UISESSION" }
case object WSSESSION extends PromoteTypeEnum { override def toString = "WSSESSION" }
case object BOTH extends PromoteTypeEnum { override def toString = "BOTH" }


case class GetOfflineSession(offlineToken: String,
  publicKey: String,
  signature: String,
  nonce: String,
  timestamp: String)


case class GetOfflineSessionResponse(session: soap.connect.OfflineSessionType)

trait LoginStatusEnum

object LoginStatusEnum {
  def fromString(value: String, scope: scala.xml.NamespaceBinding): LoginStatusEnum = value match {
    case "enabled" => Enabled
    case "disabled" => Disabled
    case "pre-deleted" => Preu45deleted
    case "deleted" => Deleted

  }
}

case object Enabled extends LoginStatusEnum { override def toString = "enabled" }
case object Disabled extends LoginStatusEnum { override def toString = "disabled" }
case object Preu45deleted extends LoginStatusEnum { override def toString = "pre-deleted" }
case object Deleted extends LoginStatusEnum { override def toString = "deleted" }

trait LoginTypeEnum

object LoginTypeEnum {
  def fromString(value: String, scope: scala.xml.NamespaceBinding): LoginTypeEnum = value match {
    case "owner" => Owner
    case "admin" => Admin
    case "full_access" => Full_access
    case "viewer" => Viewer
    case "restricted_viewer" => Restricted_viewer

  }
}

case object Owner extends LoginTypeEnum { override def toString = "owner" }
case object Admin extends LoginTypeEnum { override def toString = "admin" }
case object Full_access extends LoginTypeEnum { override def toString = "full_access" }
case object Viewer extends LoginTypeEnum { override def toString = "viewer" }
case object Restricted_viewer extends LoginTypeEnum { override def toString = "restricted_viewer" }


case class UserLoginItem(programId: Option[Int] = None,
  firstName: String,
  lastName: String,
  language: Option[String] = None,
  currency: Option[String] = None,
  loginName: String,
  description: Option[String] = None,
  status: soap.connect.LoginStatusEnum,
  loginType: Option[soap.connect.LoginTypeEnum] = None,
  isMaster: Option[Boolean] = None,
  darwinUserId: Option[Int] = None,
  connectId: Option[String] = None)


case class UserLoginUpdateItem(firstName: Option[String] = None,
  lastName: Option[String] = None,
  language: Option[String] = None,
  currency: Option[String] = None,
  description: Option[String] = None,
  status: Option[soap.connect.LoginStatusEnum] = None,
  loginType: Option[soap.connect.LoginTypeEnum] = None)


case class CreateAnonymousUserLoginRequest(userLoginItem: soap.connect.UserLoginItem,
  connectId: String,
  timestamp: String,
  nonce: String,
  signature: String)


case class GetPermanentToken(authToken: Option[String] = None,
  connectId: Option[String] = None,
  publicKey: String,
  signature: String,
  nonce: String,
  timestamp: String)


case class GetPermanentTokenResponse(permanentToken: String)


case class CreateAnonymousUserLoginResponse(userLoginItem: soap.connect.UserLoginItem,
  permanentToken: String)


case class GetUserLoginRequest(connectId: String,
  signature: String,
  nonce: String,
  timestamp: String)


case class GetUserLoginResponse(userLoginItem: Option[soap.connect.UserLoginItem] = None)


case class UpdateAnonymousUserLoginRequest(userLoginUpdateItem: soap.connect.UserLoginUpdateItem,
  connectId: String,
  timestamp: String,
  nonce: String,
  signature: String)


case class UpdateAnonymousUserLoginResponse(userLoginItem: soap.connect.UserLoginItem)


case class DeleteAnonymousUserLoginRequest(connectId: String,
  timestamp: String,
  nonce: String,
  signature: String)


case class DeleteAnonymousUserLoginResponse(successful: Boolean)


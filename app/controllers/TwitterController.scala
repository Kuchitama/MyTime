package controllers

import play.api.mvc.{Call, RequestHeader, Action, Controller}
import play.api.libs.oauth._
import play.api.libs.ws.WS
import play.api.libs.oauth.ServiceInfo
import play.api.libs.oauth.OAuth
import play.api.libs.oauth.RequestToken
import play.api.libs.oauth.ConsumerKey
import scala.Some
import scala.concurrent.{Await, ExecutionContext}
import models.User
import scala.concurrent.duration.{Duration, MILLISECONDS}
import mvc.{NoAuthenticateAction, AppAction}
import scalikejdbc.DBSession
import org.apache.commons.lang3.time.DateUtils
import java.util.Date
import utils.AppConfig

/**
 * Created by k2 on 2014/01/13.
 */
object TwitterController extends Controller {
  private val conf = AppConfig()
  private val Key = {
    val consumerKey = conf.get("twitter.consumer.key")
    val consumerSecret = conf.get("twitter.consumer.secret")
    ConsumerKey(consumerKey, consumerSecret)
  }

  private val Twitter = OAuth(ServiceInfo(
    "https://api.twitter.com/oauth/request_token",
    "https://api.twitter.com/oauth/access_token",
    "https://api.twitter.com/oauth/authorize", Key),
    true)

  /** Twitterサーバとの通信タイムアウト時間 */
  private lazy val TwitterRequestTimeout: Duration = {
    val TimeoutMillis = conf.get("twitter.request.timeout", _.getMilliseconds)
    Duration(TimeoutMillis, MILLISECONDS)
  }

  private lazy val SessionTimeout: Int = conf.get("mytime.session.timeout", _.getMilliseconds).toInt

  def authenticate = NoAuthenticateAction { implicit request =>
      implicit session =>
    request.getQueryString("oauth_verifier").map { verifier =>
      val tokenPair = buildOAuthRequestToken(request).get
      // We got the verifier; now get the access token, store it and back to index
      Twitter.retrieveAccessToken(tokenPair, verifier) match {
        case Right(t) =>
          // We received the authorized tokens in the OAuth object - store it before we proceed
          registerUser(t) match {
            case Some(user) =>{
              val expireTime = DateUtils.addMilliseconds(new Date(), SessionTimeout)
              Redirect(routes.Application.index).withSession("user_id" -> s"${user.id}", "expire_at" -> s"${expireTime.getTime}")
            }
            case _ => throw new Exception(s"can't login by twitter")
          }
        case Left(e) => throw e
      }
    }.getOrElse {
      val call:Call = routes.TwitterController.authenticate
      Twitter.retrieveRequestToken(call.absoluteURL()) match {
        case Right(t) => {
          // We received the unauthorized tokens in the OAuth object - store it before we proceed
          Redirect(Twitter.redirectUrl(t.token)).withSession("token" -> t.token, "secret" -> t.secret)
        }
        case Left(e) => throw e
      }
    }
  }

  /**
   * Twitterのユーザ情報を元にユーザを登録する
   * 既に登録済みの場合は、登録されているユーザを取得する
   *
   * @param token
   * @return
   */
  private def registerUser(token: RequestToken)(implicit session: DBSession):Option[User] = {
    import ExecutionContext.Implicits.global
    val future = WS.url("https://api.twitter.com/1.1/account/verify_credentials.json")
      .sign(OAuthCalculator(Key, token))
      .get
      .map{result =>
      val screenName = (result.json \ "screen_name").as[String]
      val idStr = (result.json \ "id_str").as[String]

      User.findByIdentifier(idStr).orElse{
        Some(User.add(idStr, screenName))
      }
    }
    Await.result(future, TwitterRequestTimeout)
  }

  private def buildOAuthRequestToken(implicit request: RequestHeader): Option[RequestToken] = {
    for {
      token <- request.session.get("token")
      secret <- request.session.get("secret")
    } yield {
      RequestToken(token, secret)
    }
  }
}

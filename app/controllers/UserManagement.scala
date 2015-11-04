package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future
import models._

class UserManagement extends Controller {

  def loginView = Action { request => 
    request.session.get("USER_EMAIL").map { sessionVal =>
      Redirect(routes.UserArea.home)
    }.getOrElse {
      Ok(views.html.login(loginForm))
    }
  }

  def login = Action.async { implicit request =>
    // handling the login form
    Future(
      loginForm.bindFromRequest
      ).map(
        _.fold(
          errorForm => BadRequest(views.html.login(errorForm)),
          user => Redirect(routes.UserArea.home).withSession("USER_EMAIL" -> user.email)
        )
      )
  }

  def logout = Action {
    Redirect(routes.UserManagement.login).withNewSession
  }

  val loginForm = Form(
    mapping(
      "email" -> email
      )(User.apply)(User.unapply).verifying(
        "Invalid email", 
        User.auth(_)
      )
    )

}

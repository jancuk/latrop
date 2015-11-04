package controllers

import play.api._
import play.api.mvc._
import play.api.i18n._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future
import models._

class UserManagement extends Controller {

  def loginView = Action {
    Ok(views.html.login(loginForm))
  }
  
  def login = Action.async { implicit request =>
    // handling the login form does not take significant time
    // the use of the future construct is for learning purposes
    // it is invoked for handling form submission, which I believe is the most significant computation performed
    Future( 
      loginForm.bindFromRequest
    ).map(
      _.fold(
        errorForm => BadRequest(views.html.login(errorForm)),
        user => Redirect(routes.UserArea.home(user.email)).withSession("PLAY_SESSION" -> user.email)
      )
    )
  }
  
  def logout = Action {
    Redirect(routes.UserManagement.login).withNewSession
  }
  
  def registerView = Action {
    Ok(views.html.register(userRegisterForm))
  }
  
  def register = Action.async { implicit request =>

    // future is invoked for handling form submission and creating a user
    Future(
      userRegisterForm.bindFromRequest.fold(
        errors => BadRequest(views.html.register(errors)),
        user => {
          User.create(user)
          Redirect(routes.UserArea.home(user.email)).withSession("PLAY_SESSION" -> user.email)
        }
      )
    ).map(identity)
  }
  
  def unregister(email: String) = Action.async {
    Future(
      User.delete(email)
    ).map(_ =>
      Redirect(routes.UserManagement.login).withNewSession 
    )
  }
  
  val userRegisterForm = Form(
    mapping(
      "email" -> nonEmptyText
    )(User.apply)(User.unapply).verifying(
      "E-mail is already registered", 
      !User.exists(_)
    )
  )

  val loginForm = Form(
    mapping(
      "email" -> email
    )(User.apply)(User.unapply).verifying(
      "Invalid email", 
      User.auth(_)
    )
  )

}


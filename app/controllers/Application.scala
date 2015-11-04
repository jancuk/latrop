package controllers

import play.api._
import play.api.mvc._
import play.api.i18n._
import play.api.data.Form
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import play.api.libs.json.Json
import play.api.Play.current
import play.api.i18n.Messages.Implicits._
import models._

class Application extends Controller {

  def index = Action {
    Redirect(routes.UserManagement.loginView)
  }

}

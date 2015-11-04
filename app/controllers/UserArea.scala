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
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.concurrent.Future


import models._

class UserArea extends Controller {

  def home(email: String) = Action { implicit request =>
    request.session.get("PLAY_SESSION").map { sessionVal =>
      Ok(views.html.userarea(email))
    }.getOrElse {
      Unauthorized("Not connected")
    }
  }
}


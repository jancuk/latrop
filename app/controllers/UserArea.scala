package controllers

import play.api._
import play.api.mvc._

class UserArea extends Controller {

  def home(email: String) = Action { implicit request =>
    request.session.get("PLAY_SESSION").map { sessionVal =>
      Ok(views.html.userarea(email))
    }.getOrElse {
      Unauthorized("Not connected")
    }
  }
}


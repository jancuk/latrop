package controllers

import play.api._
import play.api.mvc._

class UserArea extends Controller {

  def home = Action { request =>
    request.session.get("USER_EMAIL").map { sessionVal =>
      Ok(views.html.userarea(sessionVal))
    }.getOrElse {
      Unauthorized("Not connected")
    }
  }
}


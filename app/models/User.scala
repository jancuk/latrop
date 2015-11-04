package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

case class User(email: String)

object User {

  def create(user: User) = DB.withConnection { implicit conn =>
    SQL("insert into user (email) values ({email})").on(
      "email" -> user.email
    ).executeInsert()
  }

  def delete(email: String) = DB.withConnection { implicit conn =>
    SQL("delete from user where email = {email}").on(
      "email" -> email
    ).executeUpdate()
  }

  def findAll() = DB.withConnection { implicit conn =>
    SQL("select * from user").list.size
  }

  def exists(user: User) = DB.withConnection { implicit conn =>
    SQL("select * from user where email = {email}").
    on(
      "email" -> user.email
    ).singleOpt.isDefined
  }

  def auth(user: User) = DB.withConnection { implicit conn =>
    SQL("select * from user where email = {email}").
    on(
      "email" -> user.email
    ).list.size == 1
  }

}

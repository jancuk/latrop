package models

import anorm._
import anorm.SqlParser._
import play.api.db.DB
import play.api.Play.current

case class User(email: String, pwd: String)

object User {

  def create(user: User) = DB.withConnection { implicit conn =>
    SQL("insert into user (email, pwd) values ({email}, {pwd})").on(
      "email" -> user.email,
      "pwd" -> user.pwd
    ).executeInsert()
  }
  
  def delete(email: String) = DB.withConnection { implicit conn =>
    SQL("delete from user where email = {email}").on(
      "email" -> email
    ).executeUpdate()
  }
  
  def exists(user: User) = DB.withConnection { implicit conn =>
    SQL("select * from user where email = {email}").
    on(
      "email" -> user.email
    ).singleOpt.isDefined
  }
  
  def auth(user: User) = DB.withConnection { implicit conn =>
    SQL("select * from user where email = {email} and pwd = {pwd}").
    on(
      "email" -> user.email,
      "pwd" -> user.pwd
    ).list.size == 1
  }
  
  //user is a parser that, given a JDBC ResultSet row with at least 
  //an email and a password column, is able to create a User value
  val userParser = {
    get[String]("email") ~
    get[String]("pwd") map {
      case email~pwd => User(email, pwd)
    }
  }
  
}

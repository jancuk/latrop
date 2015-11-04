import play.api._
import models.User

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }
}

object InitialData {

  def insert() = {
    if (User.findAll() <= 0) {
    Seq(
      User("user@example.com"),
      User("user1@example.com"),
      User("user2@example.com")
    ).foreach(User.create)
   }
  }

}

import play.api._
import models.User

object Global extends GlobalSettings {

  override def onStart(app: Application) {
    InitialData.insert()
  }
}

/**
 * Initialize test data
 */
object InitialData {

  def insert() = {
    Seq(
      User("user@example.com"),
      User("user1@example.com"),
      User("user2@example.com")
    ).foreach(User.create)
  }

}

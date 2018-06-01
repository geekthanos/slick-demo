package abc.controller


import java.sql.{Connection, DriverManager}

import com.fasterxml.jackson.core.JsonParseException
import org.scalatest.BeforeAndAfterAll
import org.scalatestplus.play._
import play.api.Configuration
import play.api.db.slick.DatabaseConfigProvider
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.json.Json
import play.api.mvc._
import play.api.test.Helpers._
import play.api.test._
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres

import scala.concurrent.Future

class ParticipantControllerSpec extends PlaySpec with Results with BeforeAndAfterAll {


  // starting Postgres// starting Postgres

  val postgres = new EmbeddedPostgres()
  // predefined data directory
  // final EmbeddedPostgres postgres = new EmbeddedPostgres(V9_6, "/path/to/predefined/data/directory");
  val url: String = postgres.start("localhost", 5433, "postgres", "postgres", "postgres")

  // connecting to a running Postgres and feeding up the database
  val conn: Connection = DriverManager.getConnection(url)

   override def beforeAll(): Unit ={
     conn.createStatement.execute("DROP TABLE IF EXISTS participant;")
     conn.createStatement.execute("create table participant (id text not null, events jsonb []);")
  }


  val fakeApp = new GuiceApplicationBuilder()
    .configure(Configuration("slick.dbs.default.profile"->"slick.jdbc.PostgresProfile$"))
   .configure(Configuration("slick.dbs.default.db.url" ->"jdbc:postgresql://127.0.0.1:5433/"))
    .configure(Configuration("slick.dbs.default.db.user"-> "postgres"))
    .configure(Configuration("slick.dbs.default.db.password"-> "postgres"))
    .build

  val dbConfig = fakeApp.injector.instanceOf(classOf[DatabaseConfigProvider])
  "Participant application" should {
    "should be able to save a valid user event" in {
      val controller = fakeApp.injector.instanceOf[com.abc.controller.ParticipantController]
      val request=FakeRequest(POST,"/v1/participant")
           .withJsonBody(Json.parse("""{ "field": "PF" }"""))
      val result: Future[Result] = controller.save().apply(request)
      val bodyText: String = contentAsString(result)
      bodyText mustBe "Saved participant!"
    }
    "it should throw an exception on an invalid event" in {
      val controller = fakeApp.injector.instanceOf[com.abc.controller.ParticipantController]
      val request=FakeRequest(POST,"/v1/participant")
        .withJsonBody(Json.parse("""{ "field": "PF/00.0.0 (abc.xyz abc os x.x.x)" }"""))
      a[JsonParseException] must be thrownBy {
      val result: Future[Result] = controller.save().apply(request)
        val bodyText: String = contentAsString(result)
      }
    }
  }

  override def afterAll()={
    conn.createStatement.execute("DROP TABLE IF EXISTS participant;")
    conn.close()
    postgres.stop()
  }
}
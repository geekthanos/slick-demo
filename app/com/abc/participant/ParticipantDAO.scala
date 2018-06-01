package com.abc.participant


import com.abc.dao.ParticipantEntity
import com.abc.postgres.ExtendedPostgresProfile.api._
import javax.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.api.libs.json.JsValue
import slick.basic.DatabasePublisher
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}


class ParticipantDAO @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                              (implicit ec: ExecutionContext)
  extends HasDatabaseConfigProvider[JdbcProfile] {

  private val participants = TableQuery[ParticipantsTable]

  /**
    * Save a participant
    *
    * @param participant to be saved
    * @return saved  participant
    */
  def save(participant: ParticipantEntity): Future[ParticipantEntity] =
    db.run(participants returning participants += participant)

  /**
    * Definition of the  participants table
    *
    * @param tag marks a specific row represented by a table instance.
    */
  private class ParticipantsTable(tag: Tag) extends Table[ParticipantEntity](tag, "participant") {

    /**
      * This is the tables default "projection".
      *
      * It defines how the columns are converted to and from the Participant object.
      */
    def * = (id, events) <>
      (ParticipantEntity.tupled, ParticipantEntity.unapply)

    def id = column[String]("id", O.PrimaryKey)

    def events = column[List[JsValue]]("events")

  }

}
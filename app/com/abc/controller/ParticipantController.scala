package com.abc.controller

import com.abc.dao.ParticipantEntity
import com.abc.participant.ParticipantDAO
import com.google.inject.Inject
import play.api.db.slick.DatabaseConfigProvider
import play.api.libs.json.JsValue
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

class ParticipantController @Inject()
(dbConfig: DatabaseConfigProvider,
 cc: ControllerComponents
 )
  extends AbstractController(cc){

  val participantDAO = new ParticipantDAO(dbConfig)

  def save: Action[AnyContent] = Action.async { request: Request[AnyContent] =>
    val jsonBody: Option[JsValue] = request.body.asJson
    val participantEntity = new ParticipantEntity(jsonBody.get)

        participantDAO
          .save(participantEntity)
                  .map(_=>Ok("Saved participant!"))
  }
}
package com.abc.dao

import java.util.UUID.randomUUID

import org.apache.commons.lang3.builder.{ReflectionToStringBuilder, ToStringStyle}
import play.api.libs.json.JsValue

case class ParticipantEntity(
                              id: String,
                              events: List[JsValue]
                            ) {

  def this(event: JsValue){
    this(randomUUID.toString,List(event))
  }

  /**
    * When toString is called it returns the contents of the object.
    *
    * @return the contents of the object.
    */
  override def toString: String = ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE, true, true)

}


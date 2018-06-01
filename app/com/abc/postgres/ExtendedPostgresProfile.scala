package com.abc.postgres

/**
  * Slick extensions for PostgreSQL, to support a series of pg data types and related operators/functions.
  */

import com.github.tminglei.slickpg._
import play.api.libs.json.{JsValue, Json}
import slick.basic.Capability
import slick.jdbc.JdbcCapabilities


trait ExtendedPostgresProfile extends ExPostgresProfile
  with PgArraySupport
  with PgPlayJsonSupport {
  def pgjson = "jsonb" // jsonb support is in postgres 9.4.0 onward; for 9.3.x use "json"

  // Add back `capabilities.insertOrUpdate` to enable native `upsert` support; for postgres 9.5+
  override protected def computeCapabilities: Set[Capability] =
  super.computeCapabilities + JdbcCapabilities.insertOrUpdate

  override val api = MyAPI

  object MyAPI extends API with ArrayImplicits
    with JsonImplicits {
    implicit val strListTypeMapper = new SimpleArrayJdbcType[String]("text").to(_.toList)
    implicit val playJsonArrayTypeMapper =
      new AdvancedArrayJdbcType[JsValue](pgjson,
        (s) => utils.SimpleArrayUtils.fromString[JsValue](Json.parse(_))(s).orNull,
        (v) => utils.SimpleArrayUtils.mkString[JsValue](_.toString())(v)
      ).to(_.toList)
     }
}

object ExtendedPostgresProfile  extends ExtendedPostgresProfile
#slick-pg-demo
This is a sample application to replicate an issue with JSON parsing in slick-pg
## Sample error:
```
Unrecognized token 'PF': was expecting ('true', 'false' or 'null')
 at [Source: {"field": PF/00.0.0 (abc.xyz abc os x.x.x)}; line: 1, column: 13]
com.fasterxml.jackson.core.JsonParseException: Unrecognized token 'PF': was expecting ('true', 'false' or 'null')
 at [Source: {"field": PF/00.0.0 (abc.xyz abc os x.x.x)}; line: 1, column: 13]
	at com.fasterxml.jackson.core.JsonParser._constructError(JsonParser.java:1702)
	at com.fasterxml.jackson.core.base.ParserMinimalBase._reportError(ParserMinimalBase.java:558)
	at com.fasterxml.jackson.core.json.ReaderBasedJsonParser._reportInvalidToken(ReaderBasedJsonParser.java:2839)
	at com.fasterxml.jackson.core.json.ReaderBasedJsonParser._handleOddValue(ReaderBasedJsonParser.java:1903)
	at com.fasterxml.jackson.core.json.ReaderBasedJsonParser.nextToken(ReaderBasedJsonParser.java:749)
	at play.api.libs.json.jackson.JsValueDeserializer.deserialize(JacksonJson.scala:179)
	at play.api.libs.json.jackson.JsValueDeserializer.deserialize(JacksonJson.scala:128)
	at play.api.libs.json.jackson.JsValueDeserializer.deserialize(JacksonJson.scala:123)
	at com.fasterxml.jackson.databind.ObjectMapper._readValue(ObjectMapper.java:3786)
	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:2115)
	at play.api.libs.json.jackson.JacksonJson$.parseJsValue(JacksonJson.scala:235)
	at play.api.libs.json.StaticBinding$.parseJsValue(StaticBinding.scala:16)
	at play.api.libs.json.Json$.parse(Json.scala:171)
	at com.abc.postgres.ExtendedPostgresProfile$MyAPI$.$anonfun$playJsonArrayTypeMapper$2(ExtendedPostgresProfile.scala:29)
	at com.github.tminglei.slickpg.utils.SimpleArrayUtils$.$anonfun$fromString$1(SimpleArrayUtils.scala:17)
	at scala.collection.TraversableLike.$anonfun$map$1(TraversableLike.scala:234)
	at scala.collection.immutable.List.foreach(List.scala:389)
	at scala.collection.TraversableLike.map(TraversableLike.scala:234)
	at scala.collection.TraversableLike.map$(TraversableLike.scala:227)
	at scala.collection.immutable.List.map(List.scala:295)
	at com.github.tminglei.slickpg.utils.SimpleArrayUtils$.fromString(SimpleArrayUtils.scala:15)
	at com.abc.postgres.ExtendedPostgresProfile$MyAPI$.$anonfun$playJsonArrayTypeMapper$1(ExtendedPostgresProfile.scala:29)
	at com.github.tminglei.slickpg.array.PgArrayJdbcTypes$AdvancedArrayJdbcType.getValue(PgArrayJdbcTypes.scala:79)
	at com.github.tminglei.slickpg.array.PgArrayJdbcTypes$AdvancedArrayJdbcType.getValue(PgArrayJdbcTypes.scala:66)
	at com.github.tminglei.slickpg.array.PgArrayJdbcTypes$WrappedConvArrayJdbcType.getValue(PgArrayJdbcTypes.scala:105)
	at com.github.tminglei.slickpg.array.PgArrayJdbcTypes$WrappedConvArrayJdbcType.getValue(PgArrayJdbcTypes.scala:98)
	at slick.jdbc.SpecializedJdbcResultConverter$$anon$1.read(SpecializedJdbcResultConverters.scala:26)
	at slick.jdbc.SpecializedJdbcResultConverter$$anon$1.read(SpecializedJdbcResultConverters.scala:24)
	at slick.relational.ProductResultConverter.read(ResultConverter.scala:53)
	at slick.relational.ProductResultConverter.read(ResultConverter.scala:43)
	at slick.relational.TypeMappingResultConverter.read(ResultConverter.scala:133)
	at slick.jdbc.JdbcActionComponent$ReturningInsertActionComposerImpl.$anonfun$buildKeysResult$2(JdbcActionComponent.scala:642)
	at slick.jdbc.ResultSetInvoker$$anon$4.extractValue(ResultSetInvoker.scala:37)
	at slick.jdbc.ResultSetInvoker$$anon$2.extractValue(ResultSetInvoker.scala:26)
	at slick.jdbc.PositionedResultIterator.fetchNext(PositionedResult.scala:176)
	at slick.util.ReadAheadIterator.update(ReadAheadIterator.scala:28)
	at slick.util.ReadAheadIterator.hasNext(ReadAheadIterator.scala:34)
	at slick.util.ReadAheadIterator.hasNext$(ReadAheadIterator.scala:33)
	at slick.jdbc.PositionedResultIterator.hasNext(PositionedResult.scala:167)
	at slick.jdbc.Invoker.first(Invoker.scala:32)
	at slick.jdbc.Invoker.first$(Invoker.scala:29)
	at slick.jdbc.ResultSetInvoker.first(ResultSetInvoker.scala:14)
	at slick.jdbc.JdbcActionComponent$ReturningInsertActionComposerImpl.retOne(JdbcActionComponent.scala:652)
	at slick.jdbc.JdbcActionComponent$InsertActionComposerImpl$SingleInsertAction.$anonfun$run$11(JdbcActionComponent.scala:511)
	at slick.jdbc.JdbcBackend$SessionDef.withPreparedInsertStatement(JdbcBackend.scala:393)
	at slick.jdbc.JdbcBackend$SessionDef.withPreparedInsertStatement$(JdbcBackend.scala:390)
	at slick.jdbc.JdbcBackend$BaseSession.withPreparedInsertStatement(JdbcBackend.scala:448)
	at slick.jdbc.JdbcActionComponent$ReturningInsertActionComposerImpl.preparedInsert(JdbcActionComponent.scala:650)
	at slick.jdbc.JdbcActionComponent$InsertActionComposerImpl$SingleInsertAction.run(JdbcActionComponent.scala:507)
	at slick.jdbc.JdbcActionComponent$SimpleJdbcProfileAction.run(JdbcActionComponent.scala:30)
	at slick.jdbc.JdbcActionComponent$SimpleJdbcProfileAction.run(JdbcActionComponent.scala:27)
	at slick.basic.BasicBackend$DatabaseDef$$anon$2.liftedTree1$1(BasicBackend.scala:275)
	at slick.basic.BasicBackend$DatabaseDef$$anon$2.run(BasicBackend.scala:275)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
```
## Possible Issue:
 val CHUNK  = """[^}){(\[\]\\,"]+""".r in the Tokenizer is not picking up JSONs properly
 
## How to run the application?
 
 Option 1:
   * ./activator "~run 9000"
   
   * Validate the JSON https://jsonlint.com/
    {"field": "PF/00.0.0 (abc.xyz abc os x.x.x)"}
   * Run the below curl command:
     ```
     curl --header "Content-Type: application/json" \
           --request POST \
           --data '{ "field": "PF/00.0.0 (abc.xyz abc os x.x.x)" }' \
           http://localhost:9000/v1/participant
   * Check the server logs for error
   
   Option 2:
   
   * Run the ParticipantControllerSpec in test folder under package abc.controller
   * It contains an embedded postgres. So no other configuration is needed.
     
     
     
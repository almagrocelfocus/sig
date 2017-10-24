--------------------------------------------------------
--  DDL for Table LOG
--------------------------------------------------------

 CREATE TABLE "LOG" 
   (	
	"ID" VARCHAR2(250 BYTE), 
	"REQUEST_ID" VARCHAR2(250 BYTE),	
	"CORRELATION_ID" VARCHAR2(250 BYTE),  
	"DOMAIN" VARCHAR2(250 BYTE), 
	"CATEGORY" VARCHAR2(250 BYTE), 
	"TARGET" VARCHAR2(250 BYTE), 	
	"SERVICE" VARCHAR2(250 BYTE), 	
	"OPERATION" VARCHAR2(250 BYTE), 
	"VERSION" VARCHAR2(250 BYTE), 	
	"SOURCE" VARCHAR2(250 BYTE), 
	"TARGET_ENDPOINT" VARCHAR2(250 BYTE), 
	"LOG_LEVEL" VARCHAR2(20 BYTE), 
	"TASK" VARCHAR2(250 BYTE), 
	"USERNAME" VARCHAR2(250 BYTE), 
	"TIMESTAMP" TIMESTAMP (6), 
	"CREATION_DATE" TIMESTAMP (6), 
	"PAYLOAD" CLOB, 
	"STATUS_CODE" VARCHAR2(250 BYTE), 
	"STATUS_MESSAGE" VARCHAR2(250 BYTE), 
	"ENGINE" VARCHAR2(250 BYTE)
	);
--------------------------------------------------------
--  DDL for Index SYS_C0016650
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0016650" ON "LOG" ("ID");
  
--------------------------------------------------------
--  DDL for Index IDX_L_TIMESTAMP
--------------------------------------------------------

  CREATE INDEX "IDX_L_TIMESTAMP" ON "LOG" ("TIMESTAMP");
  
--------------------------------------------------------
--  DDL for Index IDX_L_MESSAGE_ID
--------------------------------------------------------

  CREATE INDEX "IDX_L_MESSAGE_ID" ON "LOG" ("REQUEST_ID") ;
--------------------------------------------------------
--  Constraints for Table LOG
--------------------------------------------------------

  ALTER TABLE "LOG" ADD PRIMARY KEY ("ID");

 --------------------------------------------------------
--  DDL for Table LOG_KEYS
--------------------------------------------------------

  CREATE TABLE "LOG_KEYS" 
   (	"ID" VARCHAR2(250 BYTE), 
	"ID_LOG" VARCHAR2(250 BYTE), 
	"KEY_NAME" VARCHAR2(250 BYTE), 
	"KEY_VALUE" VARCHAR2(2000 BYTE)
   )  ;
--------------------------------------------------------
--  DDL for Index SYS_C0016649
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0016649" ON "LOG_KEYS" ("ID");
  
--------------------------------------------------------
--  DDL for Index IDX_LK_KEY
--------------------------------------------------------

  CREATE INDEX "IDX_LK_KEY" ON "LOG_KEYS" ("KEY_NAME", "KEY_VALUE") ;
  
--------------------------------------------------------
--  DDL for Index IDX_LK_FK
--------------------------------------------------------

  CREATE INDEX "IDX_LK_FK" ON "LOG_KEYS" ("ID_LOG");
  
--------------------------------------------------------
--  Constraints for Table LOG_KEYS
--------------------------------------------------------

  ALTER TABLE "LOG_KEYS" ADD PRIMARY KEY ("ID");
  
--------------------------------------------------------
--  Ref Constraints for Table LOG_KEYS
--------------------------------------------------------

  ALTER TABLE "LOG_KEYS" ADD CONSTRAINT "LOG_KEYS_LOG_FK1" FOREIGN KEY ("ID_LOG")
	  REFERENCES "LOG" ("ID") ENABLE;

 --------------------------------------------------------
--  DDL for Table SOAF_ASYNC_EVENTS
--------------------------------------------------------

  CREATE TABLE "SOAF_ASYNC_EVENTS" 
   (	"ID" NUMBER, 
	"REQUEST_ID" VARCHAR2(250 BYTE), 
	"CORRELATION_ID" VARCHAR2(250 BYTE), 
	"EXTERNAL_CORRELATION_ID" VARCHAR2(250 BYTE), 
	"PAYLOAD_REQUEST" CLOB, 
	"PAYLOAD_RESPONSE" CLOB, 
	"TARGET_SERVICE" VARCHAR2(250 BYTE), 
	"CREATE_TIME" TIMESTAMP (6), 
	"ASYNC_RESPONSE_TIME" TIMESTAMP (6)
   )  ;
--------------------------------------------------------
--  DDL for Index SYS_C0016651
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C0016651" ON "SOAF_ASYNC_EVENTS" ("ID") ;
  
--------------------------------------------------------
--  Constraints for Table SOAF_ASYNC_EVENTS
--------------------------------------------------------

  ALTER TABLE "SOAF_ASYNC_EVENTS" ADD PRIMARY KEY ("ID");

  --------------------------------------------------------
--  DDL for Table SOAF_BACKEND_SIMULATOR
--------------------------------------------------------

  CREATE TABLE "SOAF_BACKEND_SIMULATOR" 
   (	"ID" NUMBER, 
	"NS_OPERATION" VARCHAR2(250 BYTE), 
	"XPATH_VALIDATION" VARCHAR2(500 BYTE), 
	"SERVICE" VARCHAR2(250 BYTE), 
	"RESPONSE_BODY" CLOB, 
	"RESPONSE_HEADER" CLOB
   ) ;
--------------------------------------------------------
--  DDL for Index SOAF_BACKEND_SIMULATOR_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SOAF_BACKEND_SIMULATOR_PK" ON "SOAF_BACKEND_SIMULATOR" ("ID") ;
  
--------------------------------------------------------
--  Constraints for Table SOAF_BACKEND_SIMULATOR
--------------------------------------------------------

  ALTER TABLE "SOAF_BACKEND_SIMULATOR" ADD CONSTRAINT "SOAF_BACKEND_SIMULATOR_PK" PRIMARY KEY ("ID");
  ALTER TABLE "SOAF_BACKEND_SIMULATOR" MODIFY ("NS_OPERATION" NOT NULL ENABLE);
  ALTER TABLE "SOAF_BACKEND_SIMULATOR" MODIFY ("ID" NOT NULL ENABLE);

  --------------------------------------------------------
--  DDL for Table SOAF_REPUBLISHER
--------------------------------------------------------

  CREATE TABLE "SOAF_REPUBLISHER" 
   (	"ID" NUMBER, 
	"MESSAGE_ID" VARCHAR2(250 BYTE), 
	"REQUEST_ID" VARCHAR2(250 BYTE), 
	"CORRELATION_ID" VARCHAR2(250 BYTE), 
	"ERROR_CODE" VARCHAR2(250 BYTE), 
	"ERROR_MESSAGE" VARCHAR2(4000 BYTE), 
	"STATUS" VARCHAR2(20 BYTE), 
	"REQUEST" CLOB, 
	"REPUB_INFO" CLOB, 
	"DOMAIN" VARCHAR2(250 BYTE), 
	"CATEGORY" VARCHAR2(250 BYTE), 
	"TARGET" VARCHAR2(250 BYTE), 
	"SERVICE" VARCHAR2(250 BYTE), 
	"VERSION" VARCHAR2(10 BYTE), 
	"CREATED_DATE" TIMESTAMP (6), 
	"CREATED_BY" VARCHAR2(250 BYTE), 
	"LAST_UPDATED_DATE" TIMESTAMP (6), 
	"LAST_UPDATED_BY" VARCHAR2(250 BYTE), 
	"NUMBER_OF_RETRIES" NUMBER
   )  ;
   
--------------------------------------------------------
--  DDL for Index SOAF_REPUBLISHER_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SOAF_REPUBLISHER_PK" ON "SOAF_REPUBLISHER" ("ID");
  
--------------------------------------------------------
--  Constraints for Table SOAF_REPUBLISHER
--------------------------------------------------------

  ALTER TABLE "SOAF_REPUBLISHER" ADD CONSTRAINT "SOAF_REPUBLISHER_PK" PRIMARY KEY ("ID");
  ALTER TABLE "SOAF_REPUBLISHER" MODIFY ("LAST_UPDATED_BY" NOT NULL ENABLE);
  ALTER TABLE "SOAF_REPUBLISHER" MODIFY ("LAST_UPDATED_DATE" NOT NULL ENABLE);
  ALTER TABLE "SOAF_REPUBLISHER" MODIFY ("CREATED_BY" NOT NULL ENABLE);
  ALTER TABLE "SOAF_REPUBLISHER" MODIFY ("CREATED_DATE" NOT NULL ENABLE);
  ALTER TABLE "SOAF_REPUBLISHER" MODIFY ("VERSION" NOT NULL ENABLE);
  ALTER TABLE "SOAF_REPUBLISHER" MODIFY ("SERVICE" NOT NULL ENABLE);
  ALTER TABLE "SOAF_REPUBLISHER" MODIFY ("TARGET" NOT NULL ENABLE);
  ALTER TABLE "SOAF_REPUBLISHER" MODIFY ("CATEGORY" NOT NULL ENABLE);
  ALTER TABLE "SOAF_REPUBLISHER" MODIFY ("DOMAIN" NOT NULL ENABLE);
  ALTER TABLE "SOAF_REPUBLISHER" MODIFY ("REQUEST" NOT NULL ENABLE);
  ALTER TABLE "SOAF_REPUBLISHER" MODIFY ("MESSAGE_ID" NOT NULL ENABLE);
  
  
  
  --------------------------------------------------------
--  DDL for Table SOAF_REPUBLISHER_CONFIGURATOR
--------------------------------------------------------

  CREATE TABLE "SOAF_REPUBLISHER_CONFIGURATOR" 
   (	"ID" NUMBER, 
	"DOMAIN" VARCHAR2(250 BYTE), 
	"CATEGORY" VARCHAR2(250 BYTE), 
	"TARGET" VARCHAR2(250 BYTE), 
	"SERVICE" VARCHAR2(250 BYTE), 
	"VERSION" VARCHAR2(10 BYTE), 
	"MAX_NUMBER_OF_RETRIES" NUMBER, 
	"ERROR_CODE" VARCHAR2(250 BYTE), 
	"SECONDS_BETWEEN_RETRIES" NUMBER
   ) ;
--------------------------------------------------------
--  DDL for Index SOAF_REPUBLISHER_CONF_OP_UK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SOAF_REPUBLISHER_CONF_OP_UK" ON "SOAF_REPUBLISHER_CONFIGURATOR" ("DOMAIN", "CATEGORY", "TARGET", "SERVICE", "VERSION", "ERROR_CODE");;
--------------------------------------------------------
--  DDL for Index SOAF_REPUBLISHER_CONF_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SOAF_REPUBLISHER_CONF_PK" ON "SOAF_REPUBLISHER_CONFIGURATOR" ("ID") ;
--------------------------------------------------------
--  Constraints for Table SOAF_REPUBLISHER_CONFIGURATOR
--------------------------------------------------------

  ALTER TABLE "SOAF_REPUBLISHER_CONFIGURATOR" ADD CONSTRAINT "SOAF_REPUBLISHER_CONF_OP_UK" UNIQUE ("DOMAIN", "CATEGORY", "TARGET", "SERVICE", "VERSION", "ERROR_CODE");
  ALTER TABLE "SOAF_REPUBLISHER_CONFIGURATOR" ADD CONSTRAINT "SOAF_REPUBLISHER_CONF_PK" PRIMARY KEY ("ID");
  ALTER TABLE "SOAF_REPUBLISHER_CONFIGURATOR" MODIFY ("VERSION" NOT NULL ENABLE);
  ALTER TABLE "SOAF_REPUBLISHER_CONFIGURATOR" MODIFY ("SERVICE" NOT NULL ENABLE);
  ALTER TABLE "SOAF_REPUBLISHER_CONFIGURATOR" MODIFY ("TARGET" NOT NULL ENABLE);
  ALTER TABLE "SOAF_REPUBLISHER_CONFIGURATOR" MODIFY ("CATEGORY" NOT NULL ENABLE);
  ALTER TABLE "SOAF_REPUBLISHER_CONFIGURATOR" MODIFY ("DOMAIN" NOT NULL ENABLE);

--------------------------------------------------------
--  DDL for Table SOAF_SERVICE_CONFIGURATOR
--------------------------------------------------------

  CREATE TABLE "SOAF_SERVICE_CONFIGURATOR" 
   (	"ID" NUMBER, 
	"DOMAIN" VARCHAR2(250 BYTE), 
	"CATEGORY" VARCHAR2(250 BYTE), 
	"TARGET" VARCHAR2(250 BYTE), 
	"SERVICE" VARCHAR2(250 BYTE), 
	"VERSION" VARCHAR2(250 BYTE), 
	"USERNAME" VARCHAR2(250 BYTE), 
	"PARAM_NAME" VARCHAR2(4000 BYTE), 
	"PARAM_VALUE" VARCHAR2(4000 BYTE)
   ) ;

  CREATE UNIQUE INDEX "SOAF_SERVICE_CONFIGURATOR_UK" ON "SOAF_SERVICE_CONFIGURATOR" ("DOMAIN", "CATEGORY", "TARGET", "SERVICE", "VERSION", "PARAM_NAME");
--------------------------------------------------------

  CREATE UNIQUE INDEX "SOAF_SERVICE_CONFIGURATOR_PK" ON "SOAF_SERVICE_CONFIGURATOR" ("ID") ;

  ALTER TABLE "SOAF_SERVICE_CONFIGURATOR" ADD CONSTRAINT "SOAF_SERVICE_CONFIGURATOR_UK" UNIQUE ("DOMAIN", "CATEGORY", "TARGET", "SERVICE", "VERSION", "PARAM_NAME");
  ALTER TABLE "SOAF_SERVICE_CONFIGURATOR" ADD CONSTRAINT "SOAF_SERVICE_CONFIGURATOR_PK" PRIMARY KEY ("ID");
  ALTER TABLE "SOAF_SERVICE_CONFIGURATOR" MODIFY ("PARAM_NAME" NOT NULL ENABLE);
  ALTER TABLE "SOAF_SERVICE_CONFIGURATOR" MODIFY ("USERNAME" NOT NULL ENABLE);
  ALTER TABLE "SOAF_SERVICE_CONFIGURATOR" MODIFY ("VERSION" NOT NULL ENABLE);
  ALTER TABLE "SOAF_SERVICE_CONFIGURATOR" MODIFY ("SERVICE" NOT NULL ENABLE);
  ALTER TABLE "SOAF_SERVICE_CONFIGURATOR" MODIFY ("TARGET" NOT NULL ENABLE);
  ALTER TABLE "SOAF_SERVICE_CONFIGURATOR" MODIFY ("CATEGORY" NOT NULL ENABLE);
  ALTER TABLE "SOAF_SERVICE_CONFIGURATOR" MODIFY ("DOMAIN" NOT NULL ENABLE);
  ALTER TABLE "SOAF_SERVICE_CONFIGURATOR" MODIFY ("ID" NOT NULL ENABLE);

  
  
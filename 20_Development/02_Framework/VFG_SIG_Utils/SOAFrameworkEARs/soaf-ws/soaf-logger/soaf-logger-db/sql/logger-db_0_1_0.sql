--------------------------------------------------------
--  DDL for Table LOG
--------------------------------------------------------
  CREATE TABLE "LOG" 
   (	"ID" VARCHAR2(250 BYTE) PRIMARY KEY, 
	"MESSAGE_ID" VARCHAR2(250 BYTE), 
	"SERVICE_NAME" VARCHAR2(250 BYTE), 
	"LOG_LEVEL" VARCHAR2(20 BYTE), 
	"TASK" VARCHAR2(250 BYTE), 
	"CREATED_BY" VARCHAR2(250 BYTE), 
	"TIMESTAMP" TIMESTAMP (6), 
	"PAYLOAD" CLOB, 
	"STATUS_CODE" VARCHAR2(250 BYTE), 
	"STATUS_MESSAGE" VARCHAR2(250 BYTE), 
	"ENGINE" VARCHAR2(250 BYTE)
	)  
/

--------------------------------------------------------
--  DDL for Table LOG_KEYS
--------------------------------------------------------

CREATE TABLE "LOG_KEYS" 
   (	"ID" VARCHAR2(250 BYTE) PRIMARY KEY, 
	"ID_LOG" VARCHAR2(250 BYTE), 
	"KEY_NAME" VARCHAR2(250 BYTE), 
	"KEY_VALUE" VARCHAR2(2000 BYTE), 
	 CONSTRAINT "LOG_KEYS_LOG_FK1" FOREIGN KEY ("ID_LOG") REFERENCES "LOG" ("ID")
   )
/
EXIT

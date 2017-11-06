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
	"REQUEST_ID" VARCHAR2(250 BYTE),	
	"CORRELATION_ID" VARCHAR2(250 BYTE),  
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

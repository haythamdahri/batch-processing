CREATE TABLE "DATABASE"."TBCONFIG"
(	"ID" NUMBER(19,0) NOT NULL ENABLE,
     "AVANZAMENTO" VARCHAR2(255 CHAR),
     "COMMAND" VARCHAR2(255 CHAR),
     "DESTINATIONPATH" VARCHAR2(255 CHAR),
     "EXECUTIONORDER" VARCHAR2(255 CHAR),
     "INSERTDATE" TIMESTAMP (6),
     "DESCJOB" VARCHAR2(255 CHAR),
     "IDJOB" VARCHAR2(255 CHAR),
     "JOBNAME" VARCHAR2(255 CHAR),
     "LASTUPDATE" TIMESTAMP (6),
     "IDOPERAZIONE" NUMBER(10,0),
     "SCHEDULATION" VARCHAR2(255 CHAR),
     "SOURCEPATH" VARCHAR2(255 CHAR),
     "IDSTEP" NUMBER(10,0),
     "TYPE" VARCHAR2(255 CHAR),
     "IDUSERINS" VARCHAR2(255 CHAR),
     "IDUSERUPD" VARCHAR2(255 CHAR),
     PRIMARY KEY ("ID")
         USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255
         STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
         PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
         TABLESPACE "DATA_SESAME"  ENABLE
);
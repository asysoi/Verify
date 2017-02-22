--------------------------------------------------------
--  DDL for Table CCI_CLIENT
--------------------------------------------------------

  CREATE TABLE "BELTPP"."CCI_CLIENT" 
   (	"ID" NUMBER, 
	"NAME" VARCHAR2(200 BYTE), 
	"CITY" VARCHAR2(200 BYTE), 
	"LINE" VARCHAR2(200 BYTE), 
	"CINDEX" VARCHAR2(200 BYTE), 
	"OFFICE" VARCHAR2(200 BYTE), 
	"BUILDING" VARCHAR2(200 BYTE), 
	"WORK_PHONE" VARCHAR2(50 BYTE), 
	"CELL_PHONE" VARCHAR2(50 BYTE), 
	"UNP" VARCHAR2(30 BYTE), 
	"OKPO" VARCHAR2(30 BYTE), 
	"BNAME" VARCHAR2(200 BYTE), 
	"BCITY" VARCHAR2(200 BYTE), 
	"BLINE" VARCHAR2(200 BYTE), 
	"BINDEX" VARCHAR2(20 BYTE), 
	"BOFFICE" VARCHAR2(20 BYTE), 
	"BBUILDING" VARCHAR2(20 BYTE), 
	"ACCOUNT" VARCHAR2(50 BYTE), 
	"BUNP" VARCHAR2(50 BYTE), 
	"EMAIL" VARCHAR2(80 BYTE), 
	"BEMAIL" VARCHAR2(20 BYTE), 
	"CODECOUNTRY" VARCHAR2(20 BYTE), 
	"BCODECOUNTRY" VARCHAR2(20 BYTE), 
	"ADDRESS" VARCHAR2(512 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table CCI_EMPLOYEE
--------------------------------------------------------

  CREATE TABLE "BELTPP"."CCI_EMPLOYEE" 
   (	"ID" NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE , 
	"NAME" VARCHAR2(120 BYTE), 
	"JOB" VARCHAR2(120 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table FS_BLANK
--------------------------------------------------------

  CREATE TABLE "BELTPP"."FS_BLANK" 
   (	"ID" NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE , 
	"ID_FSCERT" NUMBER, 
	"PAGE" NUMBER, 
	"BLANKNUMBER" VARCHAR2(20 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table FS_BLANK_DENORM
--------------------------------------------------------

  CREATE TABLE "BELTPP"."FS_BLANK_DENORM" 
   (	"ID_FSCERT" NUMBER, 
	"BLANKNUMBERS" VARCHAR2(1024 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table FS_BRANCH
--------------------------------------------------------

  CREATE TABLE "BELTPP"."FS_BRANCH" 
   (	"ID" NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE , 
	"NAME" VARCHAR2(2000 BYTE), 
	"CITY" VARCHAR2(200 BYTE), 
	"LINE" VARCHAR2(200 BYTE), 
	"CINDEX" VARCHAR2(200 BYTE), 
	"OFFICE" VARCHAR2(200 BYTE), 
	"BUILDING" VARCHAR2(200 BYTE), 
	"WORK_PHONE" VARCHAR2(50 BYTE), 
	"CELL_PHONE" VARCHAR2(50 BYTE), 
	"UNP" VARCHAR2(30 BYTE), 
	"OKPO" VARCHAR2(30 BYTE), 
	"BNAME" VARCHAR2(2000 BYTE), 
	"BCITY" VARCHAR2(200 BYTE), 
	"BLINE" VARCHAR2(200 BYTE), 
	"BINDEX" VARCHAR2(20 BYTE), 
	"BOFFICE" VARCHAR2(20 BYTE), 
	"BBUILDING" VARCHAR2(20 BYTE), 
	"ACCOUNT" VARCHAR2(50 BYTE), 
	"BUNP" VARCHAR2(50 BYTE), 
	"EMAIL" VARCHAR2(80 BYTE), 
	"BEMAIL" VARCHAR2(20 BYTE), 
	"CODECOUNTRY" VARCHAR2(20 BYTE), 
	"BCODECOUNTRY" VARCHAR2(20 BYTE), 
	"ADDRESS" VARCHAR2(512 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table FS_CERT
--------------------------------------------------------

  CREATE TABLE "BELTPP"."FS_CERT" 
   (	"ID" NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE , 
	"CERTNUMBER" VARCHAR2(20 BYTE), 
	"PARENTNUMBER" VARCHAR2(20 BYTE), 
	"DATEISSUE" DATE, 
	"DATEEXPIRY" DATE, 
	"CONFIRMATION" VARCHAR2(2000 BYTE), 
	"DECLARATION" VARCHAR2(2000 BYTE), 
	"ID_BRANCH" NUMBER, 
	"ID_EXPORTER" NUMBER, 
	"ID_PRODUCER" NUMBER, 
	"ID_EXPERT" NUMBER, 
	"ID_SIGNER" NUMBER, 
	"DATECERT" DATE, 
	"CODECOUNTRYTARGET" VARCHAR2(3 BYTE), 
	"OTD_ID" NUMBER, 
	"LISTSCOUNT" NUMBER
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table FS_PRODUCT
--------------------------------------------------------

  CREATE TABLE "BELTPP"."FS_PRODUCT" 
   (	"ID" NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE , 
	"ID_FSCERT" NUMBER, 
	"NUMERATOR" NUMBER, 
	"TOVAR" VARCHAR2(2000 BYTE)
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Table FS_PRODUCT_DENORM
--------------------------------------------------------

  CREATE TABLE "BELTPP"."FS_PRODUCT_DENORM" 
   (	"ID_FSCERT" NUMBER, 
	"TOVARS" CLOB
   ) SEGMENT CREATION IMMEDIATE 
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 
 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" 
 LOB ("TOVARS") STORE AS SECUREFILE (
  TABLESPACE "USERS" ENABLE STORAGE IN ROW CHUNK 8192
  NOCACHE LOGGING  NOCOMPRESS  KEEP_DUPLICATES 
  STORAGE(INITIAL 106496 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)) ;
--------------------------------------------------------
--  DDL for View FSCERTVIEWEXPORT
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "BELTPP"."FSCERTVIEWEXPORT" ("ID", "CERTNUMBER", "PARENTNUMBER", "DATEISSUE", "DATEEXPIRY", "CONFIRMATION", "DECLARATION", "ID_BRANCH", "ID_EXPORTER", "ID_PRODUCER", "ID_EXPERT", "ID_SIGNER", "DATECERT", "CODECOUNTRYTARGET", "OTD_ID", "LISTSCOUNT", "BRANCHNAME", "BRANCHADDRESS", "EXPORTERNAME", "EXPORTERADDRESS", "PRODUCERNAME", "PRODUCERADDRESS", "EXPERTNAME", "SIGNERNAME", "JOB", "COUNTRYNAME", "TOVARS", "BLANKNUMBERS") AS 
  SELECT a."ID",a."CERTNUMBER",a."PARENTNUMBER",a."DATEISSUE",a."DATEEXPIRY",a."CONFIRMATION",a."DECLARATION",a."ID_BRANCH",a."ID_EXPORTER",a."ID_PRODUCER",a."ID_EXPERT",a."ID_SIGNER",a."DATECERT",a."CODECOUNTRYTARGET",a."OTD_ID",a."LISTSCOUNT",a."BRANCHNAME",a."BRANCHADDRESS",a."EXPORTERNAME",a."EXPORTERADDRESS",a."PRODUCERNAME",a."PRODUCERADDRESS",a."EXPERTNAME",a."SIGNERNAME",a."JOB",a."COUNTRYNAME", b.tovars tovars, c.blanknumbers blanknumbers from FSCERTVIEW a
left join fs_product_denorm b on a.id = b.id_fscert
left join fs_blank_denorm c on a.id = c.id_fscert;
--------------------------------------------------------
--  DDL for View FSCERTVIEW
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "BELTPP"."FSCERTVIEW" ("ID", "CERTNUMBER", "PARENTNUMBER", "DATEISSUE", "DATEEXPIRY", "CONFIRMATION", "DECLARATION", "ID_BRANCH", "ID_EXPORTER", "ID_PRODUCER", "ID_EXPERT", "ID_SIGNER", "DATECERT", "CODECOUNTRYTARGET", "OTD_ID", "LISTSCOUNT", "BRANCHNAME", "BRANCHADDRESS", "EXPORTERNAME", "EXPORTERADDRESS", "PRODUCERNAME", "PRODUCERADDRESS", "EXPERTNAME", "SIGNERNAME", "JOB", "COUNTRYNAME") AS 
  select a."ID",a."CERTNUMBER",a."PARENTNUMBER",a."DATEISSUE",a."DATEEXPIRY",
a."CONFIRMATION",a."DECLARATION",a."ID_BRANCH",a."ID_EXPORTER",
a."ID_PRODUCER",a."ID_EXPERT",a."ID_SIGNER",a."DATECERT",a."CODECOUNTRYTARGET",
a."OTD_ID",a."LISTSCOUNT", 
b.name branchname, b.address branchaddress,  
c.name exportername, c.address exporteraddress,  
d.name producername, d.address produceraddress,  
e.name expertname, f.name signername, f.job job, g.name countryname
from fs_cert a
left join fs_branch b on a.ID_BRANCH = b.id
left join cci_client c on a.ID_EXPORTER = c.id
left join cci_client d on a.ID_Producer = d.id
left join cci_employee e on a.ID_EXPERT = e.id
left join cci_employee f on a.ID_SIGNER = f.id
left join c_country g on a.codecountrytarget = g.code;
--------------------------------------------------------
--  DDL for Index CCI_CLIENT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "BELTPP"."CCI_CLIENT_PK" ON "BELTPP"."CCI_CLIENT" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index CCI_EMPLOYEE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "BELTPP"."CCI_EMPLOYEE_PK" ON "BELTPP"."CCI_EMPLOYEE" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index FS_BLANK_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "BELTPP"."FS_BLANK_PK" ON "BELTPP"."FS_BLANK" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index FS_BLANK_DENORM_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "BELTPP"."FS_BLANK_DENORM_PK" ON "BELTPP"."FS_BLANK_DENORM" ("ID_FSCERT") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index FS_BRANCH_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "BELTPP"."FS_BRANCH_PK" ON "BELTPP"."FS_BRANCH" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index FS_BRANCH_BUILDING
--------------------------------------------------------

  CREATE INDEX "BELTPP"."FS_BRANCH_BUILDING" ON "BELTPP"."FS_BRANCH" ("BUILDING") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index FS_BRANCH_INDEX
--------------------------------------------------------

  CREATE INDEX "BELTPP"."FS_BRANCH_INDEX" ON "BELTPP"."FS_BRANCH" ("CINDEX") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index FS_BRANCH_OFFICE
--------------------------------------------------------

  CREATE INDEX "BELTPP"."FS_BRANCH_OFFICE" ON "BELTPP"."FS_BRANCH" ("OFFICE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index FS_BRANCH_LINE
--------------------------------------------------------

  CREATE INDEX "BELTPP"."FS_BRANCH_LINE" ON "BELTPP"."FS_BRANCH" ("LINE") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index FS_BRANCH_NAME
--------------------------------------------------------

  CREATE INDEX "BELTPP"."FS_BRANCH_NAME" ON "BELTPP"."FS_BRANCH" ("NAME") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index FS_CERT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "BELTPP"."FS_CERT_PK" ON "BELTPP"."FS_CERT" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index FS_CERT_NUMBER
--------------------------------------------------------

  CREATE UNIQUE INDEX "BELTPP"."FS_CERT_NUMBER" ON "BELTPP"."FS_CERT" ("CERTNUMBER") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index INDEX_TOVAR_CONTENT
--------------------------------------------------------

  CREATE INDEX "BELTPP"."INDEX_TOVAR_CONTENT" ON "BELTPP"."FS_PRODUCT" ("TOVAR") 
   INDEXTYPE IS "CTXSYS"."CONTEXT" ;
--------------------------------------------------------
--  DDL for Index FS_PRODUCT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "BELTPP"."FS_PRODUCT_PK" ON "BELTPP"."FS_PRODUCT" ("ID") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  DDL for Index FS_PRODUCT_DENORM_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "BELTPP"."FS_PRODUCT_DENORM_PK" ON "BELTPP"."FS_PRODUCT_DENORM" ("ID_FSCERT") 
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS" ;
--------------------------------------------------------
--  Constraints for Table CCI_CLIENT
--------------------------------------------------------

  ALTER TABLE "BELTPP"."CCI_CLIENT" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "BELTPP"."CCI_CLIENT" ADD CONSTRAINT "CCI_CLIENT_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table CCI_EMPLOYEE
--------------------------------------------------------

  ALTER TABLE "BELTPP"."CCI_EMPLOYEE" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "BELTPP"."CCI_EMPLOYEE" ADD CONSTRAINT "CCI_EMPLOYEE_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table FS_BLANK
--------------------------------------------------------

  ALTER TABLE "BELTPP"."FS_BLANK" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "BELTPP"."FS_BLANK" MODIFY ("PAGE" NOT NULL ENABLE);
  ALTER TABLE "BELTPP"."FS_BLANK" ADD CONSTRAINT "FS_BLANK_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table FS_BLANK_DENORM
--------------------------------------------------------

  ALTER TABLE "BELTPP"."FS_BLANK_DENORM" MODIFY ("ID_FSCERT" NOT NULL ENABLE);
  ALTER TABLE "BELTPP"."FS_BLANK_DENORM" ADD CONSTRAINT "FS_BLANK_DENORM_PK" PRIMARY KEY ("ID_FSCERT")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table FS_BRANCH
--------------------------------------------------------

  ALTER TABLE "BELTPP"."FS_BRANCH" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "BELTPP"."FS_BRANCH" ADD CONSTRAINT "FS_BRANCH_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table FS_CERT
--------------------------------------------------------

  ALTER TABLE "BELTPP"."FS_CERT" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "BELTPP"."FS_CERT" MODIFY ("CERTNUMBER" NOT NULL ENABLE);
  ALTER TABLE "BELTPP"."FS_CERT" ADD CONSTRAINT "FS_CERT_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table FS_PRODUCT
--------------------------------------------------------

  ALTER TABLE "BELTPP"."FS_PRODUCT" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "BELTPP"."FS_PRODUCT" ADD CONSTRAINT "FS_PRODUCT_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Constraints for Table FS_PRODUCT_DENORM
--------------------------------------------------------

  ALTER TABLE "BELTPP"."FS_PRODUCT_DENORM" MODIFY ("ID_FSCERT" NOT NULL ENABLE);
  ALTER TABLE "BELTPP"."FS_PRODUCT_DENORM" ADD CONSTRAINT "FS_PRODUCT_DENORM_PK" PRIMARY KEY ("ID_FSCERT")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1
  BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "USERS"  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table FS_BLANK
--------------------------------------------------------

  ALTER TABLE "BELTPP"."FS_BLANK" ADD CONSTRAINT "FS_BLANK_FK1" FOREIGN KEY ("ID_FSCERT")
	  REFERENCES "BELTPP"."FS_CERT" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table FS_CERT
--------------------------------------------------------

  ALTER TABLE "BELTPP"."FS_CERT" ADD CONSTRAINT "FS_CERT_FK_BRANCH" FOREIGN KEY ("ID_BRANCH")
	  REFERENCES "BELTPP"."FS_BRANCH" ("ID") ENABLE;
  ALTER TABLE "BELTPP"."FS_CERT" ADD CONSTRAINT "FS_CERT_FK_EXPERT" FOREIGN KEY ("ID_EXPERT")
	  REFERENCES "BELTPP"."CCI_EMPLOYEE" ("ID") ENABLE;
  ALTER TABLE "BELTPP"."FS_CERT" ADD CONSTRAINT "FS_CERT_FK_EXPORTER" FOREIGN KEY ("ID_EXPORTER")
	  REFERENCES "BELTPP"."CCI_CLIENT" ("ID") ENABLE;
  ALTER TABLE "BELTPP"."FS_CERT" ADD CONSTRAINT "FS_CERT_FK_PRODUCER" FOREIGN KEY ("ID_PRODUCER")
	  REFERENCES "BELTPP"."CCI_CLIENT" ("ID") ENABLE;
  ALTER TABLE "BELTPP"."FS_CERT" ADD CONSTRAINT "FS_CERT_FK_SINGER" FOREIGN KEY ("ID_SIGNER")
	  REFERENCES "BELTPP"."CCI_EMPLOYEE" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table FS_PRODUCT
--------------------------------------------------------

  ALTER TABLE "BELTPP"."FS_PRODUCT" ADD CONSTRAINT "FS_PRODUCT_FK_FSCERT" FOREIGN KEY ("ID_FSCERT")
	  REFERENCES "BELTPP"."FS_CERT" ("ID") ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRIGGER_FS_PRODUCT_DELETE
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TRIGGER "BELTPP"."TRIGGER_FS_PRODUCT_DELETE" 
AFTER DELETE ON FS_PRODUCT 
FOR EACH ROW
BEGIN
 delete from FS_PRODUCT_DENORM where id_fscert = :old.id_fscert;
END;
/
ALTER TRIGGER "BELTPP"."TRIGGER_FS_PRODUCT_DELETE" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRIGGER_FS_BLANK_DENORM
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TRIGGER "BELTPP"."TRIGGER_FS_BLANK_DENORM" 
AFTER DELETE ON FS_BLANK 
for each row
BEGIN
  delete from fs_blank_denorm where id_fscert = :old.id_fscert;
END;
/
ALTER TRIGGER "BELTPP"."TRIGGER_FS_BLANK_DENORM" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRIGGER_CLIENT_ADDRESS
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TRIGGER "BELTPP"."TRIGGER_CLIENT_ADDRESS" 
BEFORE INSERT OR UPDATE OF CINDEX,CITY,LINE,OFFICE,BUILDING ON CCI_CLIENT 
FOR EACH ROW
BEGIN
  :new.address := :new.cindex ||', '|| :new.CITY ||', '|| :new.LINE ||', '|| :new.BUILDING; 
  NULL;
END;
/
ALTER TRIGGER "BELTPP"."TRIGGER_CLIENT_ADDRESS" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRIGGER_BRANCH_ADDRESS
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TRIGGER "BELTPP"."TRIGGER_BRANCH_ADDRESS" 
BEFORE INSERT OR UPDATE OF CINDEX,CITY,LINE,OFFICE,BUILDING ON FS_BRANCH
FOR EACH ROW
BEGIN
  :new.address := :new.cindex ||', '|| :new.CITY ||', '|| :new.LINE ||', '|| :new.BUILDING; 
  NULL;
END;
/
ALTER TRIGGER "BELTPP"."TRIGGER_BRANCH_ADDRESS" ENABLE;

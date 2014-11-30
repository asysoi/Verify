DROP VIEW "CERT_CHILD_VIEW";
DROP VIEW "CERT_PARENT_VIEW";
DROP VIEW "CERT_VIEW";
DROP VIEW "CERT_VIEW_TOFILE";

DROP TABLE "C_COUNTRY";
DROP TABLE "C_FILES_IN";
DROP TABLE "C_FILES_OUT";
DROP TABLE "C_PRODUCT";
DROP TABLE "C_PRODUCT_DENORM";
DROP TABLE "C_CERT";
DROP TABLE "C_OTD";
DROP TABLE "C_SEQUENCE"; 

DROP TABLE "EV_GROUP_ACTION";
DROP TABLE "EV_GROUP_VALUE";
DROP TABLE "EV_ACTION";
DROP TABLE "EV_GROUP";
DROP TABLE "EV_REPORT";
DROP TABLE "EV_REPORT_MARK";
DROP TABLE "EV_REPORT_ROW";
DROP TABLE "EV_REPORT_VALUE";

DROP TABLE "PCH_PURCHASE";
DROP TABLE "PCH_COMPANY";
DROP TABLE "PCH_PRODUCT";

DROP SEQUENCE "CERT_ID_SEQ";
DROP SEQUENCE "FILE_ID_SEQ";
DROP SEQUENCE "ID_ACTION_SEQ";
DROP SEQUENCE "ID_PURCHASE_SEQ";
DROP SEQUENCE "ID_REPORT_SEQ";
DROP SEQUENCE "ID_ROW_SEQ";
DROP SEQUENCE "PRODUCT_ID_SEQ";


--------------------------------------------------------
  --  TABLE --  TABLE --  TABLE --  TABLE --  TABLE --
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Sequence CERT_ID_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "CERT_ID_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence FILE_ID_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "FILE_ID_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence ID_ACTION_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ID_ACTION_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence ID_PURCHASE_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ID_PURCHASE_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence ID_REPORT_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ID_REPORT_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence ID_ROW_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "ID_ROW_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Sequence PRODUCT_ID_SEQ
--------------------------------------------------------
   CREATE SEQUENCE  "PRODUCT_ID_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
   
--------------------------------------------------------
--  DDL for Table C_CERT
--------------------------------------------------------

  CREATE TABLE "C_CERT" ("CERT_ID" NUMBER, "FORMS" VARCHAR2(1000 BYTE), "UNN" VARCHAR2(1000 BYTE), "KONTRP" VARCHAR2(1000 BYTE), "KONTRS" VARCHAR2(1000 BYTE), "ADRESS" VARCHAR2(1000 BYTE), "POLUCHAT" VARCHAR2(1000 BYTE), "ADRESSPOL" VARCHAR2(1000 BYTE), "DATACERT" VARCHAR2(1000 BYTE), "NOMERCERT" VARCHAR2(1000 BYTE), "EXPERT" VARCHAR2(1000 BYTE), "NBLANKA" VARCHAR2(1000 BYTE), "RUKOVOD" VARCHAR2(1000 BYTE), "TRANSPORT" VARCHAR2(1000 BYTE), "MARSHRUT" VARCHAR2(1000 BYTE), "OTMETKA" VARCHAR2(1000 BYTE), "STRANAV" VARCHAR2(1000 BYTE), "STRANAPR" VARCHAR2(1000 BYTE), "STATUS" VARCHAR2(1000 BYTE), "KOLDOPLIST" NUMBER, "FLEXP" VARCHAR2(1000 BYTE), "UNNEXP" VARCHAR2(1000 BYTE), "EXPP" VARCHAR2(1000 BYTE), "EXPS" VARCHAR2(1000 BYTE), "EXPADRESS" VARCHAR2(1000 BYTE), "FLIMP" VARCHAR2(1000 BYTE), "IMPORTER" VARCHAR2(1000 BYTE), "ADRESSIMP" VARCHAR2(1000 BYTE), "FLSEZ" VARCHAR2(1000 BYTE), "SEZ" VARCHAR2(1000 BYTE), "FLSEZREZ" VARCHAR2(1000 BYTE), "STRANAP" VARCHAR2(1000 BYTE), "OTD_ID" NUMBER, "PARENTNUMBER" VARCHAR2(50 BYTE), "PARENTSTATUS" VARCHAR2(80 BYTE), "ISSUEDATE" DATE, "CODESTRANAV" VARCHAR2(4 BYTE), "CODESTRANAPR" VARCHAR2(4 BYTE), "CODESTRANAP" VARCHAR2(4 BYTE), "CATEGORY" VARCHAR2(20 BYTE)) ;
--------------------------------------------------------
--  DDL for Table C_COUNTRY
--------------------------------------------------------

  CREATE TABLE "C_COUNTRY" ("NAME" VARCHAR2(255 BYTE), "CODE" VARCHAR2(20 BYTE)) ;
--------------------------------------------------------
--  DDL for Table C_FILES_IN
--------------------------------------------------------

  CREATE TABLE "C_FILES_IN" ("FILE_IN_ID" NUMBER, "FILE_IN_NAME" VARCHAR2(255 BYTE), "CERT_ID" NUMBER, "DATE_LOAD" DATE) ;
--------------------------------------------------------
--  DDL for Table C_FILES_OUT
--------------------------------------------------------

  CREATE TABLE "C_FILES_OUT" ("FILE_IN_ID" NUMBER, "FILE_IN_NAME" VARCHAR2(255 BYTE), "CERT_ID" NUMBER, "DATE_LOAD" DATE) ;
--------------------------------------------------------
--  DDL for Table C_OTD
--------------------------------------------------------

   CREATE TABLE "C_OTD" 
   ("ID" NUMBER, 
	"OTD_NAME" VARCHAR2(255 BYTE), 
	"NAME_SYN" VARCHAR2(100 BYTE), 
	"OTD_ADDR_INDEX" VARCHAR2(250 BYTE), 
	"OTD_ADDR_CITY" VARCHAR2(250 BYTE), 
	"OTD_ADDR_LINE" VARCHAR2(250 BYTE), 
	"OTD_ADDR_BUILDING" VARCHAR2(250 BYTE), 
	"OTD_ADDR_OFFICE" VARCHAR2(250 BYTE), 
	"WORK_PHONE" VARCHAR2(50 BYTE), 
	"CELL_PHONE" VARCHAR2(50 BYTE), 
	"EOTD_NAME" VARCHAR2(255 BYTE), 
	"EOTD_ADDR_CITY" VARCHAR2(250 BYTE), 
	"EOTD_ADDR_LINE" VARCHAR2(250 BYTE)
   ) ;
  
--------------------------------------------------------
--  DDL for Table C_PRODUCT
--------------------------------------------------------

  CREATE TABLE "C_PRODUCT" ("PRODUCT_ID" NUMBER, "CERT_ID" NUMBER, "NUMERATOR" VARCHAR2(1000 BYTE), "TOVAR" VARCHAR2(2200 BYTE), "VIDUP" VARCHAR2(1000 BYTE), "KRITER" VARCHAR2(1000 BYTE), "VES" VARCHAR2(1000 BYTE), "SCHET" VARCHAR2(1000 BYTE), "FOBVALUE" VARCHAR2(200 BYTE)) ;
--------------------------------------------------------
--  DDL for Table C_PRODUCT_DENORM
--------------------------------------------------------

  CREATE TABLE "C_PRODUCT_DENORM" ("CERT_ID" NUMBER, "TOVAR" CLOB) ;
--------------------------------------------------------
--  DDL for Table EV_ACTION
--------------------------------------------------------

  CREATE TABLE "EV_ACTION" ("ID" NUMBER, "CODE" VARCHAR2(20 BYTE), "NAME" VARCHAR2(250 BYTE), "PARENT" VARCHAR2(20 BYTE)) ;
--------------------------------------------------------
--  DDL for Table EV_GROUP
--------------------------------------------------------

  CREATE TABLE "EV_GROUP" ("ID" NUMBER, "NAME" VARCHAR2(200 BYTE)) ;
--------------------------------------------------------
--  DDL for Table EV_GROUP_ACTION
--------------------------------------------------------

  CREATE TABLE "EV_GROUP_ACTION" ("ID" NUMBER, "ID_GROUP" NUMBER, "ID_ACTION" NUMBER) ;
--------------------------------------------------------
--  DDL for Table EV_GROUP_VALUE
--------------------------------------------------------

  CREATE TABLE "EV_GROUP_VALUE" ("ID" NUMBER, "ID_GROUP" NUMBER, "ID_MARK" NUMBER, "ID_REPORT" NUMBER) ;
--------------------------------------------------------
--  DDL for Table EV_REPORT
--------------------------------------------------------

  CREATE TABLE "EV_REPORT" ("ID" NUMBER, "YEAR" NUMBER, "QUARTER" NUMBER, "ID_OFFICE" NUMBER) ;
--------------------------------------------------------
--  DDL for Table EV_REPORT_MARK
--------------------------------------------------------

  CREATE TABLE "EV_REPORT_MARK" ("ID" NUMBER, "NAME" VARCHAR2(200 BYTE), "POSITION" NUMBER, "CODE" VARCHAR2(20 BYTE)) ;
--------------------------------------------------------
--  DDL for Table EV_REPORT_ROW
--------------------------------------------------------

  CREATE TABLE "EV_REPORT_ROW" ("ID_REPORT" NUMBER, "ACTION_CODE" NUMBER, "NUM_PAST" NUMBER DEFAULT 0, "NUM_CURRENT" NUMBER DEFAULT 0, "NUM_RISE" NUMBER DEFAULT 0, "NUM_PERCENT_RISE" NUMBER DEFAULT 0, "MONEY_PAST" NUMBER DEFAULT 0, "MONEY_CURRENT" NUMBER DEFAULT 0, "MONEY_RISE" NUMBER DEFAULT 0, "MONEY_PERCENT_RISE" NUMBER DEFAULT 0, "WEIGHT_FRD" NUMBER DEFAULT 0, "WEIGHT_ALL" NUMBER DEFAULT 0, "ID_ROW" NUMBER) ;
--------------------------------------------------------
--  DDL for Table EV_REPORT_VALUE
--------------------------------------------------------

  CREATE TABLE "EV_REPORT_VALUE" ("ID_REPORT" NUMBER, "ID_MARK" NUMBER, "ID_ACTION" NUMBER, "VALUE" NUMBER) ;
--------------------------------------------------------
--  DDL for Table PCH_COMPANY
--------------------------------------------------------

  //CREATE TABLE "PCH_COMPANY" ("ID" NUMBER, "NAME" VARCHAR2(200 BYTE), "ADDR_CITY" VARCHAR2(200 BYTE), "ADDR_LINE" VARCHAR2(200 BYTE), "ADDR_INDEX" VARCHAR2(200 BYTE), "ADDR_OFFICE" VARCHAR2(200 BYTE), "ADDR_BUILDING" VARCHAR2(200 BYTE), "WORK_PHONE" VARCHAR2(50 BYTE), "CELL_PHONE" VARCHAR2(50 BYTE)) ;
  --------------------------------------------------------
--  DDL for Table PCH_COMPANY
--------------------------------------------------------

  CREATE TABLE "BELTPP"."PCH_COMPANY" 
   (	"ID" NUMBER, 
	"OTD_NAME" VARCHAR2(200 BYTE), 
	"OTD_ADDR_CITY" VARCHAR2(200 BYTE), 
	"OTD_ADDR_LINE" VARCHAR2(200 BYTE), 
	"OTD_ADDR_INDEX" VARCHAR2(200 BYTE), 
	"OTD_ADDR_OFFICE" VARCHAR2(200 BYTE), 
	"OTD_ADDR_BUILDING" VARCHAR2(200 BYTE), 
	"WORK_PHONE" VARCHAR2(50 BYTE), 
	"CELL_PHONE" VARCHAR2(50 BYTE)
   ) ;
  
--------------------------------------------------------
--  DDL for Table PCH_PRODUCT
--------------------------------------------------------

  CREATE TABLE "PCH_PRODUCT" ("ID" NUMBER, "NAME" VARCHAR2(200 BYTE), "ID_PARENT" NUMBER) ;
--------------------------------------------------------
--  DDL for Table PCH_PURCHASE
--------------------------------------------------------

  CREATE TABLE "PCH_PURCHASE" ("ID" NUMBER, "ID_PRODUCT" NUMBER, "ID_OTD" NUMBER, "ID_COMPANY" NUMBER, "PRICE" NUMBER, "VOLUME" NUMBER, "UNIT" VARCHAR2(50 BYTE), "PCHDATE" DATE, "PRODUCTPROPERTY" VARCHAR2(1000 BYTE)) ;


  CREATE TABLE "C_SEQUENCE"  ("VALUE" NUMBER NOT NULL ENABLE,	"NAME" VARCHAR2(20 BYTE));
  INSERT INTO "C_SEQUENCE" VALUES (1, 'cert_id');
  
--------------------------------------------------------
--  VIEW --  VIEW --  VIEW --  VIEW --  VIEW --  VIEW --   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for View CERT_CHILD_VIEW
--------------------------------------------------------
  
  CREATE OR REPLACE VIEW "CERT_CHILD_VIEW" ("CERT_ID", "FORMS", "UNN", "KONTRP", "KONTRS", "ADRESS", "POLUCHAT", "ADRESSPOL", "DATACERT", "ISSUEDATE", "NOMERCERT", "EXPERT", "NBLANKA", "RUKOVOD", "TRANSPORT", "MARSHRUT", "OTMETKA", "STRANAV", "STRANAPR", "STATUS", "KOLDOPLIST", "FLEXP", "UNNEXP", "EXPP", "EXPS", "EXPADRESS", "FLIMP", "IMPORTER", "ADRESSIMP", "FLSEZ", "SEZ", "FLSEZREZ", "STRANAP", "OTD_ID", "PARENTNUMBER", "PARENTSTATUS", "CODESTRANAV", "CODESTRANAPR", "CODESTRANAP",  "CATEGORY", "CHLDNUMBER", "CHILD_ID" ) AS select tt."CERT_ID",tt."FORMS",tt."UNN",tt."KONTRP",tt."KONTRS",tt."ADRESS",tt."POLUCHAT",tt."ADRESSPOL",tt."DATACERT", tt."ISSUEDATE", tt."NOMERCERT",tt."EXPERT",tt."NBLANKA",tt."RUKOVOD",tt."TRANSPORT",tt."MARSHRUT",tt."OTMETKA",tt."STRANAV",tt."STRANAPR",tt."STATUS",tt."KOLDOPLIST",tt."FLEXP",tt."UNNEXP",tt."EXPP",tt."EXPS",tt."EXPADRESS",tt."FLIMP",tt."IMPORTER",tt."ADRESSIMP",tt."FLSEZ",tt."SEZ",tt."FLSEZREZ",tt."STRANAP",tt."OTD_ID",tt."PARENTNUMBER",tt."PARENTSTATUS",  tt."CODESTRANAV", tt."CODESTRANAPR", tt."CODESTRANAP",  tt."CATEGORY",  bb.nomercert chldnumber, bb.CERT_ID child_id from c_cert tt left join c_cert bb on tt.nomercert = bb.parentnumber;
--------------------------------------------------------
--  DDL for View CERT_PARENT_VIEW
--------------------------------------------------------

  CREATE OR REPLACE VIEW "CERT_PARENT_VIEW" ("CERT_ID", "FORMS", "UNN", "KONTRP", "KONTRS", "ADRESS", "POLUCHAT", "ADRESSPOL", "DATACERT", "ISSUEDATE", "NOMERCERT", "EXPERT", "NBLANKA", "RUKOVOD", "TRANSPORT", "MARSHRUT", "OTMETKA", "STRANAV", "STRANAPR", "STATUS", "KOLDOPLIST", "FLEXP", "UNNEXP", "EXPP", "EXPS", "EXPADRESS", "FLIMP", "IMPORTER", "ADRESSIMP", "FLSEZ", "SEZ", "FLSEZREZ", "STRANAP", "OTD_ID", "PARENTNUMBER", "PARENTSTATUS", "CODESTRANAV", "CODESTRANAPR", "CODESTRANAP",  "CATEGORY", "PARENT_ID") AS select tt."CERT_ID",tt."FORMS",tt."UNN",tt."KONTRP",tt."KONTRS",tt."ADRESS",tt."POLUCHAT",tt."ADRESSPOL",tt."DATACERT", tt."ISSUEDATE", tt."NOMERCERT",tt."EXPERT",tt."NBLANKA",tt."RUKOVOD",tt."TRANSPORT",tt."MARSHRUT",tt."OTMETKA",tt."STRANAV",tt."STRANAPR",tt."STATUS",tt."KOLDOPLIST",tt."FLEXP",tt."UNNEXP",tt."EXPP",tt."EXPS",tt."EXPADRESS",tt."FLIMP",tt."IMPORTER",tt."ADRESSIMP",tt."FLSEZ",tt."SEZ",tt."FLSEZREZ",tt."STRANAP",tt."OTD_ID",tt."PARENTNUMBER",tt."PARENTSTATUS",  tt."CODESTRANAV", tt."CODESTRANAPR", tt."CODESTRANAP",  tt."CATEGORY", bb.CERT_ID parent_id from c_cert tt left join c_cert bb on tt.parentnumber = bb.nomercert;
--------------------------------------------------------
--  DDL for View CERT_VIEW
--------------------------------------------------------

  CREATE OR REPLACE VIEW "CERT_VIEW" ("CERT_ID", "FORMS", "UNN", "KONTRP", "KONTRS", "ADRESS", "POLUCHAT", "ADRESSPOL", "DATACERT", "ISSUEDATE", "NOMERCERT", "EXPERT", "NBLANKA", "RUKOVOD", "TRANSPORT", "MARSHRUT", "OTMETKA", "STRANAV", "STRANAPR", "STATUS", "KOLDOPLIST", "FLEXP", "UNNEXP", "EXPP", "EXPS", "EXPADRESS", "FLIMP", "IMPORTER", "ADRESSIMP", "FLSEZ", "SEZ", "FLSEZREZ", "STRANAP", "OTD_ID", "PARENTNUMBER", "PARENTSTATUS", "CODESTRANAV", "CODESTRANAPR", "CODESTRANAP", "CATEGORY", "PARENT_ID", "OTD_NAME", "OTD_ADDR_INDEX", "OTD_ADDR_CITY", "OTD_ADDR_LINE", "OTD_ADDR_BUILDING", "EOTD_NAME", "EOTD_ADDR_CITY", "EOTD_ADDR_LINE", "EXPORTER", "IMPORTERFULL") AS 
  select a."CERT_ID",a."FORMS",a."UNN",a."KONTRP",a."KONTRS",a."ADRESS",a."POLUCHAT",a."ADRESSPOL",a."DATACERT",a."ISSUEDATE",a."NOMERCERT",a."EXPERT",a."NBLANKA",a."RUKOVOD",a."TRANSPORT",a."MARSHRUT",a."OTMETKA",a."STRANAV",a."STRANAPR",a."STATUS",a."KOLDOPLIST",a."FLEXP",a."UNNEXP",a."EXPP",a."EXPS",a."EXPADRESS",a."FLIMP",a."IMPORTER",a."ADRESSIMP",a."FLSEZ",a."SEZ",a."FLSEZREZ",a."STRANAP",a."OTD_ID",a."PARENTNUMBER",a."PARENTSTATUS", a."CODESTRANAV", a."CODESTRANAPR", a."CODESTRANAP", a."CATEGORY", a."PARENT_ID", B.OTD_NAME, B.OTD_ADDR_INDEX , B.OTD_ADDR_CITY, B.OTD_ADDR_LINE, B.OTD_ADDR_BUILDING, B.EOTD_NAME, B.EOTD_ADDR_CITY, B.EOTD_ADDR_LINE, decode(a."EXPP", null, a."KONTRP", a."EXPP") as exporter, decode(a."IMPORTER", null, a."POLUCHAT", a."IMPORTER") as importerfull from CERT_PARENT_VIEW a left join C_OTD b on A.OTD_ID = B.ID;
  
  
  
--------------------------------------------------------
--  DDL for View CERT_VIEW_TOFILE
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "BELTPP"."CERT_VIEW_TOFILE" ("CERT_ID", "FORMS", "UNN", "KONTRP", "KONTRS", "ADRESS", "POLUCHAT", "ADRESSPOL", "DATACERT", "ISSUEDATE", "NOMERCERT", "EXPERT", "NBLANKA", "RUKOVOD", "TRANSPORT", "MARSHRUT", "OTMETKA", "STRANAV", "STRANAPR", "STATUS", "KOLDOPLIST", "FLEXP", "UNNEXP", "EXPP", "EXPS", "EXPADRESS", "FLIMP", "IMPORTER", "ADRESSIMP", "FLSEZ", "SEZ", "FLSEZREZ", "STRANAP", "OTD_ID", "PARENTNUMBER", "PARENTSTATUS", "CODESTRANAV", "CODESTRANAPR", "CODESTRANAP", "CATEGORY", "PARENT_ID", "OTD_NAME", "OTD_ADDR_INDEX", "OTD_ADDR_CITY", "OTD_ADDR_LINE", "OTD_ADDR_BUILDING", "TOVAR") AS 
  SELECT cert."CERT_ID",cert."FORMS",cert."UNN",cert."KONTRP",cert."KONTRS",cert."ADRESS",cert."POLUCHAT",cert."ADRESSPOL",cert."DATACERT",cert."ISSUEDATE",cert."NOMERCERT",cert."EXPERT",cert."NBLANKA",cert."RUKOVOD",cert."TRANSPORT",cert."MARSHRUT",cert."OTMETKA",cert."STRANAV",cert."STRANAPR",cert."STATUS",cert."KOLDOPLIST",cert."FLEXP",cert."UNNEXP",cert."EXPP",cert."EXPS",cert."EXPADRESS",cert."FLIMP",cert."IMPORTER",cert."ADRESSIMP",cert."FLSEZ",cert."SEZ",cert."FLSEZREZ",cert."STRANAP",cert."OTD_ID",cert."PARENTNUMBER",cert."PARENTSTATUS", cert."CODESTRANAV", cert."CODESTRANAPR", cert."CODESTRANAP",  cert."CATEGORY", cert."PARENT_ID",cert."OTD_NAME",cert."OTD_ADDR_INDEX",cert."OTD_ADDR_CITY",cert."OTD_ADDR_LINE",cert."OTD_ADDR_BUILDING", a.tovar FROM CERT_VIEW cert INNER JOIN C_PRODUCT_DENORM a on cert.CERT_ID=a.CERT_ID;


--------------------------------------------------------
--  DDL for View CERT_VIEW_TOFILE
--------------------------------------------------------
CREATE OR REPLACE FORCE EDITIONABLE VIEW "BELTPP"."PCH_PURCHASE_VIEW" ("ID", "ID_PRODUCT", "ID_OTD", "ID_COMPANY", "PRICE", "VOLUME", "UNIT", "PCHDATE", "PRODUCTPROPERTY", "DEPARTMENT", "COMPANY", "PRODUCT") AS  
SELECT p."ID",p."ID_PRODUCT",p."ID_OTD",p."ID_COMPANY",p."PRICE",p."VOLUME",p."UNIT",p."PCHDATE",p."PRODUCTPROPERTY", c.otd_name department, pc.otd_name company, pp.name product
FROM PCH_PURCHASE p 
LEFT JOIN C_OTD c on p.id_otd = c.id 
LEFT JOIN PCH_COMPANY pc on p.id_company = pc.id
LEFT JOIN PCH_PRODUCT pp on p.id_product = pp.id;

  
  
  --------------------------------------------------------
--  DDL for Index CERT_NUMBER_IDX
--------------------------------------------------------
  
  
--------------------------------------------------------
-- INDEX -- INDEX --  INDEX --  INDEX --  INDEX --  INDEX   
--------------------------------------------------------
  CREATE UNIQUE INDEX "CERT_NUMBER_IDX" ON "C_CERT" ("NOMERCERT") ;
--------------------------------------------------------
--  DDL for Index CERT_PARENTNUMBER_IDX
--------------------------------------------------------

  CREATE INDEX "CERT_PARENTNUMBER_IDX" ON "C_CERT" ("PARENTNUMBER") ;
--------------------------------------------------------
--  DDL for Index CODE_IND
--------------------------------------------------------

  CREATE INDEX "CODE_IND" ON "EV_ACTION" ("CODE") ;
--------------------------------------------------------
--  DDL for Index C_COUNTRY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "C_COUNTRY_PK" ON "C_COUNTRY" ("CODE") ;
--------------------------------------------------------
--  DDL for Index GROUP_ACTION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "GROUP_ACTION_PK" ON "EV_GROUP_ACTION" ("ID") ;
--------------------------------------------------------
--  DDL for Index GROUP_VALUE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "GROUP_VALUE_PK" ON "EV_GROUP_VALUE" ("ID") ;
--------------------------------------------------------
--  DDL for Index ID
--------------------------------------------------------

  CREATE UNIQUE INDEX "ID_COMPANY" ON "PCH_COMPANY" ("ID") ;
--------------------------------------------------------
--  DDL for Index ID_GROUP_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ID_GROUP_PK" ON "EV_GROUP" ("ID") ;
--------------------------------------------------------
--  DDL for Index ID_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "ID_PK" ON "EV_ACTION" ("ID") ;
--------------------------------------------------------
--  DDL for Index ID_PRODUCT
--------------------------------------------------------

  CREATE UNIQUE INDEX "ID_PRODUCT" ON "PCH_PRODUCT" ("ID") ;
--------------------------------------------------------
--  DDL for Index ID_PURCHASE
--------------------------------------------------------

  CREATE UNIQUE INDEX "ID_PURCHASE" ON "PCH_PURCHASE" ("ID") ;
--------------------------------------------------------
--  DDL for Index REPORT_MARK_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "REPORT_MARK_PK" ON "EV_REPORT_MARK" ("ID") ;
--------------------------------------------------------
--  DDL for Index REPORT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "REPORT_PK" ON "EV_REPORT" ("ID") ;
--------------------------------------------------------
--  DDL for Index SYS_C006997
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C006997" ON "C_OTD" ("ID") ;
--------------------------------------------------------
--  DDL for Index SYS_C007001
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C007001" ON "C_CERT" ("CERT_ID") ;
--------------------------------------------------------
--  DDL for Index SYS_C007010
--------------------------------------------------------

  CREATE UNIQUE INDEX "SYS_C007010" ON "C_PRODUCT" ("PRODUCT_ID") ;
  
  
  
  
--------------------------------------------------------
--  Constraints --  Constraints --  Constraints --  
--------------------------------------------------------

  ALTER TABLE "C_CERT" ADD PRIMARY KEY ("CERT_ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table C_COUNTRY
--------------------------------------------------------

  ALTER TABLE "C_COUNTRY" MODIFY ("CODE" NOT NULL ENABLE);
  ALTER TABLE "C_COUNTRY" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "C_COUNTRY" ADD CONSTRAINT "C_COUNTRY_PK" PRIMARY KEY ("CODE") ENABLE;
--------------------------------------------------------
--  Constraints for Table C_OTD
--------------------------------------------------------

  ALTER TABLE "C_OTD" ADD PRIMARY KEY ("ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table C_PRODUCT
--------------------------------------------------------

  ALTER TABLE "C_PRODUCT" ADD PRIMARY KEY ("PRODUCT_ID") ENABLE;
--------------------------------------------------------
--  Constraints for Table EV_ACTION
--------------------------------------------------------

  ALTER TABLE "EV_ACTION" ADD CONSTRAINT "ID_PK" PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "EV_ACTION" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table EV_GROUP
--------------------------------------------------------

  ALTER TABLE "EV_GROUP" ADD CONSTRAINT "ID_GROUP_PK" PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "EV_GROUP" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table EV_GROUP_ACTION
--------------------------------------------------------

  ALTER TABLE "EV_GROUP_ACTION" ADD CONSTRAINT "GROUP_ACTION_PK" PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "EV_GROUP_ACTION" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table EV_GROUP_VALUE
--------------------------------------------------------

  ALTER TABLE "EV_GROUP_VALUE" ADD CONSTRAINT "GROUP_VALUE_PK" PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "EV_GROUP_VALUE" MODIFY ("ID_REPORT" NOT NULL ENABLE);
  ALTER TABLE "EV_GROUP_VALUE" MODIFY ("ID_MARK" NOT NULL ENABLE);
  ALTER TABLE "EV_GROUP_VALUE" MODIFY ("ID_GROUP" NOT NULL ENABLE);
  ALTER TABLE "EV_GROUP_VALUE" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table EV_REPORT
--------------------------------------------------------

  ALTER TABLE "EV_REPORT" ADD CONSTRAINT "REPORT_PK" PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "EV_REPORT" MODIFY ("ID_OFFICE" NOT NULL ENABLE);
  ALTER TABLE "EV_REPORT" MODIFY ("QUARTER" NOT NULL ENABLE);
  ALTER TABLE "EV_REPORT" MODIFY ("YEAR" NOT NULL ENABLE);
  ALTER TABLE "EV_REPORT" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table EV_REPORT_MARK
--------------------------------------------------------

  ALTER TABLE "EV_REPORT_MARK" ADD CONSTRAINT "REPORT_MARK_PK" PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "EV_REPORT_MARK" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table EV_REPORT_ROW
--------------------------------------------------------

  ALTER TABLE "EV_REPORT_ROW" MODIFY ("ID_ROW" NOT NULL ENABLE);
  ALTER TABLE "EV_REPORT_ROW" MODIFY ("ID_REPORT" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PCH_COMPANY
--------------------------------------------------------

  ALTER TABLE "PCH_COMPANY" ADD CONSTRAINT "ID" PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "PCH_COMPANY" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PCH_PRODUCT
--------------------------------------------------------

  ALTER TABLE "PCH_PRODUCT" ADD CONSTRAINT "ID_PRODUCT" PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "PCH_PRODUCT" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PCH_PURCHASE
--------------------------------------------------------

  ALTER TABLE "PCH_PURCHASE" ADD CONSTRAINT "ID_PURCHASE" PRIMARY KEY ("ID") ENABLE;
  ALTER TABLE "PCH_PURCHASE" MODIFY ("ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Ref Constraints for Table C_CERT
--------------------------------------------------------

  ALTER TABLE "C_CERT" ADD FOREIGN KEY ("OTD_ID") REFERENCES "C_OTD" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table C_PRODUCT
--------------------------------------------------------

  ALTER TABLE "C_PRODUCT" ADD FOREIGN KEY ("CERT_ID") REFERENCES "C_CERT" ("CERT_ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table EV_GROUP_ACTION
--------------------------------------------------------

  ALTER TABLE "EV_GROUP_ACTION" ADD CONSTRAINT "ACTION_FK" FOREIGN KEY ("ID_ACTION") REFERENCES "EV_ACTION" ("ID") ENABLE;
  ALTER TABLE "EV_GROUP_ACTION" ADD CONSTRAINT "GROUP_FK" FOREIGN KEY ("ID_GROUP") REFERENCES "EV_GROUP" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table EV_GROUP_VALUE
--------------------------------------------------------

  ALTER TABLE "EV_GROUP_VALUE" ADD CONSTRAINT "GROUP_GROUP_FK" FOREIGN KEY ("ID_GROUP") REFERENCES "EV_GROUP" ("ID") ENABLE;
  ALTER TABLE "EV_GROUP_VALUE" ADD CONSTRAINT "GROUP_MARK_FK" FOREIGN KEY ("ID_MARK") REFERENCES "EV_REPORT_MARK" ("ID") ENABLE;
  ALTER TABLE "EV_GROUP_VALUE" ADD CONSTRAINT "GROUP_REPORT_FK" FOREIGN KEY ("ID_REPORT") REFERENCES "EV_REPORT" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PCH_PRODUCT
--------------------------------------------------------

  ALTER TABLE "PCH_PRODUCT" ADD CONSTRAINT "FK_ID_PARENT" FOREIGN KEY ("ID_PARENT") REFERENCES "PCH_PRODUCT" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table PCH_PURCHASE
--------------------------------------------------------

  ALTER TABLE "PCH_PURCHASE" ADD CONSTRAINT "FK_ID_COMPANY" FOREIGN KEY ("ID_COMPANY") REFERENCES "PCH_COMPANY" ("ID") ENABLE;
  ALTER TABLE "PCH_PURCHASE" ADD CONSTRAINT "FK_ID_PRODUCT" FOREIGN KEY ("ID") REFERENCES "PCH_PRODUCT" ("ID") ENABLE;


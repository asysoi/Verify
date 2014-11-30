--------------------------------------------------------
--  File created - понедельник-Декабрь-01-2014   
--------------------------------------------------------
DROP TABLE "PCH_COMPANY";
DROP TABLE "CCI_CLIENT";
DROP SEQUENCE "ID_CLIENT_SEQ";
DROP VIEW "CLIENT_VIEW";
DROP VIEW "PCH_PURCHASE_VIEW";
--------------------------------------------------------
--  DDL for Sequence ID_CLIENT_SEQ
--------------------------------------------------------

   CREATE SEQUENCE  "ID_CLIENT_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 22 CACHE 20 NOORDER  NOCYCLE  NOPARTITION ;
--------------------------------------------------------
--  DDL for Table CCI_CLIENT
--------------------------------------------------------

  CREATE TABLE "CCI_CLIENT" 
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
	"BCODECOUNTRY" VARCHAR2(20 BYTE)
   ) ;
--------------------------------------------------------
--  DDL for View CLIENT_VIEW
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "CLIENT_VIEW" ("ID", "NAME", "CITY", "LINE", "CINDEX", "OFFICE", "BUILDING", "WORK_PHONE", "CELL_PHONE", "UNP", "OKPO", "BNAME", "BCITY", "BLINE", "BINDEX", "BOFFICE", "BBUILDING", "ACCOUNT", "BUNP", "EMAIL", "BEMAIL", "CODECOUNTRY", "BCODECOUNTRY", "ADDRESS", "COUNTRY", "BCOUNTRY") AS 
  SELECT  cl."ID",cl."NAME",cl."CITY",cl."LINE",cl."CINDEX",cl."OFFICE",cl."BUILDING",cl."WORK_PHONE",cl."CELL_PHONE",cl."UNP",cl."OKPO",cl."BNAME",cl."BCITY",cl."BLINE",cl."BINDEX",cl."BOFFICE",cl."BBUILDING",cl."ACCOUNT",cl."BUNP",cl."EMAIL",cl."BEMAIL",cl."CODECOUNTRY",cl."BCODECOUNTRY", CITY||', '||LINE||', '||CINDEX||', '||OFFICE||', '||BUILDING address, cc.name as country, cb.name as bcountry  
FROM CCI_CLIENT cl 
LEFT JOIN C_COUNTRY cc ON cl.codecountry = cc.code 
LEFT JOIN C_COUNTRY cb ON cl.bcodecountry = cb.code;
--------------------------------------------------------
--  DDL for View PCH_PURCHASE_VIEW
--------------------------------------------------------

  CREATE OR REPLACE FORCE EDITIONABLE VIEW "PCH_PURCHASE_VIEW" ("ID", "ID_PRODUCT", "ID_OTD", "ID_COMPANY", "PRICE", "VOLUME", "UNIT", "PCHDATE", "PRODUCTPROPERTY", "DEPARTMENT", "COMPANY", "PRODUCT") AS 
  SELECT p."ID",p."ID_PRODUCT",p."ID_OTD",p."ID_COMPANY",p."PRICE",p."VOLUME",p."UNIT",p."PCHDATE",p."PRODUCTPROPERTY", c.otd_name department, pc.name company, pp.name product
FROM PCH_PURCHASE p 
LEFT JOIN C_OTD c on p.id_otd = c.id 
LEFT JOIN CCI_CLIENT pc on p.id_company = pc.id
LEFT JOIN PCH_PRODUCT pp on p.id_product = pp.id;
--------------------------------------------------------
--  DDL for Index PCH_COMPANY_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCI_CLIENT_PK" ON "CCI_CLIENT" ("ID") 
  ;
--------------------------------------------------------
--  Constraints for Table CCI_CLIENT
--------------------------------------------------------

  ALTER TABLE "CCI_CLIENT" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "CCI_CLIENT" ADD CONSTRAINT "CCI_CLIENT_PK" PRIMARY KEY ("ID")
  USING INDEX  ENABLE;

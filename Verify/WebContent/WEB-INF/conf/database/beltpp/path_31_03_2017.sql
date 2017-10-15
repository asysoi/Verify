--------------------------------------------------------
--  File created - РїСЏС‚РЅРёС†Р°-РњР°СЂС‚-31-2017   
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Table CCI_CLIENT
--------------------------------------------------------

  DROP TABLE "CCI_CLIENT";
  DROP TABLE "CCI_CLIENT_LOCALE";
  DROP TABLE "CCI_EMPLOYEE";
  DROP TABLE "CCI_EMPLOYEE_LOCALE";
  DROP TABLE "FS_BRANCH_LOCALE";
  
  
  
  CREATE TABLE "CCI_CLIENT" 
   ("ID" NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ,, 
	"NAME" VARCHAR2(200), 
	"CITY" VARCHAR2(200), 
	"STREET" VARCHAR2(200), 
	"CINDEX" VARCHAR2(200), 
	"OFFICE" VARCHAR2(200), 
	"BUILDING" VARCHAR2(200), 
	"PHONE" VARCHAR2(50), 
	"CELL" VARCHAR2(50),
    "FAX" VARCHAR2(50)	
	"UNP" VARCHAR2(30), 
	"OKPO" VARCHAR2(30), 
	"BNAME" VARCHAR2(200), 
	"BCITY" VARCHAR2(200), 
	"BSTREET" VARCHAR2(200), 
	"BINDEX" VARCHAR2(20), 
	"BOFFICE" VARCHAR2(20), 
	"BBUILDING" VARCHAR2(20), 
	"ACCOUNT" VARCHAR2(50), 
	"BUNP" VARCHAR2(50), 
	"EMAIL" VARCHAR2(80), 
	"BEMAIL" VARCHAR2(20), 
	"CODECOUNTRY" VARCHAR2(20), 
	"BCODECOUNTRY" VARCHAR2(20), 
	"ADDRESS" VARCHAR2(512), 
	"VERSION" NUMBER DEFAULT 0, 
	"LOCKED" NUMBER(1,0) DEFAULT 0 
   ) ;
--------------------------------------------------------
--  DDL for Table CCI_CLIENT_LOCALE
--------------------------------------------------------

  CREATE TABLE "CCI_CLIENT_LOCALE" 
   ("IDCLIENT" NUMBER, 
	"NAME" VARCHAR2(1000), 
	"STREET" VARCHAR2(1000), 
	"CITY" VARCHAR2(100), 
	"ADDRESS" VARCHAR2(1000), 
	"LOCALE" VARCHAR2(2)
   ) ;
--------------------------------------------------------
--  DDL for Table CCI_EMPLOYEE
--------------------------------------------------------

  CREATE TABLE "CCI_EMPLOYEE" 
   ("ID" NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE , 
	"NAME" VARCHAR2(120), 
	"JOB" VARCHAR2(120), 
	"ID_DEPARTMENT" NUMBER, 
	"FIRSTNAME" VARCHAR2(50), 
	"LASTNAME" VARCHAR2(50), 
	"MIDDLENAME" VARCHAR2(50), 
	"PHONE" VARCHAR2(20), 
	"EMAIL" VARCHAR2(20), 
	"BDAY" DATE, 
	"ACTIVE" CHAR(1) DEFAULT 'Y'
   ) ;
--------------------------------------------------------
--  DDL for Table CCI_EMPLOYEE_LOCALE
--------------------------------------------------------

  CREATE TABLE "CCI_EMPLOYEE_LOCALE" 
   ("IDEMPLOYEE" NUMBER, 
	"LOCALE" VARCHAR2(2), 
	"NAME" VARCHAR2(200), 
	"JOB" VARCHAR2(200)
   ) ;
--------------------------------------------------------
--  DDL for Table FS_BRANCH
--------------------------------------------------------

  CREATE TABLE "FS_BRANCH" 
   ("ID" NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE , 
	"NAME" VARCHAR2(2000), 
	"CITY" VARCHAR2(200), 
	"LINE" VARCHAR2(200), 
	"CINDEX" VARCHAR2(200), 
	"OFFICE" VARCHAR2(200), 
	"BUILDING" VARCHAR2(200), 
	"PHONE" VARCHAR2(50), 
	"CELL" VARCHAR2(50),
	"FAX" VARCHAR2(50),	
	"UNP" VARCHAR2(30), 
	"OKPO" VARCHAR2(30), 
	"BNAME" VARCHAR2(2000), 
	"BCITY" VARCHAR2(200), 
	"BSTREET" VARCHAR2(200), 
	"BINDEX" VARCHAR2(20), 
	"BOFFICE" VARCHAR2(20), 
	"BBUILDING" VARCHAR2(20), 
	"ACCOUNT" VARCHAR2(50), 
	"BUNP" VARCHAR2(50), 
	"EMAIL" VARCHAR2(80), 
	"BEMAIL" VARCHAR2(20), 
	"CODECOUNTRY" VARCHAR2(20), 
	"BCODECOUNTRY" VARCHAR2(20), 
	"ADDRESS" VARCHAR2(512), 
	"VERSION" NUMBER DEFAULT 0, 
	"LOCKED" NUMBER(1,0) DEFAULT 0
   ) ;
--------------------------------------------------------
--  DDL for Table FS_BRANCH_LOCALE
--------------------------------------------------------

  CREATE TABLE "FS_BRANCH_LOCALE" 
   ("IDBRANCH" NUMBER, 
	"LOCALE" VARCHAR2(2), 
	"NAME" VARCHAR2(1000), 
	"CITY" VARCHAR2(120), 
	"STREET" VARCHAR2(120), 
	"ADDRESS" VARCHAR2(1000)
   ) ;
--------------------------------------------------------
--  DDL for Table FS_TEMPLATE
--------------------------------------------------------

  CREATE TABLE "FS_TEMPLATE" 
   ("ID" NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE , ,
    "KEY" VARCHAR2(50), 
	"TEXT" VARCHAR2(2024), 
   ) ;
--------------------------------------------------------
--  DDL for Table FS_TEMPLATE_LOCALE
--------------------------------------------------------

  CREATE TABLE "FS_TEMPLATE_LOCALE" 
   ("IDKEY" NUMBER, 
	"LOCALE" VARCHAR2(2), 
	"TEXT" VARCHAR2(2000)
   ) ;
--------------------------------------------------------
--  DDL for View CLIENT_VIEW
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE VIEW "CLIENT_VIEW" ("ID", "NAME", "CITY", "STREET", "CINDEX", "OFFICE", "BUILDING", "PHONE", "CELL", "FAX", "UNP", "OKPO", "BNAME", "BCITY", "BSTREET", "BINDEX", "BOFFICE", "BBUILDING", "ACCOUNT", "BUNP", "EMAIL", "BEMAIL", "CODECOUNTRY", "BCODECOUNTRY", "ADDRESS", "VERSION", "LOCKED", "COUNTRY", "BCOUNTRY") AS 
  SELECT  cl."ID",cl."NAME", cl."CITY",cl."STREET",cl."CINDEX",cl."OFFICE",cl."BUILDING",cl."PHONE",cl."CELL", cl."FAX", cl."UNP",cl."OKPO",cl."BNAME",cl."BCITY",cl."BSTREET",cl."BINDEX",cl."BOFFICE",cl."BBUILDING",cl."ACCOUNT",cl."BUNP",cl."EMAIL",cl."BEMAIL",cl."CODECOUNTRY",cl."BCODECOUNTRY", cl."ADDRESS", cl."VERSION", cl."LOCKED", cc.name as country, cb.name as bcountry  
FROM CCI_CLIENT cl 
LEFT JOIN C_COUNTRY cc ON cl.codecountry = cc.code 
LEFT JOIN C_COUNTRY cb ON cl.bcodecountry = cb.code;
--------------------------------------------------------
--  DDL for Index CCI_CLIENT_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCI_CLIENT_PK" ON "CCI_CLIENT" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index CCI_EMPLOYEE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "CCI_EMPLOYEE_PK" ON "CCI_EMPLOYEE" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index FS_BRANCH_NAME
--------------------------------------------------------

  CREATE INDEX "FS_BRANCH_NAME" ON "FS_BRANCH" ("NAME") 
  ;
--------------------------------------------------------
--  DDL for Index FS_BRANCH_LINE
--------------------------------------------------------

  CREATE INDEX "FS_BRANCH_STREET" ON "FS_BRANCH" ("STREET") 
  ;
--------------------------------------------------------
--  DDL for Index FS_BRANCH_OFFICE
--------------------------------------------------------

  CREATE INDEX "FS_BRANCH_OFFICE" ON "FS_BRANCH" ("OFFICE") 
  ;
--------------------------------------------------------
--  DDL for Index FS_BRANCH_INDEX
--------------------------------------------------------

  CREATE INDEX "FS_BRANCH_INDEX" ON "FS_BRANCH" ("CINDEX") 
  ;
--------------------------------------------------------
--  DDL for Index FS_BRANCH_BUILDING
--------------------------------------------------------

  CREATE INDEX "FS_BRANCH_BUILDING" ON "FS_BRANCH" ("BUILDING") 
  ;
--------------------------------------------------------
--  DDL for Index FS_BRANCH_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "FS_BRANCH_PK" ON "FS_BRANCH" ("ID") 
  ;
--------------------------------------------------------
--  DDL for Index FS_TEMPLATE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "FS_TEMPLATE_PK" ON "FS_TEMPLATE" ("ID") 
  ;
--------------------------------------------------------
--  Constraints for Table CCI_CLIENT
--------------------------------------------------------

  ALTER TABLE "CCI_CLIENT" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "CCI_CLIENT" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "CCI_CLIENT" ADD CONSTRAINT "CCI_CLIENT_PK" PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table CCI_EMPLOYEE
--------------------------------------------------------

  ALTER TABLE "CCI_EMPLOYEE" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "CCI_EMPLOYEE" ADD CONSTRAINT "CCI_EMPLOYEE_PK" PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
  ALTER TABLE "CCI_EMPLOYEE" MODIFY ("JOB" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table FS_BRANCH
--------------------------------------------------------

  ALTER TABLE "FS_BRANCH" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "FS_BRANCH" ADD CONSTRAINT "FS_BRANCH_PK" PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table FS_TEMPLATE
--------------------------------------------------------

  ALTER TABLE "FS_TEMPLATE" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "FS_TEMPLATE" ADD CONSTRAINT "FS_TEMPLATE_PK" PRIMARY KEY ("ID")
  USING INDEX  ENABLE;
--------------------------------------------------------
--  Constraints for Table FS_TEMPLATE_LOCALE
--------------------------------------------------------

  ALTER TABLE "FS_TEMPLATE_LOCALE" MODIFY ("LOCALE" NOT NULL ENABLE);
  
  
// DEPARTMENT MODIFICATION
ALTER TABLE CCI_DEPARTMENT DROP COLUMN ID;

ALTER TABLE CCI_DEPARTMENT 
ADD ("ID" NUMBER GENERATED BY DEFAULT ON NULL AS IDENTITY MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE);				  
  


--------------------------------------------------------
--  File created - четверг-Апрель-06-2017   
--------------------------------------------------------
REM INSERTING into BELTPP.FS_BRANCH
SET DEFINE OFF;
Insert into BELTPP.FS_BRANCH (ID,NAME,CITY,STREET,CINDEX,OFFICE,BUILDING,PHONE,CELL,UNP,OKPO,BNAME,BCITY,BSTREET,BINDEX,BOFFICE,BBUILDING,ACCOUNT,BUNP,EMAIL,BEMAIL,CODECOUNTRY,BCODECOUNTRY,ADDRESS,VERSION,LOCKED,FAX,CODE) values (2,'Унитарное предприятие по оказанию услуг «Бресткое отделение Белорусской торгово-промышленной палаты»','Брест','Интернациональная','220113',null,'50','+375 17 280 04 73',null,null,null,null,null,null,null,null,null,null,null,'tppm@cci.by',null,'BY',null,'220113, Минск, ул. Якуба Коласа, 65, Республика Беларусь',1,0,'12234567','2');
Insert into BELTPP.FS_BRANCH (ID,NAME,CITY,STREET,CINDEX,OFFICE,BUILDING,PHONE,CELL,UNP,OKPO,BNAME,BCITY,BSTREET,BINDEX,BOFFICE,BBUILDING,ACCOUNT,BUNP,EMAIL,BEMAIL,CODECOUNTRY,BCODECOUNTRY,ADDRESS,VERSION,LOCKED,FAX,CODE) values (1,'Унитарное предприятие по оказанию услуг «Минское отделение Белорусской торгово-промышленной палаты»','Минск','Якуба Коласа','220113',null,'65','+375 17 280 04 73',null,null,null,null,null,null,null,null,null,null,null,'tppm@tppm.by',null,'BY',null,'220113, Брест, ул. Интернациональная, 50, Республика Беларусь',1,0,'45628849','1');
Insert into BELTPP.FS_BRANCH (ID,NAME,CITY,STREET,CINDEX,OFFICE,BUILDING,PHONE,CELL,UNP,OKPO,BNAME,BCITY,BSTREET,BINDEX,BOFFICE,BBUILDING,ACCOUNT,BUNP,EMAIL,BEMAIL,CODECOUNTRY,BCODECOUNTRY,ADDRESS,VERSION,LOCKED,FAX,CODE) values (5,'Унитарное предприятие по оказанию услуг «Витебское отделение Белорусской торгово-промышленной палаты»','Витебск','Космонавтов','210001',null,'4','+375 17 280 04 73',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,'210001, Витебск, ул. Космонавтов, 4, Республика Беларусь',1,0,'676666','5');
Insert into BELTPP.FS_BRANCH (ID,NAME,CITY,STREET,CINDEX,OFFICE,BUILDING,PHONE,CELL,UNP,OKPO,BNAME,BCITY,BSTREET,BINDEX,BOFFICE,BBUILDING,ACCOUNT,BUNP,EMAIL,BEMAIL,CODECOUNTRY,BCODECOUNTRY,ADDRESS,VERSION,LOCKED,FAX,CODE) values (3,'Унитарное предприятие по оказанию услуг «Гомельское отделение Белорусской торгово-промышленной палаты»','Гомель','Ирининская','246050',null,'21','+375 17 280 04 73',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,'246050, Гомель, ул. Ирининская, 21, Республика Беларусь',1,0,'1234','3');
Insert into BELTPP.FS_BRANCH (ID,NAME,CITY,STREET,CINDEX,OFFICE,BUILDING,PHONE,CELL,UNP,OKPO,BNAME,BCITY,BSTREET,BINDEX,BOFFICE,BBUILDING,ACCOUNT,BUNP,EMAIL,BEMAIL,CODECOUNTRY,BCODECOUNTRY,ADDRESS,VERSION,LOCKED,FAX,CODE) values (4,'Унитарное предприятие по оказанию услуг «Гродненское отделение Белорусской торгово-промышленной палаты»','Гродно','Cоветская','230023',null,'23а','+375 17 280 04 73',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,'230023, Гродно, ул. Cоветская, 23а, Республика Беларусь',1,0,'5555','4');
Insert into BELTPP.FS_BRANCH (ID,NAME,CITY,STREET,CINDEX,OFFICE,BUILDING,PHONE,CELL,UNP,OKPO,BNAME,BCITY,BSTREET,BINDEX,BOFFICE,BBUILDING,ACCOUNT,BUNP,EMAIL,BEMAIL,CODECOUNTRY,BCODECOUNTRY,ADDRESS,VERSION,LOCKED,FAX,CODE) values (6,'Унитарное предприятие по оказанию услуг «Могилевское отделение Белорусской торгово-промышленной палаты»','Могилев','Первомайская','212030',null,'71','+375 222 32 68 50',null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,'212030, Могилев, ул. Первомайская, 71, Республика Беларусь',1,0,'777777','6');

--------------------------------------------------------
--  File created - четверг-Апрель-06-2017   
--------------------------------------------------------
REM INSERTING into BELTPP.C_OTD
SET DEFINE OFF;
Insert into BELTPP.C_OTD (ID,OTD_NAME,NAME_SYN,OTD_ADDR_INDEX,OTD_ADDR_CITY,OTD_ADDR_LINE,OTD_ADDR_BUILDING,OTD_ADDR_OFFICE,WORK_PHONE,CELL_PHONE,EOTD_NAME,EOTD_ADDR_CITY,EOTD_ADDR_LINE,ACL_ROLE,CODE) values (1,'Минское отделение Белорусской торгово-промышленной палаты','minsk','220113','Минск','Я.Колоса','65',null,'+375 17 2884922',null,'The unitary enterprise for rendering of services "Minsk Branch of the Belarusian Chamber of Commerce and Indastry"','Minsk','Ya. Kolas St.','ROLE_MINSK','1');
Insert into BELTPP.C_OTD (ID,OTD_NAME,NAME_SYN,OTD_ADDR_INDEX,OTD_ADDR_CITY,OTD_ADDR_LINE,OTD_ADDR_BUILDING,OTD_ADDR_OFFICE,WORK_PHONE,CELL_PHONE,EOTD_NAME,EOTD_ADDR_CITY,EOTD_ADDR_LINE,ACL_ROLE,CODE) values (2,'Брестское отделение Белорусской торгово-промышленной палаты','brest','224030','Брест','ул. Гоголя','13',null,null,null,'The unitary enterprise for rendering of services "The Brest Branch of Chamber of Commerce and Industry"','Brest','Gogol','ROLE_BREST','2');
Insert into BELTPP.C_OTD (ID,OTD_NAME,NAME_SYN,OTD_ADDR_INDEX,OTD_ADDR_CITY,OTD_ADDR_LINE,OTD_ADDR_BUILDING,OTD_ADDR_OFFICE,WORK_PHONE,CELL_PHONE,EOTD_NAME,EOTD_ADDR_CITY,EOTD_ADDR_LINE,ACL_ROLE,CODE) values (3,'Витебское отделение Белорусской торгово-промышленной палаты','vitebsk','210001','Витебск','ул. Космонавтов','4',null,null,null,'The unitary enterprise for rendering of services "The Vitebsk Branch of Chamber of Commerce and Industry"','Vitebsk','Kosmonavtov','ROLE_VITEBSK','5');
Insert into BELTPP.C_OTD (ID,OTD_NAME,NAME_SYN,OTD_ADDR_INDEX,OTD_ADDR_CITY,OTD_ADDR_LINE,OTD_ADDR_BUILDING,OTD_ADDR_OFFICE,WORK_PHONE,CELL_PHONE,EOTD_NAME,EOTD_ADDR_CITY,EOTD_ADDR_LINE,ACL_ROLE,CODE) values (4,'Гомельское отделение Белорусской торгово-промышленной палаты','gomel','246050','Гомель','ул. Ирининская','21',null,null,null,'The unitary enterprise for rendering of services "The Gomel Branch of Chamber of Commerce and Industry"','Gomel','Irininskay','ROLE_GOMEL','3');
Insert into BELTPP.C_OTD (ID,OTD_NAME,NAME_SYN,OTD_ADDR_INDEX,OTD_ADDR_CITY,OTD_ADDR_LINE,OTD_ADDR_BUILDING,OTD_ADDR_OFFICE,WORK_PHONE,CELL_PHONE,EOTD_NAME,EOTD_ADDR_CITY,EOTD_ADDR_LINE,ACL_ROLE,CODE) values (5,'Гродненское отделение Белорусской торгово-промышленной палаты','grodno','230023','Гродно','ул. Советская','23а',null,null,null,'The unitary enterprise for rendering of services "The Grodno Branch of Chamber of Commerce and Industry"','Grodno','Sovietskaya','ROLE_GRODNO','4');
Insert into BELTPP.C_OTD (ID,OTD_NAME,NAME_SYN,OTD_ADDR_INDEX,OTD_ADDR_CITY,OTD_ADDR_LINE,OTD_ADDR_BUILDING,OTD_ADDR_OFFICE,WORK_PHONE,CELL_PHONE,EOTD_NAME,EOTD_ADDR_CITY,EOTD_ADDR_LINE,ACL_ROLE,CODE) values (6,'Могилевское отделение Белорусской торгово-промышленной палаты','mogilev','212030','Могилев','улю Первомайская','71','802','+375 222 32 68 50',null,'The unitary enterprise for rendering of services "The Mogilev Branch of Chamber of Commerce and Industry"','Mogilev','Pervomayaskay','ROLE_MOGILEV','6');


--------------------------------------------------------
--  File created - четверг-Апрель-06-2017   
--------------------------------------------------------
REM INSERTING into BELTPP.FS_BRANCH_LOCALE
SET DEFINE OFF;
Insert into BELTPP.FS_BRANCH_LOCALE (IDBRANCH,LOCALE,NAME,CITY,STREET,ADDRESS) values (1,'EN','The unitary enterprise for rendering of services «Minsk Branch of the Belarusian Chamber of Commerce and Indastry»','Minsk','Ya. Kolas St.','220113, Minsk, Ya. Kolas St., 50, Republic of Belarus');
Insert into BELTPP.FS_BRANCH_LOCALE (IDBRANCH,LOCALE,NAME,CITY,STREET,ADDRESS) values (2,'EN','The unitary enterprise for rendering of services «The Brest Branch of Chamber of Commerce and Industry»','Brest','Gogol','220113, Brest, Gogol, 65, Republic of Belarus');
Insert into BELTPP.FS_BRANCH_LOCALE (IDBRANCH,LOCALE,NAME,CITY,STREET,ADDRESS) values (5,'EN','The unitary enterprise for rendering of services «The Vitebsk Branch of Chamber of Commerce and Industry»','Vitebsk','Kosmonavtov','210001, Vitebsk, Kosmonavtov, 4, Republic of Belarus');
Insert into BELTPP.FS_BRANCH_LOCALE (IDBRANCH,LOCALE,NAME,CITY,STREET,ADDRESS) values (3,'EN','The unitary enterprise for rendering of services «The Gomel Branch of Chamber of Commerce and Industry»','Gomel','Irininskay','246050, Gomel, Irininskay, 21, Republic of Belarus');
Insert into BELTPP.FS_BRANCH_LOCALE (IDBRANCH,LOCALE,NAME,CITY,STREET,ADDRESS) values (4,'EN','The unitary enterprise for rendering of services «The Grodno Branch of Chamber of Commerce and Industry»','Grodno','Sovietskaya','230023, Grodno, Sovietskaya, 23а, Republic of Belarus');
Insert into BELTPP.FS_BRANCH_LOCALE (IDBRANCH,LOCALE,NAME,CITY,STREET,ADDRESS) values (6,'EN','The unitary enterprise for rendering of services «The Mogilev Branch of Chamber of Commerce and Industry»','Mogilev','Pervomayaskay','212030, Mogilev, Pervomayaskay, 71, Republic of Belarus');


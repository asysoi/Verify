-- 1. Добавить LANGUAGE в FS_CERT

alter table FS_CERT add "LANGUAGE" VARCHAR2(2 BYTE) DEFAULT 'RU';


CREATE OR REPLACE FORCE EDITIONABLE VIEW "BELTPP"."FSCERTVIEW" ("ID", "CERTNUMBER", "PARENTNUMBER", "DATEISSUE", "DATEEXPIRY", "CONFIRMATION", "DECLARATION", "ID_BRANCH", "ID_EXPORTER", "ID_PRODUCER", "ID_EXPERT", "ID_SIGNER", "DATECERT", "CODECOUNTRYTARGET", "OTD_ID", "LISTSCOUNT", "BRANCHNAME", "BRANCHADDRESS", "EXPORTERNAME", "EXPORTERADDRESS", "PRODUCERNAME", "PRODUCERADDRESS", "EXPERTNAME", "SIGNERNAME", "JOB", "COUNTRYNAME") AS 
select a."ID",a."CERTNUMBER",a."PARENTNUMBER",a."DATEISSUE",a."DATEEXPIRY",
a."CONFIRMATION",a."DECLARATION",a."ID_BRANCH",a."ID_EXPORTER",
a."ID_PRODUCER",a."ID_EXPERT",a."ID_SIGNER",a."DATECERT",a."CODECOUNTRYTARGET",
a."OTD_ID",a."LISTSCOUNT", a."LANGUAGE" 
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

-- 2. Добавить в CCI_CLIENT

alter table CCI_CLIENT add "ENNAME" VARCHAR2(200 BYTE); 
alter table CCI_CLIENT add	"ENCITY" VARCHAR2(50 BYTE); 
alter table CCI_CLIENT add	"ENLINE" VARCHAR2(50 BYTE); 
alter table CCI_CLIENT add	"ENADDRESS" VARCHAR2(200 BYTE); 
alter table CCI_CLIENT add	"VERSION" NUMBER DEFAULT 0; 
alter table CCI_CLIENT add	"LOCKED" NUMBER(1,0) DEFAULT 0;

-- 3. Добавить в CCI_CLIENT

alter table FS_BRANCH add "ENNAME" VARCHAR2(200 BYTE); 
alter table FS_BRANCH add	"ENCITY" VARCHAR2(50 BYTE); 
alter table FS_BRANCH add	"ENLINE" VARCHAR2(50 BYTE); 
alter table FS_BRANCH add	"ENADDRESS" VARCHAR2(200 BYTE); 
alter table FS_BRANCH add	"VERSION" NUMBER DEFAULT 0; 
alter table FS_BRANCH add	"LOCKED" NUMBER(1,0) DEFAULT 0;


-- 4. Изменить триггера


  CREATE OR REPLACE EDITIONABLE TRIGGER "TRIGGER_BRANCH_ADDRESS" 
BEFORE INSERT OR UPDATE OF CINDEX,CITY,LINE,OFFICE,BUILDING ON FS_BRANCH
FOR EACH ROW
BEGIN
  :new.address := :new.cindex ||', '|| :new.CITY ||', '|| :new.LINE ||', '|| :new.BUILDING; 
  :new.enaddress := :new.cindex ||', '|| :new.ENCITY ||', '|| :new.ENLINE ||', '|| :new.BUILDING; 
END;

--------------------------------------------------------
--  DDL for Trigger TRIGGER_CLIENT_ADDRESS
--------------------------------------------------------

  CREATE OR REPLACE EDITIONABLE TRIGGER "BELTPP"."TRIGGER_CLIENT_ADDRESS" 
BEFORE INSERT OR UPDATE OF CINDEX,CITY,LINE,OFFICE,BUILDING ON CCI_CLIENT 
FOR EACH ROW
BEGIN
  :new.address := :new.cindex ||', '|| :new.CITY ||', '|| :new.LINE ||', '|| :new.BUILDING; 
  :new.enaddress := :new.cindex ||', '|| :new.ENCITY ||', '|| :new.ENLINE ||', '|| :new.BUILDING; 
END;


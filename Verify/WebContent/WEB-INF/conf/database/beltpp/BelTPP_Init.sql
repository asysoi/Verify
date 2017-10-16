REM log as sysdba

ALTER SESSION SET CONTAINER = pdborcl;

CREATE USER  BELTPP
    IDENTIFIED BY 123456;
    

GRANT CREATE SESSION TO BELTPP;
GRANT CREATE TABLE TO BELTPP;
GRANT CREATE PROCEDURE TO BELTPP;
GRANT CREATE TRIGGER TO BELTPP;
GRANT CREATE VIEW TO BELTPP;
GRANT CREATE SEQUENCE TO BELTPP;
GRANT ALTER ANY TABLE TO BELTPP;
GRANT ALTER ANY PROCEDURE TO BELTPP;
GRANT ALTER ANY TRIGGER TO BELTPP;
GRANT ALTER PROFILE TO BELTPP;
GRANT DELETE ANY TABLE TO BELTPP;  
GRANT DROP ANY TABLE TO BELTPP;
GRANT DROP ANY PROCEDURE TO BELTPP;
GRANT DROP ANY TRIGGER TO BELTPP;
GRANT DROP ANY VIEW TO BELTPP;
GRANT DROP PROFILE TO BELTPP;
GRANT CONNECT SESSION TO BELTPP;

GRANT ALTER TABLESPACE TO BELTPP;
GRANT MANAGE TABLESPACE	TO BELTPP;
GRANT UNLIMITED TABLESPACE TO BELTPP;

ALTER USER BELTPP ACCOUNT UNLOCK IDENTIFIED BY 123456;
SELECT name, pdb FROM   v$services ORDER BY name;

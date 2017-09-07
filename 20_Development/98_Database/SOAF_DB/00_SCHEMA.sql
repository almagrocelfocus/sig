DROP USER SOAF;

CREATE TABLESPACE tablespace_SOAF DATAFILE 'tablespace_soaf_01.dbf' SIZE unlimited;
    
CREATE TEMPORARY TABLESPACE temp_tablespace_SOAF  TEMPFILE 'temp_soaf_01.dbf' SIZE 5M AUTOEXTEND ON;

CREATE USER SOAF
  IDENTIFIED BY SOAF
   DEFAULT TABLESPACE tablespace_SOAF
  TEMPORARY TABLESPACE temp_tablespace_SOAF
  QUOTA unlimited on tablespace_SOAF;
 
  
GRANT create session TO SOAF;
GRANT create table TO SOAF;
GRANT create view TO SOAF;
GRANT create any trigger TO SOAF;
GRANT create any procedure TO SOAF;
GRANT create sequence TO SOAF;
GRANT create synonym TO SOAF;

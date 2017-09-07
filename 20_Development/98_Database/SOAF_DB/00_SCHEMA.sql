DROP USER CCH_SOAF;

CREATE TABLESPACE tablespace_CCH_SOAF DATAFILE 'tablespace_cch_soaf_01.dbf' SIZE unlimited;
    
CREATE TEMPORARY TABLESPACE temp_tablespace_CCH_SOAF  TEMPFILE 'temp_cch_soaf_01.dbf' SIZE 5M AUTOEXTEND ON;

CREATE USER CCH_SOAF
  IDENTIFIED BY CCH_SOAF
   DEFAULT TABLESPACE tablespace_CCH_SOAF
  TEMPORARY TABLESPACE temp_tablespace_CCH_SOAF
  QUOTA unlimited on tablespace_CCH_SOAF;
 
  
GRANT create session TO CCH_SOAF;
GRANT create table TO CCH_SOAF;
GRANT create view TO CCH_SOAF;
GRANT create any trigger TO CCH_SOAF;
GRANT create any procedure TO CCH_SOAF;
GRANT create sequence TO CCH_SOAF;
GRANT create synonym TO CCH_SOAF;

SET SERVEROUTPUT ON

BEGIN
    INSERT INTO EAI_SERVICE_CONFIGURATOR (DOMAIN, TARGET, SERVICE, VERSION) VALUES ('All', 'All', 'All', 'All');
	INSERT INTO EAI_SERVICE_CONFIGURATOR (DOMAIN, TARGET, SERVICE, VERSION) VALUES ('Provisioning', 'All', 'uploadAndProvisionBatchFile', 'All');
	INSERT INTO EAI_SERVICE_CONFIGURATOR (DOMAIN, TARGET, SERVICE, VERSION) VALUES ('Provisioning', 'All', 'uploadOrderReturnBatchFile', 'All');
	INSERT INTO EAI_SERVICE_CONFIGURATOR (DOMAIN, TARGET, SERVICE, VERSION) VALUES ('Provisioning', 'All', 'uploadSimBatchFile', 'All');
EXCEPTION
    WHEN dup_val_on_index THEN
    dbms_output.put_line('*** Exception: Dupplicate Key');
END;
/

BEGIN
	INSERT INTO EAI_SERVICE_CONFIGURATOR_PARAM VALUES ('All', 'All', 'All', 'All', 'LOG_LEVEL_SYSTEM', 'INFO', '1'); --Available levels: ALL, TRACE, DEBUG, INFO, WARN, ERROR, OFF
	INSERT INTO EAI_SERVICE_CONFIGURATOR_PARAM VALUES ('All', 'All', 'All', 'All', 'LOG_LEVEL', 'DEBUG', '2');
	INSERT INTO EAI_SERVICE_CONFIGURATOR_PARAM VALUES ('All', 'All', 'All', 'All', 'LOG_LEVEL_EVENT', 'INFO', '3');
	INSERT INTO EAI_SERVICE_CONFIGURATOR_PARAM VALUES ('Provisioning', 'All', 'uploadAndProvisionBatchFile', 'All', 'LOG_WRITE_BODY', 'false', '4');
	INSERT INTO EAI_SERVICE_CONFIGURATOR_PARAM VALUES ('Provisioning', 'All', 'uploadOrderReturnBatchFile', 'All', 'LOG_WRITE_BODY', 'false', '5');
	INSERT INTO EAI_SERVICE_CONFIGURATOR_PARAM VALUES ('Provisioning', 'All', 'uploadSimBatchFile', 'All', 'LOG_WRITE_BODY', 'false', '6');
EXCEPTION
    WHEN dup_val_on_index THEN
    dbms_output.put_line('*** Exception: Dupplicate Key');
END;
/

COMMIT
/

EXIT

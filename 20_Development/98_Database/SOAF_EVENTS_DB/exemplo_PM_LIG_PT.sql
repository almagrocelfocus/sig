create or replace PACKAGE BODY "SIG_PM_GUI" AS

-- PM GUI --

PROCEDURE SEARCH_PROCESSES(
I_PROCESS_PUBLIC_ID IN VARCHAR2,
I_PROCESS_TYPE IN VARCHAR2,
I_STATUS IN VARCHAR2,
I_PROCESS_DATE_START IN VARCHAR2,
I_PROCESS_DATE_END IN VARCHAR2,
I_CLIENT_SYSTEM IN VARCHAR2,
I_CREATED_DATE_START IN VARCHAR2,
I_CREATED_DATE_END IN VARCHAR2,
I_KEY_NAME IN VARCHAR2, 
I_KEY_VALUE IN VARCHAR2,
I_TASK_TYPE IN VARCHAR2,
I_ORDER_BY IN VARCHAR2,
I_ASC_FLAG IN VARCHAR2,
I_ROW_NUMBER IN VARCHAR2,
I_NUMBER_OF_HOURS IN VARCHAR2,
I_VF_PROCESS_TABLE IN VARCHAR2,
I_VF_TASK_TABLE IN VARCHAR2,
I_VF_TASK_KEYS_TABLE IN VARCHAR2,
O_PROCESS_LIST OUT PROCESS_LIST,
O_STATUS_CODE OUT VARCHAR2,
O_STATUS_MESSAGE OUT VARCHAR2)
AS
queryToexecute VARCHAR2(32767);
auxI_PROCESS_TYPE VARCHAR2(250);
auxI_STATUS VARCHAR2(250);
auxI_PROCESS_DATE_START VARCHAR2(250);
auxI_I_PROCESS_DATE_END VARCHAR2(250);
auxI_I_CLIENT_SYSTEM VARCHAR2(250);
auxI_I_CREATED_DATE_START VARCHAR2(250);
auxI_I_CREATED_DATE_END VARCHAR2(250);
auxI_I_KEY_NAME VARCHAR2(250);
auxI_I_KEY_VALUE VARCHAR2(250);
auxI_I_TASK_TYPE VARCHAR2(250);
auxI_I_ORDER_BY VARCHAR2(250);
auxI_I_ASC_FLAG VARCHAR2(250);

BEGIN

IF I_PROCESS_TYPE is NULL then
auxI_PROCESS_TYPE:=NVL(I_PROCESS_TYPE,'NULL');
ELSE
auxI_PROCESS_TYPE :=CONCAT(CONCAT('''',I_PROCESS_TYPE),'''');
END IF;

IF I_STATUS is NULL then
auxI_STATUS:=NVL(I_STATUS,'NULL');
ELSE
auxI_STATUS :=CONCAT(CONCAT('''',I_STATUS),'''');
END IF;

IF I_PROCESS_DATE_START is NULL then
auxI_PROCESS_DATE_START:=NVL(I_PROCESS_DATE_START,'NULL');
ELSE
auxI_PROCESS_DATE_START :=CONCAT(CONCAT('''',I_PROCESS_DATE_START),'''');
END IF;

IF I_PROCESS_DATE_END is NULL then
auxI_I_PROCESS_DATE_END:=NVL(I_PROCESS_DATE_END,'NULL');
ELSE
auxI_I_PROCESS_DATE_END :=CONCAT(CONCAT('''',I_PROCESS_DATE_END),'''');
END IF;

IF I_CLIENT_SYSTEM is NULL then
auxI_I_CLIENT_SYSTEM:=NVL(I_CLIENT_SYSTEM,'NULL');
ELSE
auxI_I_CLIENT_SYSTEM :=CONCAT(CONCAT('''',I_CLIENT_SYSTEM),'''');
END IF;

IF I_CREATED_DATE_START is NULL then
auxI_I_CREATED_DATE_START:=NVL(I_CREATED_DATE_START,'NULL');
ELSE
auxI_I_CREATED_DATE_START :=CONCAT(CONCAT('''',I_CREATED_DATE_START),'''');
END IF;

IF I_CREATED_DATE_END is NULL then
auxI_I_CREATED_DATE_END:=NVL(I_CREATED_DATE_END,'NULL');
ELSE
auxI_I_CREATED_DATE_END :=CONCAT(CONCAT('''',I_CREATED_DATE_END),'''');
END IF;

IF I_KEY_NAME is NULL then
auxI_I_KEY_NAME:=NVL(I_KEY_NAME,'NULL');
ELSE
auxI_I_KEY_NAME :=CONCAT(CONCAT('''',I_KEY_NAME),'''');
END IF;

IF I_KEY_VALUE is NULL then
auxI_I_KEY_VALUE:=NVL(I_KEY_VALUE,'NULL');
ELSE
auxI_I_KEY_VALUE :=CONCAT(CONCAT('''',I_KEY_VALUE),'''');
END IF;

IF I_TASK_TYPE is NULL then
auxI_I_TASK_TYPE:=NVL(I_TASK_TYPE,'NULL');
ELSE
auxI_I_TASK_TYPE :=CONCAT(CONCAT('''',I_TASK_TYPE),'''');
END IF;

IF I_ORDER_BY is NULL then
auxI_I_ORDER_BY:=NVL(I_ORDER_BY,'NULL');
ELSE
auxI_I_ORDER_BY :=CONCAT(CONCAT('''',I_ORDER_BY),'''');
END IF;

IF I_ASC_FLAG is NULL then
auxI_I_ASC_FLAG:=NVL(I_ASC_FLAG,'NULL');
ELSE
auxI_I_ASC_FLAG :=CONCAT(CONCAT('''',I_ASC_FLAG),'''');
END IF;

queryToexecute := 

  -- Base query to 'Process' / 'Process History' table
  'SELECT VFP.ID, VFP.PROCESS_PUBLIC_ID, VFPT.PROCESS_TYPE, VFP.XML, VFP.STATUS, VFP.MESSAGE, VFP.PROCESS_DATE, VFP.CLIENT_SYSTEM, VFP.CREATED_DATE, VFT_TK.VALUE
  FROM ' || I_VF_PROCESS_TABLE || ' VFP' ||  
  -- Join 'Process Type' table
  ' LEFT JOIN VF_PROCESS_TYPE VFPT ON VFP.PROCESS_TYPE_ID = VFPT.ID' ||  
  -- Join 'Task Type' only if passed as input
  CASE WHEN auxI_I_TASK_TYPE <> 'NULL' THEN 
    ' LEFT JOIN VF_TASK_TYPE VFTT ON VFP.PROCESS_TYPE_ID = VFTT.PROCESS_TYPE_ID'
  END ||  
  -- Join 'Task' and 'Task Keys' tables to get CustomerID value
  ' LEFT JOIN
      (SELECT MAX(VFT.ID) AS ID, VFT.PROCESS_ID AS PROCESS_ID, MAX(VFTK.NAME) AS NAME, MAX(VFTK.VALUE) AS VALUE FROM ' || I_VF_TASK_TABLE || ' VFT 
      LEFT JOIN ' || I_VF_TASK_KEYS_TABLE || ' VFTK ON VFT.ID = VFTK.TASK_ID
      WHERE VFTK.NAME = ''CustomerID''
      GROUP BY VFT.PROCESS_ID) VFT_TK 
    ON VFP.ID = VFT_TK.PROCESS_ID' ||
  
  ' WHERE' ||  
  -- 'Process' / 'Process History' filters
  ' (VFP.PROCESS_DATE > sysdate - ' || NVL(I_NUMBER_OF_HOURS,'NULL') || '/ 24.0 or ' || CONCAT(CONCAT('''',NVL(I_NUMBER_OF_HOURS,NULL)),'''') || ' is NULL)
  AND((VFP.PROCESS_PUBLIC_ID = ' || NVL(I_PROCESS_PUBLIC_ID,'NULL') || ' AND ' || NVL(I_PROCESS_PUBLIC_ID,'NULL') || ' IS NOT NULL) OR ' || NVL(I_PROCESS_PUBLIC_ID,'NULL') || ' IS NULL)
  AND((VFP.STATUS = ' || auxI_STATUS|| ' AND ' || auxI_STATUS || ' IS NOT NULL) OR ' || auxI_STATUS || ' IS NULL)
  AND((VFP.PROCESS_DATE >= TO_TIMESTAMP (' || auxI_PROCESS_DATE_START || ',''yyyy-mm-dd hh24:mi:ss'') AND ' || auxI_PROCESS_DATE_START || ' IS NOT NULL) OR ' || auxI_PROCESS_DATE_START || ' IS NULL)
  AND((VFP.PROCESS_DATE <= TO_TIMESTAMP (' || auxI_I_PROCESS_DATE_END || ',''yyyy-mm-dd hh24:mi:ss'') AND ' || auxI_I_PROCESS_DATE_END || ' IS NOT NULL) OR ' || auxI_I_PROCESS_DATE_END || ' IS NULL)
  AND((VFP.CLIENT_SYSTEM = ' || auxI_I_CLIENT_SYSTEM || ' AND ' ||auxI_I_CLIENT_SYSTEM || ' IS NOT NULL) OR ' || auxI_I_CLIENT_SYSTEM || ' IS NULL)
  AND((VFP.CREATED_DATE >= TO_TIMESTAMP (' || auxI_I_CREATED_DATE_START || ',''yyyy-mm-dd hh24:mi:ss'') AND ' || auxI_I_CREATED_DATE_START || ' IS NOT NULL) OR ' || auxI_I_CREATED_DATE_START || ' IS NULL)
  AND((VFP.CREATED_DATE <= TO_TIMESTAMP (' || auxI_I_CREATED_DATE_END || ',''yyyy-mm-dd hh24:mi:ss'') AND ' ||auxI_I_CREATED_DATE_END || ' IS NOT NULL) OR ' || auxI_I_CREATED_DATE_END || ' IS NULL) ' || 
  -- 'Process Type' filters
  'AND((VFPT.PROCESS_TYPE = ' || auxI_PROCESS_TYPE || ' AND ' || auxI_PROCESS_TYPE || ' IS NOT NULL) OR ' || auxI_PROCESS_TYPE || ' IS NULL) ' ||
  -- 'Task Type' filters, only if passed as input
  CASE WHEN auxI_I_TASK_TYPE <> 'NULL' THEN
    'AND((' || auxI_I_TASK_TYPE || ' IS NOT NULL AND VFTT.NAME = ' || auxI_I_TASK_TYPE || ') OR ' || auxI_I_TASK_TYPE || ' IS NULL) '
  END ||
  -- 'Task' and 'Task Keys' filters
  'AND(
    (' || auxI_I_KEY_NAME || ' IS NOT NULL AND ' || auxI_I_KEY_VALUE || ' IS NOT NULL AND VFT_TK.NAME = ' || auxI_I_KEY_NAME || ' AND VFT_TK.VALUE = ' || auxI_I_KEY_VALUE || ' ) OR 
    (' || auxI_I_KEY_NAME || ' IS NULL OR ' || auxI_I_KEY_VALUE || ' IS NULL))' ||
  
  -- Filter number of rows
  ' AND ROWNUM <= NVL('|| NVL(I_ROW_NUMBER,'NULL') || ',''50'')' ||  
  -- Order By
  'ORDER BY
    CASE WHEN (' || auxI_I_ORDER_BY || ' =''PROCESS_PUBLIC_ID'' AND ' || auxI_I_ASC_FLAG || '=''true'') THEN VFP.PROCESS_PUBLIC_ID END ASC,
    CASE WHEN (' || auxI_I_ORDER_BY || ' =''PROCESS_PUBLIC_ID'' AND ' || auxI_I_ASC_FLAG || '=''false'') THEN VFP.PROCESS_PUBLIC_ID END DESC,
    CASE WHEN (' || auxI_I_ORDER_BY || ' =''PROCESS_TYPE'' AND ' || auxI_I_ASC_FLAG || '=''true'') THEN VFPT.PROCESS_TYPE END ASC,
    CASE WHEN (' || auxI_I_ORDER_BY || ' =''PROCESS_TYPE'' AND ' || auxI_I_ASC_FLAG || '=''false'') THEN VFPT.PROCESS_TYPE END DESC,
    CASE WHEN (' || auxI_I_ORDER_BY || ' =''STATUS'' AND ' || auxI_I_ASC_FLAG || '=''true'') THEN VFP.STATUS END ASC,
    CASE WHEN (' || auxI_I_ORDER_BY || ' =''STATUS'' AND ' || auxI_I_ASC_FLAG || '=''false'') THEN VFP.STATUS END DESC,
    CASE WHEN (' || auxI_I_ORDER_BY || ' =''PROCESS_DATE'' AND ' || auxI_I_ASC_FLAG || '=''true'') THEN VFP.PROCESS_DATE END ASC,
    CASE WHEN (' || auxI_I_ORDER_BY || ' =''PROCESS_DATE'' AND ' || auxI_I_ASC_FLAG || '=''false'') THEN VFP.PROCESS_DATE END DESC,
    CASE WHEN (' || auxI_I_ORDER_BY || ' =''CLIENT_SYSTEM'' AND ' || auxI_I_ASC_FLAG || '=''true'') THEN VFP.CLIENT_SYSTEM END ASC,
    CASE WHEN (' || auxI_I_ORDER_BY || ' =''CLIENT_SYSTEM'' AND ' || auxI_I_ASC_FLAG || '=''false'') THEN VFP.CLIENT_SYSTEM END DESC,
    CASE WHEN (' || auxI_I_ORDER_BY || ' =''CREATED_DATE'' AND ' || auxI_I_ASC_FLAG || '=''true'') THEN VFP.CREATED_DATE END ASC,
    CASE WHEN (' || auxI_I_ORDER_BY || ' =''CREATED_DATE'' AND ' || auxI_I_ASC_FLAG || '=''false'') THEN VFP.CREATED_DATE END DESC,
    CASE WHEN (' || auxI_I_ORDER_BY || ' IS NULL) THEN VFP.PROCESS_DATE END DESC';
  
dbms_output.put_line(queryToexecute);
OPEN O_PROCESS_LIST FOR SELECT 1 FROM DUAL WHERE 1 = 2;
OPEN O_PROCESS_LIST FOR queryToexecute;

O_STATUS_CODE := 0;
O_STATUS_MESSAGE := 'SUCCESS';

EXCEPTION
  WHEN OTHERS THEN
                O_STATUS_CODE := 1;
                O_STATUS_MESSAGE := SUBSTR(SQLERRM, 1, 1000) ||CHR(10)||CHR(13)|| DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;

                
END SEARCH_PROCESSES;
-- PM GUI --
PROCEDURE SEARCH_BPM(
I_LABELSTEP IN VARCHAR2,
I_SERVERID IN VARCHAR2,
I_CUSTOMDATA IN VARCHAR2,
I_CUSTOMDATAKEY IN VARCHAR2,
I_CUSTOMID IN VARCHAR2,
I_STATUS IN VARCHAR2,
I_RETRY IN VARCHAR2,
I_MODELVERSION IN VARCHAR2,
I_PROCESSNAME IN VARCHAR2, 
I_DURATION IN VARCHAR2,
I_LASTTIME IN VARCHAR2,
I_FIRSTTIME IN VARCHAR2,
I_INSTANCEID IN VARCHAR2,
I_STEPID IN VARCHAR2,
I_ORDER_BY IN VARCHAR2,
I_ASC_FLAG IN VARCHAR2,
I_ROW_NUMBER IN VARCHAR2,
O_BPM_LIST OUT BPM_LIST,
O_STATUS_CODE OUT VARCHAR2,
O_STATUS_MESSAGE OUT VARCHAR2
)
AS
BEGIN

OPEN O_BPM_LIST FOR SELECT 1 FROM DUAL WHERE 1 = 2;

OPEN O_BPM_LIST FOR 
select /*+PARALLEL(18)*/ t.LABELSTEP,t.SERVERID,t.CUSTOMDATA,t.CUSTOMDATAKEY,t.CUSTOMID,t.STATUS,t.RETRY,t.MODELVERSION,t.PROCESSNAME,t.DURATION,t.LASTTIME,t.FIRSTTIME,t.INSTANCEID,t.STEPID
from (
select /*+PARALLEL(18)*/ LABELSTEP,
SERVERID,
CUSTOMDATA,
CUSTOMDATAKEY,
CUSTOMID,
STATUS,
RETRY,
MODELVERSION,
PROCESSNAME,
((EXTRACT (DAY from DURATION)) || 'd ' ||(EXTRACT (DAY from DURATION)) || ':' || (EXTRACT (MINUTE from DURATION)) || ':' || (EXTRACT (SECOND from DURATION))) as DURATION,
to_char(LASTTIME,'yyyy/MM/dd hh24:mi')as LASTTIME,
to_char(FIRSTTIME,'yyyy/MM/dd hh24:mi')as FIRSTTIME,
--LASTTIME as LASTTIME,
--FIRSTTIME as FIRSTTIME,
--FIRST_VALUE(INSTANCEID) over (PARTITION by CUSTOMDATA),
INSTANCEID,
STEPID,
ROW_NUMBER() OVER (PARTITION BY INSTANCEID ORDER BY INSTANCEID) as rnum
from BPM_INSTANCE_INFORMATION where
(
((LABELSTEP=I_LABELSTEP and LABELSTEP is not null) or I_LABELSTEP is null) and
((SERVERID=I_SERVERID and SERVERID is not null) or I_SERVERID is null) and
((CUSTOMDATA=I_CUSTOMDATA and I_CUSTOMDATA is not null) or I_CUSTOMDATA is null) and
((upper(CUSTOMDATAKEY)=upper(I_CUSTOMDATAKEY) and CUSTOMDATAKEY is not null) or I_CUSTOMDATAKEY is null) and
((CUSTOMID=I_CUSTOMID and I_CUSTOMID is not null) or I_CUSTOMID is null) and
((upper(STATUS)=upper(I_STATUS) and STATUS is not null) or I_STATUS is null) and
((RETRY=I_RETRY and RETRY is not null) or I_RETRY is null) and
((MODELVERSION=I_MODELVERSION and MODELVERSION is not null) or I_MODELVERSION is null) and
((PROCESSNAME=I_PROCESSNAME and PROCESSNAME is not null) or I_PROCESSNAME is null) and
((DURATION=I_DURATION and DURATION is not null) or I_DURATION is null) and
((to_char(LASTTIME,'yyyy/MM/dd HH:mm')<I_LASTTIME and LASTTIME is not null) or I_LASTTIME is null) and
((to_char(FIRSTTIME,'yyyy/MM/dd HH:mm')>I_FIRSTTIME and FIRSTTIME is not null) or I_FIRSTTIME is null) and
((INSTANCEID=I_INSTANCEID and INSTANCEID is not null) or I_INSTANCEID is null) and
((STEPID=I_STEPID and STEPID is not null) or I_STEPID is null)
and ROWNUM <= NVL(NVL(I_ROW_NUMBER,NULL),10)

 )
ORDER BY
  CASE WHEN (I_ORDER_BY  ='firsttime' AND I_ASC_FLAG ='true') THEN FIRSTTIME END ASC,
  CASE WHEN (I_ORDER_BY  ='firsttime' AND I_ASC_FLAG ='false') THEN FIRSTTIME END DESC,
  CASE WHEN (I_ORDER_BY  ='lasttime' AND I_ASC_FLAG ='true') THEN LASTTIME END ASC,
  CASE WHEN (I_ORDER_BY  ='lasttime' AND I_ASC_FLAG ='false') THEN LASTTIME END DESC,
  CASE WHEN (I_ORDER_BY is null ) THEN FIRSTTIME END ASC
) t where t.rnum=1
;
  O_STATUS_CODE := 0;
  O_STATUS_MESSAGE := 'SUCCESS';
EXCEPTION
  WHEN OTHERS THEN
                O_STATUS_CODE := 1;
                O_STATUS_MESSAGE := SUBSTR(SQLERRM, 1, 1000) ||CHR(10)||CHR(13)|| DBMS_UTILITY.FORMAT_ERROR_BACKTRACE;

END SEARCH_BPM;



END SIG_PM_GUI;

RUBEN SOUSA
OM & INTEGRATION CONSULTANT 
Mobile: (+351) 918065120
Email: ruben.sousa@celfocus.com
 


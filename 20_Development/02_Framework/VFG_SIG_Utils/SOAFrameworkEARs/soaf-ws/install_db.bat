@echo off

if "%~1" == "" (
    echo install_db.bat user/password@db_service [rollback]
    goto :EOF
)

if exist script.log (
	del /s script.log
) else (
	copy /y nul script.log
)

if "%~2" == "rollback" (
	for /r %%i in (*-db-data_0_1_0_rollback.sql) do @call :ConcatFile "%%i" %~1
	for /r %%i in (*-db_0_1_0_rollback.sql) do @call :ConcatFile "%%i" %~1
	goto :EOF
) else (
	for /r %%i in (*-db_0_1_0.sql) do @call :ConcatFile "%%i" %~1
	for /r %%i in (*-db-data_0_1_0.sql) do @call :ConcatFile "%%i" %~1
	goto :EOF
)
 
:ConcatFile
if "" == "%1" goto :EOF
setlocal ENABLEEXTENSIONS
set FLDR=%~dp0

echo --START: %1 %NLM% >> script.log
sqlplus %2 @%1 >> script.log
echo --END: %1 %NLM% >> script.log

endlocal & goto :EOF
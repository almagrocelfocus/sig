@ECHO OFF

if exist %~dp0\env.conf (
	echo loading %~dp0\env.conf
) else (
	echo configure %~dp0\env.conf: Example can be found in %~dp0\env.conf.example
    goto :EOF
)
FOR /F "tokens=1,2 delims==" %%G IN (%~dp0\env.conf) DO (set %%G=%%H)
call "%ORACLE_MIDDLEWARE_HOME%\wlserver_10.3\common\bin\setPatchEnv.cmd"
echo Enviroment set
exit /b 0
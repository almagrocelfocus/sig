@echo off

if exist "%~dp0\set_env\ds.conf" (
	echo loading "%~dp0\set_env\ds.conf
) else (
	echo configure "%~dp0\set_env\ds.conf": Example can be found in "%~dp0\set_env\ds.conf.example"
    goto :EOF
)


call "%~dp0\set_env\set_install_env.bat"
if %ERRORLEVEL% GEQ 1 goto :EOF

call "%ORACLE_MIDDLEWARE_HOME%\wlserver_10.3\common\bin\wlst.cmd" "%~dp0\set_env\ds.py" "%~dp0\set_env\ds.conf"
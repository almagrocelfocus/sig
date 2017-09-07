@echo off

call "%~dp0\set_env\set_install_env.bat"
if %ERRORLEVEL% GEQ 1 goto :EOF

call "%ORACLE_MIDDLEWARE_HOME%\wlserver_10.3\common\bin\wlst.cmd" "%~dp0\set_env\commonWSLTLoader.py" %~dp0 app jms ds appList=%1
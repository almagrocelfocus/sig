#!/bin/sh

if [ -f  set_env/jms.conf ] ; then
	echo loading $(pwd)/set_env/jms.conf
else
	echo configure set_env/jms.conf: Example can be found in set_env/jms.conf.example
	exit 1;
fi;

. set_env/set_install_env.sh

$ORACLE_MIDDLEWARE_HOME/wlserver_10.3/common/bin/wlst.sh set_env/jms.py set_env/jms.conf

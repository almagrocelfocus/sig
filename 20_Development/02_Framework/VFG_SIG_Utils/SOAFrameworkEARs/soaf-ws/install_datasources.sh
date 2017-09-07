#!/bin/sh

if [ -f  set_env/ds.conf ] ; then
	echo loading $(pwd)/set_env/ds.conf
else
	echo configure set_env/ds.conf: Example can be found in set_env/ds.conf.example
	exit 1;
fi;

. set_env/set_install_env.sh

$ORACLE_MIDDLEWARE_HOME/wlserver_10.3/common/bin/wlst.sh set_env/ds.py set_env/ds.conf

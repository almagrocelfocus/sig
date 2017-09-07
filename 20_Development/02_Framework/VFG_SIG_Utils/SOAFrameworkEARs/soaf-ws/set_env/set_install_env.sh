#!/bin/sh

if [ ! -f set_env/env.conf ] ; then
	echo Please provide set_env/env.conf file 
	exit 1;
fi;

for r in $(cat set_env/env.conf) ; do
	export $r;
done;

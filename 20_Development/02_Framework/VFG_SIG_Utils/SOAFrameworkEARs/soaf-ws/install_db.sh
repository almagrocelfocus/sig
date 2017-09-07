#!/bin/sh

if [ $# -ne 1 ] ; then
        echo Usage: $(basename $0) user/password@db_service
        exit 1;
fi;

curdir=$(pwd)

for s in ./so??-*/so??-*-db/sql/install_db.sh 
do
	dir=$(dirname $s)
echo $dir
	script=$(basename $s)
	cd $dir
	sh $script $@
	cd $curdir
done;

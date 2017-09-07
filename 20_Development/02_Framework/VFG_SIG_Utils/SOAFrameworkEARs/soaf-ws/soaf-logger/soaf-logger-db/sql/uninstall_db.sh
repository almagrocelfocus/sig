#!/bin/sh

script=$(basename $0)
logfile=$script.log

if [ $# -ne 1 ] ; then
	echo Usage: $script user/password@db_service
	exit 1;
fi;

if [ -z $(which sqlplus) ]; then
	echo $script: Make sure the 'sqlplus' is on the execution path.
	exit 2;
fi;

if [ -f $logfile ] ; then 
	rm -f $logfile
fi;

for ddl in $(ls *-db_[0-9]*[0-9]_rollback.sql | sort -r); do
	dml=$(echo $ddl | sed 's/-db_/-db-data_/')
	if [ -f $dml ] ; then
		echo $script: ------------------------------------ >> $logfile
		echo $script: Running: $dml | tee -a $logfile
		echo $script: ------------------------------------ >> $logfile
		sqlplus $1 @$dml >> $logfile
		if [ $? -ne 0 ] ; then
			echo $script: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! >> $logfile
        		echo $script: The script execution failed! Exiting... | tee -a $logfile
			echo $script: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! >> $logfile
        		exit $?;
		fi;
	fi;

        echo $script: ------------------------------------ >> $logfile
        echo $script: Running: $ddl | tee -a $logfile
        echo $script: ------------------------------------ >> $logfile
        sqlplus $1 @$ddl >> $logfile
        if [ $? -ne 0 ] ; then
                echo $script: The script execution failed! Exiting...
                exit $?;
        fi;

done;
echo $script: ------------------------------------ >> $logfile
echo $script: Done \(log: $logfile\) | tee -a $logfile
echo $script: ------------------------------------ >> $logfile

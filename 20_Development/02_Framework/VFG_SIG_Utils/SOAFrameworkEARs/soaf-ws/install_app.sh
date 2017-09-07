#!/bin/sh

if [ -z $(which java) ] ; then 
	echo Please make sure that java is on the path.
	exit 1;
fi;

if [ -f ./set_env/set_install_env.sh ] ; then 
	. ./set_env/set_install_env.sh
fi;

export SET_ENV_DIR=$(pwd)

find_set_env() {
	while [ \( ! -f $SET_ENV_DIR/set_env/env.conf \) -a \( $(dirname $SET_ENV_DIR) != $SET_ENV_DIR \) ] ; do
		export SET_ENV_DIR=$(dirname $SET_ENV_DIR)
	done;
	echo $SET_ENV_DIR/set_env/env.conf;	
}

for r in $(cat `find_set_env`) ; do
        export $r;
done;

if [ -z $ORACLE_MIDDLEWARE_HOME ] ; then
	echo Please set the ORACLE_MIDDLEWARE_HOME variable in the set_env/env.conf file..
	exit 1;
fi;
if [ -z $WLS_DOMAIN ] ; then 
	echo Plase set the WLS_DOMAIN variable in the set_env/env.conf file.
	exit 1;
fi;
if [ -z $WLS_ADMIN_URL ] ; then 
	echo Please set the WLS_ADMIN_URL variable in the set_env/env.conf file.
	exit 1;
fi;
if [ -z $WLS_USER ] ; then 
	echo Please set the WLS_USER variable in the set_env/env.conf file
	exit 1;
fi;
if [ -z $WLS_PASSWORD ] ; then 
	echo Please set the WLS_PASSWORD variable in the set_env/env.conf file.
	exit 1;
fi;
if [ -z $APP_VERSION ] ; then
	echo Plese est the APP_VERSION variable in the set_env/env.conf file.
	exit 1;
fi;
if [ -z $APP_TARGETS ] ; then 
	echo Plese est the APP_TARGETS variable in the set_env/env.conf file.
        exit 1;
fi;


#If called by uninstall_app.sh name do undeploy rather than deploy:
if [ $(basename $0) = uninstall_app.sh ] ; then
	OP="-undeploy"
	ACTION="Undeploying"
	STATUS="undeployed"
	TAIL_OPTS=""
elif [ $(basename $0) = update_app.sh ] ; then
	OP="-deploy"
        ACTION="Updating"
        STATUS="updated"
        TAIL_OPTS="-source earName -retiretimeout 30"
else 
	OP="-deploy"
	ACTION="Deploying"
        STATUS="deployed"
	TAIL_OPTS="-source earName"
fi;

EAR_LIST=$(find . -name "*.ear")

for ear in $EAR_LIST; do
	appName=$(basename $ear | sed 's/\.ear//' | sed 's/-ear//')
	echo $ACTION $appName...
	echo;
	java -Dweblogic.RootDirectory=$ORACLE_MIDDLEWARE_HOME/user_projects/domains/$WLS_DOMAIN -cp $ORACLE_MIDDLEWARE_HOME/wlserver_10.3/server/lib/weblogic.jar weblogic.Deployer -adminurl $WLS_ADMIN_URL -username $WLS_USER -password $WLS_PASSWORD $OP -name $appName -appversion $APP_VERSION -targets $APP_TARGETS $(echo $TAIL_OPTS | sed "s[earName[$ear[");
	if [ $? -gt 0 ] ; then
		exit 2;
	else
		echo $appName $STATUS;
		echo; 
	fi;
done;

echo "All done!"

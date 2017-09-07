#!/bin/sh

if [ -f ./install.bat ]; then 
	cat ./install.bat | sed 's/call mvn/mvn/' | /bin/sh
else
	echo "$0: Sorry mate, but the 'install.bat' must exist :-("
fi;


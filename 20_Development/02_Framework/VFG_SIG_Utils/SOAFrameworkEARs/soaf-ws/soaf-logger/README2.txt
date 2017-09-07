0. Importing the project to IDE Eclipse Luna, soaf-osb-db will return a project facet error. To remove this error execute the following steps:
	. Navigate to ../soaf-logger-db/src/META-INF and move the weblogic-ejb-jar.xml to a temp folder.
	. In eclipse execute a Maven Update Project in soaf-logger-db (at this point the error should disappear).
	. Finally move again the weblogic-ejb-jar.xml to the original path (../soaf-logger-db/src/META-INF).


1. Add the following xml entry to weblogic-application.xml (can be found in soaf-logger-ear\EarContent\META-INF)

These will avoid conflicts with sl4j native log used by weblogic 12c.

<wls:prefer-application-packages>
	<wls:package-name>org.slf4j.*</wls:package-name>
	<wls:package-name>org.apache.commons.logging.*</wls:package-name>
</wls:prefer-application-packages>
<wls:prefer-application-resources>
	<wls:resource-name>org/slf4j/impl/StaticLoggerBinder.class</wls:resource-name>
</wls:prefer-application-resources>




2. Navigate to <DOMAIN_HOME>/bin and specify logback.xml location for all startup server scripts:

	WINDOWS ENVIRONMENT:
		
		startWebLogic.cmd (insert at line 82)
			set JAVA_OPTIONS=%JAVA_OPTIONS% -Dlogback.configurationFile=logback.xml
		
		startManagedWebLogic.cmd (insert before %JAVA_OPTIONS% of line 58)
			-Dlogback.configurationFile="logback.xml"
			(Full line will be: set JAVA_OPTIONS=-Dweblogic.security.SSL.trustedCAKeyStore="D:\Oracle\Middleware12c\wlserver\server\lib\cacerts" -Dlogback.configurationFile="logback.xml" %JAVA_OPTIONS%)


	LINUX ENVIRONMENT (it seems these steps can be discarded, since WebLogic can discover logback.xml)
		
		startManagedWebLogic.sh (insert at line 53)
			JAVA_OPTIONS="-Dlogback.configurationFile="logback.xml" ${JAVA_OPTIONS}"

			export JAVA_OPTIONS

		startWebLogic.sh (add to the end of line 85)
			-Dlogback.configurationFile=logback.xml
			(Full line will be: JAVA_OPTIONS="${JAVA_OPTIONS} -Djava.util.logging.manager=oracle.core.ojdl.logging.ODLLogManager -Dlogback.configurationFile=logback.xml")
			
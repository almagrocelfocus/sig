call mvn install:install-file -Dfile=ws.api_1.1.0.0.jar -DgroupId=weblogic -DartifactId=ws.api -Dversion=1.1.0.0 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=javax.persistence_1.1.0.0_2-0.jar -DgroupId=com.oracle.weblogic -DartifactId=javax.persistence -Dversion=1.1.0.0_2-0 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=ojdbc6.jar -DgroupId=com.oracle.db -DartifactId=ojdbc -Dversion=6 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=weblogic-maven-plugin.jar -DpomFile=weblogic-maven-plugin.pom.xml -DgeneratePom=true
call mvn install:install-file -Dfile=com.bea.alsb.reporting.api.jar -DgroupId=com.bea.alsb.reporting.api -DartifactId=reporting-api -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=com.bea.alsb.reporting.impl.jar -DgroupId=com.bea.alsb.reporting.impl -DartifactId=reporting-impl -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=com.bea.alsb.security.api.jar -DgroupId=com.bea.alsb.security.api -DartifactId=com_bea_alsb_security_api -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=com.bea.alsb.security.impl.jar -DgroupId=com.bea.alsb.security.impl -DartifactId=com_bea_alsb_security_impl -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=com.bea.alsb.common.jar -DgroupId=com.bea.alsb.common -DartifactId=com_bea_alsb_common -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=com.bea.alsb.platform.jar -DgroupId=com.bea.alsb.platform -DartifactId=com_bea_alsb_platform -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=soaf-framework_publisher-callback_v1.jar -DgroupId=com.telco.osb -DartifactId=soaf-framework_publisher-callback_v1 -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true
call mvn install:install-file -Dfile=soa-infra-mgmt.jar -DgroupId=oracle.soa -DartifactId=soa-infra-mgmt -Dversion=12.0 -Dpackaging=jar
call mvn install:install-file -Dfile=adflibSOAMgmtBase.jar -DgroupId=oracle.soa -DartifactId=adflibSOAMgmtBase -Dversion=12.0 -Dpackaging=jar
call mvn install:install-file -Dfile=adflibSOAMgmtMain.jar -DgroupId=oracle.soa -DartifactId=adflibSOAMgmtMain -Dversion=12.0 -Dpackaging=jar
call mvn install:install-file -Dfile=com.oracle.webservices.fabric-common-api_12.1.3.jar -DgroupId=oracle.soa -DartifactId=com.oracle.webservices.fabric-common-api_12.1.3 -Dversion=12.0 -Dpackaging=jar
call mvn install:install-file -Dfile=fabric-client.jar -DgroupId=oracle.soa -DartifactId=fabric-client -Dversion=12.0 -Dpackaging=jar
call mvn install:install-file -Dfile=fabric-ext.jar -DgroupId=oracle.soa -DartifactId=fabric-ext -Dversion=12.0 -Dpackaging=jar
call mvn install:install-file -Dfile=fabric-runtime.jar -DgroupId=oracle.soa -DartifactId=fabric-runtime -Dversion=12.0 -Dpackaging=jar
call mvn install:install-file -Dfile=jrf-api.jar -DgroupId=oracle.soa -DartifactId=jrf-api -Dversion=12.0 -Dpackaging=jar
call mvn install:install-file -Dfile=oracle.soa.fabric.jar -DgroupId=oracle.soa -DartifactId=oracle.soa.fabric -Dversion=12.0 -Dpackaging=jar
call mvn install:install-file -Dfile=oracle-soa-client-api.jar -DgroupId=oracle.soa -DartifactId=oracle-soa-client-api -Dversion=12.0 -Dpackaging=jar
call mvn install:install-file -Dfile=soa-client-stubs-was.jar -DgroupId=oracle.soa -DartifactId=soa-client-stubs-was -Dversion=12.0 -Dpackaging=jar
call mvn install:install-file -Dfile=tracking-api.jar -DgroupId=oracle.soa -DartifactId=tracking-api -Dversion=12.0 -Dpackaging=jar
call mvn install:install-file -Dfile=weblogic.jar -DgroupId=oracle.soa -DartifactId=weblogic -Dversion=12.0 -Dpackaging=jar
call mvn install:install-file -Dfile=wlthint3client.jar -DgroupId=oracle.soa -DartifactId=wlthint3client -Dversion=12.0 -Dpackaging=jar
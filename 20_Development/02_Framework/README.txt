

Create the following connection factories in jmsResources:
	-> Name: jms/soaf-osb-connection-factory | JNDI Name: jms/soaf-osb-connection-factory

Create the following distributed topics in jmsResources:
	-> Name: jms/soaf-osb-db-cache-clean-topic | JNDI Name: jms/soaf-osb-db-cache-clean-topic

Create the following distributed queues in jmsResources:
	-> Name: jms/soaf-osb-republisher-queue | JNDI Name: jms/soaf-osb-republisher-queue


Create the following datasources:
	-> Name: soaf-osb-db | JNDI Name: soaf-osb-db
	-> Name: soaf-logger-db | JNDI Name: soaf-logger-db

	
Create a new DB instance in Outbound Connection Pool of dbAdapter: Go to Deployments > DbAdapter > Configuration > Outbound Connection Pools
	--> JNDI: eis/DB/SOAF | XADataSourceName: soaf-osb-db
	

from java.io import FileInputStream
import os

logDir = os.path.join(os.path.realpath('.'), 'wlst.log')
print 'Redirect debug log to: ' + logDir
redirect(logDir, 'false')

#####################################################################################
#####																			#####
#####								COMMON										#####
#####																			#####
#####################################################################################
def loadFilePath(filePath, fileExtensionLst, allFiles):
	for file in os.listdir(filePath):
		currentFilePath = os.path.join(filePath, file)
		if os.path.isdir(currentFilePath):
			loadFilePath(currentFilePath, fileExtensionLst, allFiles)
		if os.path.isfile(currentFilePath):
			if fileExtensionLst:
				for fileExtension in fileExtensionLst:
					if fileExtension and currentFilePath.endswith(fileExtension):
						allFiles.append(currentFilePath)
	return allFiles

#####################################################################################
#####																			#####
#####								JMS											#####
#####																			#####
#####################################################################################
def loadJMSConfiguration(configurationName):
	print 'Installing JMS for configuration file: "' + configurationName + '"'
	propInputStream = FileInputStream(configurationName)
	configProps = Properties()
	configProps.load(propInputStream)

	# load applications info
	jmsServerName=configProps.get("jms.server.name")
	jmsModuleName=configProps.get("jms.module.name")
	jmsSubdeploymentName=configProps.get("jms.subdeployment.name")
	queuesNames=configProps.get("queues.names")
	topicsNames=configProps.get("topic.names")
	connectionFactoryNames=configProps.get("connection.factory.names")
	redeliveryLimit = configProps.get("jms.redeliveryLimit")

	baseDir=os.path.dirname(os.path.realpath(sys.argv[0]))
	#queues
	if queuesNames:
		for queueName in queuesNames.split(','):
			currentQueueName = queueName
			if configProps.get(queueName + ".name"):
				currentQueueName=configProps.get(queueName +  ".name")

			currentJmsModuleName = jmsModuleName
			if configProps.get(queueName + ".module"):
				currentJmsModuleName=configProps.get(queueName + ".module")
			
			currentJndiQueueName = queueName
			if configProps.get(queueName + ".jndiname"):
				currentJndiQueueName=configProps.get(queueName +  ".jndiname")
			
			currentSubDeploymentName = jmsSubdeploymentName
			if configProps.get(queueName + ".subdeployment.name"):
				currentSubDeploymentName=configProps.get(queueName +  ".subdeployment.name")
				
			currentJmsServerName = jmsServerName
			if configProps.get(queueName + ".server.name"):
				currentJmsServerName=configProps.get(queueName +  ".server.name")
				
			currentRedeliveryLimit = redeliveryLimit
			if configProps.get(queueName + ".redeliveryLimit"):
				currentRedeliveryLimit = configProps.get(queueName + ".redeliveryLimit")
				
			createJMSSubDeployment(currentJmsServerName, currentJmsModuleName, currentSubDeploymentName, false) #if exists keep it since other references may exists
			createJMSUDQ(currentSubDeploymentName, currentJmsModuleName, currentJndiQueueName, currentQueueName, true, currentRedeliveryLimit)

	#topics
	if topicsNames:
		for topicsName in topicsNames.split(','):
			currentTopicName = topicsName
			if configProps.get(topicsName + ".name"):
				currentQueueName=configProps.get(topicsName +  ".name")

			currentJmsModuleName = jmsModuleName
			if configProps.get(topicsName + ".module"):
				currentJmsModuleName=configProps.get(topicsName + ".module")
			
			currentJndiTopicName = topicsName
			if configProps.get(topicsName + ".jndiname"):
				currentJndiTopicName=configProps.get(topicsName +  ".jndiname")
			
			currentSubDeploymentName = jmsSubdeploymentName
			if configProps.get(topicsName + ".subdeployment.name"):
				currentSubDeploymentName=configProps.get(topicsName +  ".subdeployment.name")
				
			currentJmsServerName = jmsServerName
			if configProps.get(topicsName + ".server.name"):
				currentJmsServerName=configProps.get(topicsName +  ".server.name")
				
			currentRedeliveryLimit = redeliveryLimit
			if configProps.get(topicsName + ".redeliveryLimit"):
				currentRedeliveryLimit = configProps.get(topicsName + ".redeliveryLimit")
			
			createJMSSubDeployment(currentJmsServerName, currentJmsModuleName, currentSubDeploymentName, false) #if exists keep it since other references may exists
			createJMSUDT(currentSubDeploymentName, currentJmsModuleName, currentJndiTopicName, currentTopicName, true, currentRedeliveryLimit)
		
	#connectionFactory
	if connectionFactoryNames:
		for connectionFactoryName in connectionFactoryNames.split(','):
			currentConnectionFactoryName = connectionFactoryName
			if configProps.get(connectionFactoryName + ".name"):
				currentQueueName=configProps.get(connectionFactoryName +  ".name")

			currentJmsModuleName = jmsModuleName
			if configProps.get(connectionFactoryName + ".module"):
				currentJmsModuleName=configProps.get(connectionFactoryName + ".module")
			
			currentJndiConnectionFactoryName = connectionFactoryName
			if configProps.get(connectionFactoryName + ".jndiname"):
				currentJndiConnectionFactoryName=configProps.get(connectionFactoryName +  ".jndiname")
			
			currentSubDeploymentName = jmsSubdeploymentName
			if configProps.get(connectionFactoryName + ".subdeployment.name"):
				currentSubDeploymentName=configProps.get(connectionFactoryName +  ".subdeployment.name")
				
			currentJmsServerName = jmsServerName
			if configProps.get(connectionFactoryName + ".server.name"):
				currentJmsServerName=configProps.get(connectionFactoryName +  ".server.name")
				
			createJMSSubDeployment(currentJmsServerName, currentJmsModuleName, currentSubDeploymentName, false)
			createJMSConnectionFactory(currentSubDeploymentName, currentJmsModuleName, currentConnectionFactoryName, currentJndiConnectionFactoryName, true)

def existsJMSSubDeployment(jms_module_name,sub_deployment_name):
	jms_module_path = '/SystemResources/'+jms_module_name
	jms_sub_deployment_path = jms_module_path+'/SubDeployments/'
	ref = getMBean(jms_sub_deployment_path+'/'+sub_deployment_name)
	return ref != None
def deleteJMSSubDeployment(jms_module_name,sub_deployment_name):
	jms_module_path = '/SystemResources/'+jms_module_name
	cd(jms_module_path)
	delete(sub_deployment_name, 'SubDeployments')
def createJMSSubDeployment(jms_server_name, jms_module_name, sub_deployment_name, deleteIfExists):
	exists = existsJMSSubDeployment(jms_module_name,sub_deployment_name)
	if(exists):
		if(deleteIfExists):
			deleteJMSSubDeployment(jms_module_name,sub_deployment_name)
		else:
			return false
	jms_module_path = '/SystemResources/'+jms_module_name
	cd(jms_module_path)
	jms_module = getMBean(jms_module_path)
	sub_deployment = jms_module.createSubDeployment(sub_deployment_name)
	jms_server_path = '/JMSServers/' + jms_server_name
	sub_deployment.addTarget(getMBean(jms_server_path))
	print 'Installed JMS Sub Deployment: "' + sub_deployment_name + '"'
	return true

def getJMSModulePath(jms_module_name):
	jms_module_path = "/JMSSystemResources/"+jms_module_name+"/JMSResource/"+jms_module_name
	return jms_module_path


def existsJMSUDQ(jms_module_name, jms_udq_name):
	jms_module_path = getJMSModulePath(jms_module_name)
	jms_udq_path = jms_module_path+'/UniformDistributedQueues/'
	ref = getMBean(jms_udq_path+'/'+jms_udq_name)
	return ref != None
def deleteJMSUDQ(jms_module_name, jms_udq_name):
	jms_module_path = getJMSModulePath(jms_module_name)
	jms_udq_path = jms_module_path+'/UniformDistributedQueues/'
	cd(jms_module_path)
	delete(jms_udq_name, 'UniformDistributedQueues')
def createJMSUDQ(sub_deployment_name, jms_module_name, jndi, jms_udq_name, deleteIfExists, redelivery_limit):
	exists = existsJMSUDQ(jms_module_name, jms_udq_name)
	if(exists):
		if(deleteIfExists):
			deleteJMSUDQ(jms_module_name, jms_udq_name)
		else:
			print 'Skipping JMS Uniform Distributed Queue: "' + jms_udq_name + '"'
			return false
	jms_module_path = getJMSModulePath(jms_module_name)
	cd(jms_module_path)
	cmo.createUniformDistributedQueue(jms_udq_name)
	cd(jms_module_path+'/UniformDistributedQueues/'+jms_udq_name)
	cmo.setJNDIName(jndi)
	cmo.setSubDeploymentName(sub_deployment_name)
	cd(jms_module_path+'/UniformDistributedQueues/'+jms_udq_name + '/DeliveryFailureParams/' + jms_udq_name)
	cmo.setRedeliveryLimit(int(redelivery_limit))
	print 'Installed JMS Uniform Distributed Queue: "' + jms_udq_name + '"'
	return true

def existsJMSUDT(jms_module_name, jms_udt_name):
	jms_module_path = getJMSModulePath(jms_module_name)
	jms_udq_path = jms_module_path+'/UniformDistributedTopics/'
	ref = getMBean(jms_udq_path+'/'+jms_udt_name)
	return ref != None
def deleteJMSUDT(jms_module_name, jms_udt_name):
	jms_module_path = getJMSModulePath(jms_module_name)
	jms_udq_path = jms_module_path+'/UniformDistributedTopics/'
	cd(jms_module_path)
	delete(jms_udt_name, 'UniformDistributedTopics')
def createJMSUDT(sub_deployment_name, jms_module_name, jndi, jms_udt_name, deleteIfExists, redelivery_limit):
	exists = existsJMSUDT(jms_module_name, jms_udt_name)
	if(exists):
		if(deleteIfExists):
			deleteJMSUDT(jms_module_name, jms_udt_name)
		else:
			print 'Skipping JMS Uniform Distributed Topic: "' + jms_udt_name + '"'
			return false
	jms_module_path = getJMSModulePath(jms_module_name)
	cd(jms_module_path)
	cmo.createUniformDistributedTopic(jms_udt_name)
	cd(jms_module_path+'/UniformDistributedTopics/'+jms_udt_name)
	cmo.setJNDIName(jndi)
	cmo.setSubDeploymentName(sub_deployment_name)
	cd(jms_module_path+'/UniformDistributedTopics/'+jms_udt_name + '/DeliveryFailureParams/' + jms_udt_name)
	cmo.setRedeliveryLimit(int(redelivery_limit))
	print 'Installed JMS Uniform Distributed Topic: "' + jms_udt_name + '"'
	return true
	
def existsJMSConnectionFactory(jms_module_name, jms_cf_name):
	jms_module_path = getJMSModulePath(jms_module_name)
	jms_udq_path = jms_module_path+'/ConnectionFactories/'
	ref = getMBean(jms_udq_path+'/'+jms_cf_name)
	return ref != None
def deleteJMSConnectionFactory(jms_module_name, jms_cf_name):
	jms_module_path = getJMSModulePath(jms_module_name)
	jms_udq_path = jms_module_path+'/ConnectionFactories/'
	cd(jms_module_path)
	delete(jms_cf_name, 'ConnectionFactories')
def createJMSConnectionFactory(sub_deployment_name, jms_module_name, jndi, jms_cf_name, deleteIfExists):
	exists = existsJMSConnectionFactory(jms_module_name, jms_cf_name)
	if(exists):
		if(deleteIfExists):
			deleteJMSConnectionFactory(jms_module_name, jms_cf_name)
		else:
			print 'Skipping JMS Connection Factory: "' + jms_cf_name + '"'
			return false
	jms_module_path = getJMSModulePath(jms_module_name)
	cd(jms_module_path)
	cmo.createConnectionFactory(jms_cf_name)
	cd(jms_module_path+'/ConnectionFactories/'+jms_cf_name)
	cmo.setJNDIName(jndi)
	cmo.setSubDeploymentName(sub_deployment_name)
	cmo.getTransactionParams().setXAConnectionFactoryEnabled(true) #additional properties example: http://atheek.wordpress.com/2012/02/28/wlst-modular-scripts-to-createdestroy-wls-resources/
	print 'Installed JMS Connection Factory: "' + jms_cf_name + '"'
	return true

#####################################################################################
#####																			#####
#####									DS										#####
#####																			#####
#####################################################################################	
def loadDSConfiguration(configurationName):	
	print 'Installing Datasources for configuration file: "' + configurationName + '"'
	propInputStream = FileInputStream(configurationName)
	configProps = Properties()
	configProps.load(propInputStream)

	# get server properties
	applicationTarget=os.environ.get('APP_TARGETS')

	# get common properties
	dsDatabaseName=configProps.get("datasource.database.name")
	dsDriverName=configProps.get("datasource.driver.class")
	dsURL=configProps.get("datasource.url")
	dsUserName=configProps.get("datasource.username")
	dsPassword=configProps.get("datasource.password")
	dsTestQuery=configProps.get("datasource.test.query")

	dsNames=configProps.get("datasource.names")

	baseDir=os.path.dirname(os.path.realpath(sys.argv[0]))
	for dsName in dsNames.split(','):
		currentDsName = dsName
		if configProps.get(dsName + ".name"):
			currentDsName=configProps.get(dsName + ".name")

		currentDsDatabaseName = dsDatabaseName
		if configProps.get(dsName + ".database.name"):
			currentDsDatabaseName=configProps.get(dsName + ".database.name")
		
		currentDatasourceTarget = applicationTarget
		if configProps.get(dsName + ".target"):
			currentDatasourceTarget=configProps.get(dsName +  ".target")

		currentDsJNDIName = dsName
		if configProps.get(dsName +  ".jndiname"):
			currentDsJNDIName=configProps.get(dsName +  ".jndiname")

		currentDsDriverName = dsDriverName
		if configProps.get(dsName +  ".driver.class"):
			currentDsDriverName=configProps.get(dsName +  ".driver.class")

		currentDsURL = dsURL
		if configProps.get(dsName +  ".url"):
			currentDsURL=configProps.get(dsName + ".url")

		currentDsUserName = dsUserName
		if configProps.get(dsName +  ".username"):
			currentDsUserName=configProps.get(dsName +  ".username")

		currentDsPassword = dsPassword
		if configProps.get(dsName +  ".password"):
			currentDsPassword=configProps.get(dsName + ".password")

		currentDsTestQuery = dsTestQuery
		if configProps.get(dsName +  ".test.query"):
			currentDsTestQuery=configProps.get(dsName + ".test.query")
		
		cd('/')
		
		ref = getMBean('/JDBCSystemResources/' + currentDsJNDIName)
		
		if(ref != None):
			print 'Skipping Datasource: "' + currentDsJNDIName + '"'
			continue
		
		cmo.createJDBCSystemResource(currentDsJNDIName)
		cd('/JDBCSystemResources/' + currentDsJNDIName + '/JDBCResource/' + currentDsJNDIName)
		cmo.setName(currentDsName)

		cd('/JDBCSystemResources/' + currentDsJNDIName + '/JDBCResource/' + currentDsJNDIName + '/JDBCDataSourceParams/' + currentDsJNDIName )
		set('JNDINames',jarray.array([currentDsJNDIName], String))

		cd('/JDBCSystemResources/' + currentDsJNDIName + '/JDBCResource/' + currentDsJNDIName + '/JDBCDriverParams/' + currentDsJNDIName )
		cmo.setUrl(currentDsURL)
		cmo.setDriverName(currentDsDriverName)
		cmo.setPassword(currentDsPassword)

		cd('/JDBCSystemResources/' + currentDsJNDIName + '/JDBCResource/' + currentDsJNDIName + '/JDBCConnectionPoolParams/' + currentDsJNDIName )
		cmo.setTestTableName(currentDsTestQuery)
		cd('/JDBCSystemResources/' + currentDsJNDIName + '/JDBCResource/' + currentDsJNDIName + '/JDBCDriverParams/' + currentDsJNDIName + '/Properties/' + currentDsJNDIName )
		cmo.createProperty('user')

		cd('/JDBCSystemResources/' + currentDsJNDIName + '/JDBCResource/' + currentDsJNDIName + '/JDBCDriverParams/' + currentDsJNDIName + '/Properties/' + currentDsJNDIName + '/Properties/user')
		cmo.setValue(currentDsUserName)

		cd('/JDBCSystemResources/' + currentDsJNDIName + '/JDBCResource/' + currentDsJNDIName + '/JDBCDriverParams/' + currentDsJNDIName + '/Properties/' + currentDsJNDIName )
		cmo.createProperty('databaseName')

		cd('/JDBCSystemResources/' + currentDsJNDIName + '/JDBCResource/' + currentDsJNDIName + '/JDBCDriverParams/' + currentDsJNDIName + '/Properties/' + currentDsJNDIName + '/Properties/databaseName')
		cmo.setValue(currentDsDatabaseName)

		cd('/JDBCSystemResources/' + currentDsJNDIName + '/JDBCResource/' + currentDsJNDIName + '/JDBCDataSourceParams/' + currentDsJNDIName )
		cmo.setGlobalTransactionsProtocol('OnePhaseCommit')

		cd('/SystemResources/' + currentDsJNDIName )
		set('Targets',jarray.array([ObjectName('com.bea:Name=' + currentDatasourceTarget + ',Type=Server')], ObjectName))
		
		print 'Installed Datasource: "' + currentDsName + '"'

#####################################################################################
#####																			#####
#####									MULTI DS										#####
#####																			#####
#####################################################################################	
def loadMDSConfiguration(configurationName):	
	print 'Installing Multi-Datasources for configuration file: "' + configurationName + '"'
	propInputStream = FileInputStream(configurationName)
	configProps = Properties()
	configProps.load(propInputStream)

	# get server properties
	applicationTarget=os.environ.get('APP_TARGETS')

	# get common properties

	mdsNames=configProps.get("mds.names")
	
	if(mdsNames):
		mdsAlgorithm=configProps.get("mds.algorithm")
		physicalDsName=configProps.get("mds.physical.ds.name")

		baseDir=os.path.dirname(os.path.realpath(sys.argv[0]))
		for dsName in mdsNames.split(','):
			currentDsName = dsName

			if configProps.get(dsName + ".mds.name"):
				currentDsName=configProps.get(dsName + ".mds.name")

			currentDatasourceTarget = applicationTarget
			if configProps.get(dsName + ".mds.target"):
				currentDatasourceTarget=configProps.get(dsName +  ".mds.target")

			currentDsJNDIName = dsName
			if configProps.get(dsName +  ".mds.jndiname"):
				currentDsJNDIName=configProps.get(dsName +  ".mds.jndiname")

			currentPhysicalDsname = physicalDsName
			if configProps.get(dsName +  ".mds.physical.ds.name"):
				currentPhysicalDsname=configProps.get(dsName + ".mds.physical.ds.name")
					
			cd('/')
			
			ref = getMBean('/JDBCSystemResources/' + currentDsJNDIName)
			if(ref != None):
				print 'Skipping Datasource: "' + currentDsJNDIName + '"'
				continue
			
			cmo.createJDBCSystemResource(currentDsJNDIName)

			cd('/JDBCSystemResources/'+currentDsJNDIName+'/JDBCResource/' + currentDsJNDIName)
			
			cmo.setName(currentDsName)

			cd('/JDBCSystemResources/'+currentDsJNDIName+'/JDBCResource/'+currentDsJNDIName+'/JDBCDataSourceParams/'+currentDsJNDIName)

			# sets the JNDI name of the multi datasource to currentDsJNDIName variable value
			set('JNDINames',jarray.array([String(currentDsJNDIName)], String))

			cmo.setAlgorithmType(mdsAlgorithm)

			cmo.setDataSourceList(physicalDsName)
			cd('/JDBCSystemResources/' + currentDsJNDIName)

			cd('/SystemResources/' + currentDsJNDIName )
			set('Targets',jarray.array([ObjectName('com.bea:Name=' + currentDatasourceTarget + ',Type=Server')], ObjectName))
			
			print 'Installed multi-Datasource: "' + currentDsName + '"'		
		
#####################################################################################
#####																			#####
#####								APPLICATION									#####
#####																			#####
#####################################################################################
def loadWLSApp(configurationName, searchDir, appList):	
	print 'Installing EAR Application for configuration file: "' + configurationName + '" in bease directory: "' + searchDir + "'"
	propInputStream = FileInputStream(configurationName)
	configProps = Properties()
	configProps.load(propInputStream)

	# get server properties
	applicationTarget=os.environ.get('APP_TARGETS')

	# load applications info
	applicationNames=configProps.get("application.names")
	for applicationName in applicationNames.split(','):
		currentDeployName = applicationName
		if configProps.get(applicationName + ".name"):
			currentDeployName = configProps.get(applicationName + ".name")
		
		currentTargetName = applicationTarget
		if configProps.get(applicationName + ".target"):
			currentTargetName = configProps.get(applicationName +  ".target")
		
		if configProps.get(applicationName + ".file.path"):
			currentEARFilePathLst = [configProps.get(applicationName +  ".file.path")]
		else:
			currentEARFilePathLst = loadFilePath(searchDir, [applicationName+'.ear',applicationName+'-ear.ear'], [])
		
		if currentEARFilePathLst:
			for currentEARFilePath in currentEARFilePathLst:
				if appList != "":
					for app in appList.split(','):
						if currentDeployName.startswith(app):
							deploy(currentDeployName,currentEARFilePath,targets=currentTargetName)
							print 'Installed EAR Application: "' + currentDeployName + '"'
				else:
					deploy(currentDeployName,currentEARFilePath,targets=currentTargetName)
					print 'Installed filtered EAR Application: "' + currentDeployName + '"'
# get server properties
domainName=os.environ.get('ORACLE_MIDDLEWARE_HOME')
adminURL=os.environ.get('WLS_ADMIN_URL')
adminUserName=os.environ.get('WLS_USER')
adminPassword=os.environ.get('WLS_PASSWORD')
applicationTarget=os.environ.get('APP_TARGETS')

print 'Connecting to: "' + adminURL + '"'
connect(adminUserName, adminPassword, adminURL)
edit()
startEdit()
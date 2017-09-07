import os

envproperty=""
if (len(sys.argv) > 1):
	envproperty=sys.argv[1]
else:
	print "Environment property file and current directory not specified"
	sys.exit(2)

baseDir=os.path.dirname(os.path.realpath(sys.argv[0]))
searchDir= baseDir + '/../'
execfile(baseDir+'/common.py')
execfile(baseDir+'/connect.py')
if (len(sys.argv) > 2):
	for i in range(2, len(sys.argv)):
		loadWLSApp(envproperty, searchDir, sys.argv[i])
else:
	loadWLSApp(envproperty, searchDir, "")
execfile(baseDir+'/disconnect.py')
import os

envproperty=""
if (len(sys.argv) > 1):
	envproperty=sys.argv[1]
else:
	print "Environment property file and current directory not specified"
	sys.exit(2)

baseDir=os.path.dirname(os.path.realpath(sys.argv[0]))
execfile(baseDir+'/common.py')
execfile(baseDir+'/connect.py')
loadDSConfiguration(envproperty)
loadMDSConfiguration(envproperty)
execfile(baseDir+'/disconnect.py')
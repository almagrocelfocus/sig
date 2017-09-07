import os
from java.io import FileInputStream



externalBaseDir = ''
if (len(sys.argv) <= 2):
	print 'Usage: commonWSLTLoader <basedir> <app|jms|ds>*'
	sys.exit(2)
else:
	externalBaseDir = sys.argv[1]

baseDir=os.path.dirname(os.path.realpath(sys.argv[0]))
execfile(baseDir+'/common.py')
execfile(baseDir+'/connect.py')
for action in sys.argv:
	if action == 'app':
		config = loadFilePath(externalBaseDir, [action+'.conf'], [])
		if config:
			for confFile in config:
				loadWLSApp(confFile, externalBaseDir, "")
	if action == 'ds':
		config = loadFilePath(externalBaseDir, [action+'.conf'], [])
		if config:
			for confFile in config:
				loadDSConfiguration(confFile)
	if action == 'jms':
		config = loadFilePath(externalBaseDir, [action+'.conf'], [])
		if config:
			for confFile in config:
				loadJMSConfiguration(confFile)
execfile(baseDir+'/disconnect.py')
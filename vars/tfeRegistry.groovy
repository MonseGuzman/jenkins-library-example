def call() {
	sh '''
		ls 
		pwd
		ls scripts
	'''
	sh '''
		chmod +x scripts/tfe-private-module.sh
		sh ./scripts/tfe-private-module.sh
	'''
}
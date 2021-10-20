def call() {
	
linux 'Terratest'

dir('test'){
	sh '''
		echo "####[command] Create backend"
		
		chmod +x scripts/override-backend.sh
		chmod +x scripts/override-backend.sh
	'''

	sh '''
		echo "####[command] Terratest"

		chmod +x scripts/terratest.sh
		sh ./scripts/terratest.sh
	'''
}
				
}
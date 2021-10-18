def call() {
	sh '''
		ls 
		ls scripts
		echo "terratestTimeout - $terratestTimeout"
	'''
	sh '''
		chmod +x scripts/terratest.sh
		echo "Aquí deberían correr los tests"

		// sh ./scripts/terratest.sh
	'''
}
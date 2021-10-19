// import org.monseExample
def call() {
	sh '''
		ls 
		ls scripts
		echo "terratestTimeout - $terratestTimeout"

		export TFE_WORKSPACE="terratest-${BUILD_NUMBER}" > myenv
		echo $TFE_WORKSPACE
	'''
    stash 'myenv'
	unstash 'myenv'
	// sh '''
	// 	chmod +x scripts/create-tfe-workspace.sh
		
	// 	sh ./scripts/create-tfe-workspace.sh
	// '''
	sh '''
		chmod +x scripts/terratest.sh
		echo "Aquí deberían correr los tests"
		
		source myenv
		echo $TFE_WORKSPACE
	'''
	// echo GlobalVars.TFE_WORKSPACE
	// sh '''
	// 	chmod +x scripts/delete-tfe-workspace.sh
		
	// 	sh ./scripts/delete-tfe-workspace.sh
	// '''
	// sh ./scripts/terratest.sh
}
// import org.monseExample
def call() {
	sh '''
		ls 
		ls scripts
		echo "terratestTimeout - $terratestTimeout"

		export TFE_WORKSPACE="terratest-${BUILD_NUMBER}" > myenv
		echo $TFE_WORKSPACE
	'''
    // stash 'myenv'
	// sh '''
	// 	chmod +x scripts/create-tfe-workspace.sh
		
	// 	sh ./scripts/create-tfe-workspace.sh
	// '''
	// unstash 'myenv'
	sh '''
		source myenv

		chmod +x scripts/terratest.sh
		echo "Aquí deberían correr los tests"
		
		echo $TFE_WORKSPACE
	'''
	// echo GlobalVars.TFE_WORKSPACE
	// sh '''
	// 	chmod +x scripts/delete-tfe-workspace.sh
		
	// 	sh ./scripts/delete-tfe-workspace.sh
	// '''
	// sh ./scripts/terratest.sh
}
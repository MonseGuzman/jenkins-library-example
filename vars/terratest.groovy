import org.monseExample

def call() {
	sh '''
		ls 
		ls scripts
		echo "terratestTimeout - $terratestTimeout"

		echo GlobalVars.TFE_WORKSPACE
	'''
	// sh '''
	// 	chmod +x scripts/create-tfe-workspace.sh
		
	// 	sh ./scripts/create-tfe-workspace.sh
	// '''
	sh '''
		chmod +x scripts/terratest.sh
		echo "Aquí deberían correr los tests"

		echo GlobalVars.TFE_WORKSPACE

	'''
	// sh '''
	// 	chmod +x scripts/delete-tfe-workspace.sh
		
	// 	sh ./scripts/delete-tfe-workspace.sh
	// '''
	// sh ./scripts/terratest.sh
}
// import org.monseExample
def workspacesName = sh script: 'terratest-${BUILD_NUMBER}', returnStdout: true

def call() {
	sh '''
		ls 
		ls scripts
		echo "terratestTimeout - $terratestTimeout"

		export TFE_WORKSPACE = ${workspacesName}
		echo $TFE_WORKSPACE
	'''
	// sh '''
	// 	chmod +x scripts/create-tfe-workspace.sh
		
	// 	sh ./scripts/create-tfe-workspace.sh
	// '''
	sh '''
		chmod +x scripts/terratest.sh
		echo "Aquí deberían correr los tests"
		
		export TFE_WORKSPACE = ${workspacesName}
		echo $TFE_WORKSPACE
	'''
	// echo GlobalVars.TFE_WORKSPACE
	// sh '''
	// 	chmod +x scripts/delete-tfe-workspace.sh
		
	// 	sh ./scripts/delete-tfe-workspace.sh
	// '''
	// sh ./scripts/terratest.sh
}
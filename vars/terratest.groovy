def call() {
	
stages {
	stage('setup') {
		steps {
			linux 'setup'

			script{
				env.TFE_WORKSPACE = sh(script: "eval terratest-${BUILD_NUMBER}", returnStdout: true).trim()
			}
			
			example()

			sh '''
				ls 
				echo "########## LS FOR SCRIPTS"
				ls scripts
				echo "########## Shout Out to my ex"
				echo "$TFE_WORKSPACE"
				echo "$terratestTimeout"
				echo "$BRANCH_NAME"
			'''
		}
	}
	stage('Terratest') {
		steps {
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
	}
		
}
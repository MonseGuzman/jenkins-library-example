def call() {
	pipeline {
		agent { docker 'kaarla/terraform-terratest' }
		environment {
			AWS_ACCESS_KEY_ID="${AWS_KEY_ID}"
			AWS_SECRET_ACCESS_KEY="${AWS_SECRET_KEY}"
			AWS_SESSION_TOKEN="${AWS_TOKEN}"
			TFE_TOKEN="${TFE_TOKEN}"
		}
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
		post {
			always {
				echo "Cleaning up Workspace"
				deleteDir() /* clean up our workspace */
			}
		}
	}
}
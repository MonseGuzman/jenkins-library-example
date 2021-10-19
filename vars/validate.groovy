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
					
					dir("scripts"){
						git branch: 'main',
							credentialsId: 'global',
							url: 'https://github.com/MonseGuzman/jenkins-library-example.git'
					}

					sh '''
						ls 
						echo "LS FOR SCRIPTS"
						ls scripts
					'''
				}
			}
			stage('validate') {
				steps {
					linux 'validate'

					sh '''
						ls 
						echo "LS FOR SCRIPTS"
						ls scripts
					'''
					
					sh '''
						echo "####[command] Terraform Validate"
						chmod +x scripts/terraform-validate.sh

						sh ./scripts/terraform-validate.sh
					'''
				}
			}
			stage('tflint') {
				steps {
					linux 'tflint'

					sh '''
						ls 
						echo "LS FOR SCRIPTS"
						ls scripts
					'''
					
					sh '''
						echo "####[command] Terraform TFLint"
						chmod +x scripts/terraform-tflint.sh

						export tflint_config="scripts/tflint/.tflint.hcl"
						echo $tflint_config
						sh ./scripts/terraform-tflint.sh
					'''
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
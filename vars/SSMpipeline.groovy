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
						env.GIT_AUTHOR = sh(script: "eval git --no-pager show -s --format=\'%an\'", returnStdout: true).trim()
					}
					
					example()
				}
			}
			stage('validate') {
				steps {
					linux 'validate'

					validate()
				}
			}
			stage('terratest') {
				steps {
					linux 'terratest'

					script{
						env.TFE_WORKSPACE = sh(script: "eval echo 'terratest-$BUILD_ID'", returnStdout: true).trim()
						env.GIT_REPO_NAME = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
					}

					sh 'echo $GIT_REPO_NAME'
					sh 'echo $TFE_WORKSPACE'
					// terratest()
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
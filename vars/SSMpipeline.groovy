def call() {
	
	pipeline {
		agent { docker 'monserratguzman/terratest' }
		environment {
			AWS_ACCESS_KEY_ID="${AWS_KEY_ID}"
			AWS_SECRET_ACCESS_KEY="${AWS_SECRET_KEY}"
			AWS_SESSION_TOKEN="${AWS_TOKEN}"
			TFE_TOKEN="${TFE_TOKEN}"
		}
		parameters {
			choice(choices: ['True', 'False'], description: 'Allow for skipping `terraform validate` stage', name: 'SKIP_TF_VALIDATE')
			string(defaultValue: "", description: 'a description of string empty', name: 'TF_TARGET')
		}
		stages {
			stage('setup') {
				steps {
					linux 'setup'

					script{
						env.replace = env.WORKSPACE.replaceAll( '/', '-' )
						env.GIT_AUTHOR = sh(script: "eval git log -1 --pretty=format:'%an'", returnStdout: true).trim()
						env.GIT_REPO_NAME = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
						env.TFE_WORKSPACE = sh(script: "eval echo 'terratest-$BUILD_ID-$GIT_REPO_NAME'", returnStdout: true).trim()
						env.BRANCH_NAME = "${GIT_BRANCH.split("origin/")[1]}"
					}

					example()
					
					sh '''
						echo "GIT_AUTHOR"
						echo "${replace}"
						ls
						ls scripts
					'''

					sh 'printenv'
				}
			}
			stage('try-catch') {
				when { expression { env.TF_TARGET != '' } }
				steps {
					loadDwarfconfig()

					sh """
						echo "flag: ${SKIP_TF_VALIDATE}"
						echo "TF_TARGET: ${TF_TARGET}"
					"""

					sh """
						if [[ -f "scripts/terratest.sh" ]]; then
							echo "it not specified... exiting"
							exit 1
						fi
					"""
				}
			}
			stage('validate') {
				when { expression {env.SKIP_TF_VALIDATE == 'False'} }
				steps {
					linux 'validate'

					validate()
				}
			}
			stage('terratest') {
				steps {
					linux 'terratest'

					sh """
						cat <<EOF > ".terraformrc"
						{
							"credentials": {
								"app.terraform.io": = {
									"token": "$TFE_TOKEN"
								}
							}
						}
						EOF
					"""
					// terratest()
				}
			}
			stage('destroy'){
				steps {
					sh '''
						chmod +x scripts/terraform-destroy.sh scripts/check-empty-var.sh
						source scripts/terraform-destroy.sh ${TFE_TOKEN}

						sh ./scripts/check-empty-var.sh "ARM_TENANT_ID"
					'''
				}
			}
			stage('publish'){
				when { expression { env.SKIP_TF_VALIDATE == 'False' } }
				steps{
					linux 'publish'
					
					tfeRegistry()
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
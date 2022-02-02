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
		}
		stages {
			stage('setup') {
				steps {
					linux 'setup'

					script{
						env.GIT_AUTHOR = sh(script: "eval git log -1 --pretty=format:'%an'", returnStdout: true).trim()
						env.GIT_REPO_NAME = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
						env.TFE_WORKSPACE = sh(script: "eval echo 'terratest-$BUILD_ID-$GIT_REPO_NAME'", returnStdout: true).trim()
						env.BRANCH_NAME = "${GIT_BRANCH.split("origin/")[1]}"
					}

					example()
					
					sh '''
						echo "GIT_AUTHOR"
						echo "${GIT_AUTHOR}"
					'''

					sh 'printenv'
				}
			}
			stage('try-catch') {
				steps {
					loadDwarfconfig()

					sh """
						echo "flag: ${SKIP_TF_VALIDATE}"
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

					sh '''
						cat <<EOF > ".terraformrc"
						{
							"credentials": {
								"app.terraform.io": = {
									"token": "$TFE_TOKEN"
								}
							}
						}
						EOF
					'''
					// terratest()
				}
			}
			stage('destroy'){
				when { expression { env.SKIP_TF_VALIDATE == 'False' } }
				steps {
					sh 'exit 1'
				}
				post { // works without catcherror
					failure {
						destroy 'delete'
					}
				}
			}
			stage('publish'){
				// when {
				// 	branch 'master'
				// }
				steps{
					linux 'publish'
					
					tfeRegistry()
				}
				post { // works without catcherror
					failure {
						destroy()
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
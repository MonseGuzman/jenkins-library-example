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
					script{
						env.MAX_RUN_TIME = ${currentBuild.durationString}.replace(' and counting', '')}
					}
					sh """
						echo "${MAX_RUN_TIME}"
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
				// when { expression { env.SKIP_TF_VALIDATE == 'False' } }
				steps {
					// sh 'exit 1'
					destroy 'delete'
					destroy 'create'
					destroy()
				}
			}
			stage('publish'){
				// when { expression { env.SKIP_TF_VALIDATE == 'False' } }
				steps{
					linux 'publish'
					
					tfeRegistry()
				}
				// post { // works without catcherror
				// 	failure {
				// 		destroy 'create'
				// 	}
				// }
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
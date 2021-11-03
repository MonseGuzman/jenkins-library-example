def call() {
	
	pipeline {
		agent { docker 'monserratguzman/terratest' }
		environment {
			AWS_ACCESS_KEY_ID="${AWS_KEY_ID}"
			AWS_SECRET_ACCESS_KEY="${AWS_SECRET_KEY}"
			AWS_SESSION_TOKEN="${AWS_TOKEN}"
			TFE_TOKEN="${TFE_TOKEN}"
			TF_DESTROY="TRUE"
		}
		stages {
			stage('setup') {
				steps {
					linux 'setup'

					script{
						env.GIT_AUTHOR = sh(script: "eval git --no-pager show -s --format=\'%an\'", returnStdout: true).trim()
					}

					example()
					
					sh '''
						echo "########## LS"
						ls 
						echo "########## LS FOR SCRIPTS"
						ls scripts
					'''
				}
			}
			stage('validate') {
				when {
					branch 'master'
				}
				steps {
					linux 'validate'

					validate()
				}
			}
			stage('export dwarf vars'){
				steps {
					loadDwarfconfig()
					// script {
					// 	def test = sh(script: '''
					// 		source scripts/export.sh
        			// 		echo \$TERRAFORM_DESTROY
					// 	''', returnStdout: true).trim()
					// }

					sh 'printenv | sort'
				}
			}
			stage('destroy'){
				// when { expression { env.TF_DESTROY == 'TRUE' } }
				steps {
					sh 'echo ${env.TF_DESTROY}'
					// terraformDestroy()
				}
			}
			stage('terratest') {
				when {
					branch 'master'
				}
				steps {
					linux 'terratest'

					script{
						// def BRANCH = sh(returnStdout: true, script: 'git rev-parse --abbrev-ref HEAD').trim()
						env.GIT_REPO_NAME = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
						env.TFE_WORKSPACE = sh(script: "eval echo 'terratest-$BUILD_ID-$GIT_REPO_NAME'", returnStdout: true).trim()
						env.BRANCH_NAME = "${GIT_BRANCH.split("origin/")[1]}"
					}

					// sh 'printenv'
					sh 'echo $GIT_REPO_NAME'
					sh 'echo $TFE_WORKSPACE'
					sh 'echo $BRANCH_NAME'
					// terratest()

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
				}
			}
			stage('publish'){
				when {
					branch 'master'
				}
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
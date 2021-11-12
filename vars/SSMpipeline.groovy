def call() {
	
	pipeline {
		agent { docker 'monserratguzman/terratest' }
		environment {
			AWS_ACCESS_KEY_ID="${AWS_KEY_ID}"
			AWS_SECRET_ACCESS_KEY="${AWS_SECRET_KEY}"
			AWS_SESSION_TOKEN="${AWS_TOKEN}"
			// TFE_TOKEN="${TFE_TOKEN}"
			TFE_TOKEN="kjgjkldjklehkalurk49875u2y263gskq2"
		}
		stages {
			stage('setup') {
				steps {
					linux 'setup'

					script{
						env.GIT_AUTHOR = sh(script: "eval git --no-pager show -s --format=\'%an\'", returnStdout: true).trim()
						env.GIT_REPO_NAME = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
						env.TFE_WORKSPACE = sh(script: "eval echo 'terratest-$BUILD_ID-$GIT_REPO_NAME'", returnStdout: true).trim()
						env.BRANCH_NAME = "${GIT_BRANCH.split("origin/")[1]}"
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
			// stage('validate') {
			// 	steps {
			// 		linux 'validate'

			// 		validate()
			// 	}
			// }
			// stage('export dwarf vars'){
			// 	steps {
			// 		sh 'chmod +x scripts/export.sh'
			// 		script{
			// 			env.TF_DESTROY=sh(script: "eval echo `sh ./scripts/export.sh TERRAFORM_DESTROY`", returnStdout: true).trim()
			// 		}

			// 		sh 'echo "heyyyyyy"'
			// 		sh 'echo $TF_DESTROY'

			// 		// sh 'printenv | sort'
			// 	}
			// }
			// stage('destroy'){
			// 	when { expression { env.TF_DESTROY == 'TRUE' } }
			// 	steps {
			// 		sh 'echo "heyyyyyy x2"'
			// 		sh 'echo $TF_DESTROY'
			// 		terraformDestroy()
			// 	}
			// }
			stage('terratest') {
				// when {
				// 	branch 'master'
				// }
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
					
					sh 'printenv'
					// terratest()
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
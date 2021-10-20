def call() {				
example()

sh '''
	ls 
	echo "########## LS FOR SCRIPTS"
	ls scripts
	echo "########## Mr Perfectly Fine"
	echo "$GIT_AUTHOR"
	echo "$SKIP_TF_VALIDATE"
	echo "$BRANCH_NAME"
'''

linux 'validate'

sh '''
	echo "$GIT_AUTHOR"
	echo "$BUILD_ID" # 11
	echo "$BUILD_NUMBER" # 11
	echo "$BUILD_TAG" # jenkins-test-library-waas-11 ($BUILD_ID)
	echo "$BUILD_URL" # http://localhost:8080/job/test-library-waas/11/
	echo "$EXECUTOR_NUMBER" # 1
	echo "$JAVA_HOME"
	echo "$JENKINS_URL" # http://localhost:8080/
	echo "$JOB_NAME" # test-library-waas
	echo "$NODE_NAME" # master
	echo "$WORKSPACE" # /Users/monseguzman/.jenkins/workspace/test-library-waas
'''

sh '''
	echo "####[command] Terraform Validate"
	chmod +x scripts/terraform-validate.sh

	sh ./scripts/terraform-validate.sh
'''

linux 'tflint'

sh '''
	echo "####[command] Terraform TFLint"
	chmod +x scripts/terraform-tflint.sh

	export tflint_config="scripts/.tflint.hcl"
	echo $tflint_config
	sh ./scripts/terraform-tflint.sh
'''

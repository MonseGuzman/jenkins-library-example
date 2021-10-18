def call() {
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
	sh '''
		echo "####[command] Terraform TFLint"
		chmod +x scripts/terraform-tflint.sh

		export tflint_config="scripts/tflint/.tflint.hcl"
		echo $tflint_config
		sh ./scripts/terraform-tflint.sh
	'''
}
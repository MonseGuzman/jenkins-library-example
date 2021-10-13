def call() {
	dir("scripts"){
		git branch: 'master',
			credentialsId: 'global',
			url: 'https://github.com/MonseGuzman/example-scripts.git'
	}
	sh '''
		ls 
		ls scripts
		pwd
	'''
	sh '''
		chmod +x scripts/terraform-validate.sh
		chmod +x scripts/terraform-tflint.sh

		sh ./scripts/terraform-validate.sh

		echo "terratestTimeout - "
		echo $terratestTimeout
	'''
}
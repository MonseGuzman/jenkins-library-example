def call() {
	sh '''
		echo "########## LS command"
		ls 
		echo "########## Pay my rent"
		echo "$GIT_AUTHOR"
		echo "$terratestTimeout"
		echo "$BRANCH_NAME"
	'''

	dir('test'){
		sh '''
			ls 
			echo "####[command] Create backend"
			
			chmod +x ../scripts/override-backend.sh
			sh ./../scripts/override-backend.sh
		'''

		sh '''
			echo "####[command] Terratest"

			chmod +x ../scripts/terratest.sh
			sh ./../scripts/terratest.sh
		'''
	}	
}
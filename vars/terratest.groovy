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
			echo "####[command] Create backend"
			
			chmod +x override-backend.sh
			sh ./override-backend.sh
		'''

		sh '''
			echo "####[command] Terratest"

			chmod +x terratest.sh
			sh ./terratest.sh
		'''
	}	
}
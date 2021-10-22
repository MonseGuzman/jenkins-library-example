def call() {
	// dir("scripts"){
	// 	git branch: 'master',
	// 		credentialsId: 'global',
	// 		url: 'https://github.com/MonseGuzman/example-scripts.git'
	// }
	dir("resources"){
		git branch: $BRANCH_NAME,
			credentialsId: 'global',
			url: 'https://github.com/MonseGuzman/jenkins-library-example.git'
	}
	sh '''
		folder=`find . -type d -name "terraform"`
		cp -r $folder scripts
	'''
}
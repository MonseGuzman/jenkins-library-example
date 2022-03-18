def call() {
	sh """
		echo "library version: ${env."library.library-example.version"}"
	"""
	
	dir("resources"){
		git branch: "${env."library.library-example.version"}",
			credentialsId: 'global',
			url: 'https://github.com/MonseGuzman/jenkins-library-example.git'
	}
	sh '''
		folder=`find . -type d -name "terraform"`
		cp -r $folder scripts
	'''
}
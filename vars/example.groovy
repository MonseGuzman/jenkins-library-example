def call() {
	sh """
		echo "library version: ${env."library.library-example.version"}"
	"""

	def SHARED_VERSION = env."library.library-example.version"

	sh """
		echo "library version: ${SHARED_VERSION}"
	"""
	
	dir("resources"){
		git branch: "${SHARED_VERSION}",
			credentialsId: 'global',
			url: 'https://github.com/MonseGuzman/jenkins-library-example.git'
	}
	sh '''
		folder=`find . -type d -name "terraform"`
		cp -r $folder scripts
	'''
}
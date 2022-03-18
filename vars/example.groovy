def call() {
	sh 'printenv'

	// def SHARED_LIBRARY_VER = env."library.library-example.version"

	sh """
		echo "library version: ${env."library.libName.version"}"
	"""
	
	dir("resources"){
		git branch: "${env."library.libName.version"}",
			credentialsId: 'global',
			url: 'https://github.com/MonseGuzman/jenkins-library-example.git'
	}
	sh '''
		folder=`find . -type d -name "terraform"`
		cp -r $folder scripts
	'''
}
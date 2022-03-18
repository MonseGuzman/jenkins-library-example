def call() {
	sh 'printenv'

	// def SHARED_LIBRARY_VER = env."library.library-example.version"

	sh """
		echo ${SHARED_LIBRARY_VER}
		echo ${library.library-example.version}
	"""
	
	dir("resources"){
		git branch: 'SHARED_LIBRARY_VER',
			credentialsId: 'global',
			url: 'https://github.com/MonseGuzman/jenkins-library-example.git'
	}
	sh '''
		folder=`find . -type d -name "terraform"`
		cp -r $folder scripts
	'''
}
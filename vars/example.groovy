def call() {
	sh 'printenv'

	// def SHARED_LIBRARY_VER = env."library.library-example.version"

	sh """
		echo ${library.library-example.version}
	"""
	
	dir("resources"){
		git branch: "${library.library-example.version}",
			credentialsId: 'global',
			url: 'https://github.com/MonseGuzman/jenkins-library-example.git'
	}
	sh '''
		folder=`find . -type d -name "terraform"`
		cp -r $folder scripts
	'''
}
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
}
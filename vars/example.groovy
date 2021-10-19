def call() {
	// dir("scripts"){
	// 	git branch: 'master',
	// 		credentialsId: 'global',
	// 		url: 'https://github.com/MonseGuzman/example-scripts.git'
	// }
	dir("scripts"){
		git branch: 'main',
			credentialsId: 'global',
			url: 'https://github.com/MonseGuzman/jenkins-library-example.git'
	}
}
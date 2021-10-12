def call(String name = 'User') {
	steps {
		echo "Install ${name}."
		git branch: 'master',
			credentialsId: 'global',
			url: 'https://github.com/MonseGuzman/example-scripts.git'
	}
}
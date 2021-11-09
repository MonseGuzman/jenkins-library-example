def call() {
	def MY_PASSWORD = "YWVyY3dxZWY"

	// def test = sh (
	// 	script: 'git --no-pager show -s --format=\'%ae\'',
	// 	returnStdout: true
	// ).trim()

	wrap([$class: "MaskPasswordsBuildWrapper",
			varPasswordPairs: [[password: MY_PASSWORD]]]) {
		echo "Password: ${MY_PASSWORD}"
	}
	// sh '''
	// 	chmod +x scripts/tfe-private-module.sh
	// 	sh ./scripts/tfe-private-module.sh
	// '''
}
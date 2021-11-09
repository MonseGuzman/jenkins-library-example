def call() {
	def MY_PASSWORD = "YWVyY3dxZWY"

	wrap([$class: "MaskPasswordsBuildWrapper",
			varPasswordPairs: [[password: MY_PASSWORD]]]) {
		echo "Password: ${MY_PASSWORD}"
		echo "Secret: ${MY_SECRET}"
	}
	// sh '''
	// 	chmod +x scripts/tfe-private-module.sh
	// 	sh ./scripts/tfe-private-module.sh
	// '''
}
def tfeToken

def call() {
	def MY_PASSWORD = "YWVyY3dxZWY"

	sh 'echo "ay noooo"'

	tfeToken = sh(
		script: 'eval echo `cat .terraformrc | grep "token" | awk \'{printf $2}\' | tr -d \' " \' `',
		returnStdout: true,
	).trim()

	wrap([$class: "MaskPasswordsBuildWrapper",
			varPasswordPairs: [[password: MY_PASSWORD], [password: tfeToken]]]) {
		echo "Password: ${MY_PASSWORD}"
		echo "test: ${tfeToken}"

		sh '''
			${tfeToken}="hey"
			sh ./scripts/tfe-private-module.sh
		'''
	}

	// sh '''
	// 	chmod +x scripts/tfe-private-module.sh
	// 	sh ./scripts/tfe-private-module.sh
	// '''
}
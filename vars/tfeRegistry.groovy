def tfeToken

def call() {
	def MY_PASSWORD = "YWVyY3dxZWY"

	sh 'echo "ay noooo"'

	tfeToken = sh(
		script: 'eval `cat .terraformrc | grep "token" | awk \'{printf $2}\' | tr -d \' " \' `',
		returnStdout: true,
  	)

	wrap([$class: "MaskPasswordsBuildWrapper",
			varPasswordPairs: [[password: MY_PASSWORD], [password: tfeToken]]]) {
		echo "Password: ${MY_PASSWORD}"
		echo "test: ${tfeToken}"
		sh "./scripts/tfe-private-module.sh"
	}

	// sh '''
	// 	chmod +x scripts/tfe-private-module.sh
	// 	sh ./scripts/tfe-private-module.sh
	// '''
}
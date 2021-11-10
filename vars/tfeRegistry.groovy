def tfeToken

def call() {
	def MY_PASSWORD = "YWVyY3dxZWY"

	sh 'echo "ay noooo"'

	wrap([$class: "MaskPasswordsBuildWrapper",
			varPasswordPairs: [[password: MY_PASSWORD], [password: tfeToken]]]) {
		echo "Password: ${MY_PASSWORD}"

		tfeToken = sh(
			script: 'eval echo `cat .terraformrc | grep "token" | awk \'{printf $2}\' | tr -d \' " \' `',
			returnStdout: true,
		)

		sh '''
			sh ./scripts/tfe-private-module.sh
		'''
		
		echo "test: ${tfeToken}"
	}

	// sh '''
	// 	chmod +x scripts/tfe-private-module.sh
	// 	sh ./scripts/tfe-private-module.sh
	// '''
}
def tfeToken

def call() {
	def MY_PASSWORD = "YWVyY3dxZWY"

	tfeToken = sh(
		script: 'echo `cat .terraformrc | grep "token" | awk \'{printf $2}\' | tr -d \' " \' `',
		returnStdout: true,
  	)

	sh 'echo ${tfeToken}'
	sh 'echo $tfeToken'

	// wrap([$class: "MaskPasswordsBuildWrapper",
	// 		varPasswordPairs: [[password: MY_PASSWORD], [password: test]]]) {
	// 	echo "Password: ${MY_PASSWORD}"
	// 	echo "test: ${test}"
	// }

	// sh '''
	// 	chmod +x scripts/tfe-private-module.sh
	// 	sh ./scripts/tfe-private-module.sh
	// '''
}
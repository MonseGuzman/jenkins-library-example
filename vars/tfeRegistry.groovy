def call() {
	def MY_PASSWORD = '`cat .terraformrc | grep "token" | awk \'{printf $2}\' | tr -d \' " \' `'

	// tfeToken = sh(
	// 	script: 'eval echo `cat .terraformrc | grep "token" | awk \'{printf $2}\' | tr -d \' " \' `',
	// 	returnStdout: true,
	// ).trim()

	sh 'echo "ay noooo"'

	sh """
		echo 'esta es la variable MY_PASSWORD'
		echo ${MY_PASSWORD}
		echo 'end sh'
	"""

	wrap([$class: "MaskPasswordsBuildWrapper",
			varPasswordPairs: [[password: MY_PASSWORD]]]) {
		echo "TFE_TOKEN: ${MY_PASSWORD}"

		sh """
			set +x
			echo 'esta es la variable MY_PASSWORD'
			sh ./scripts/tfe-private-module.sh ${MY_PASSWORD}
		"""
	}
}
// def tfeToken

def call() {
	def MY_PASSWORD = 'eval echo `cat .terraformrc | grep "token" | awk \'{printf $2}\' | tr -d \' " \' `'

	tfeToken = sh(
		script: 'eval echo `cat .terraformrc | grep "token" | awk \'{printf $2}\' | tr -d \' " \' `',
		returnStdout: true,
	).trim()

	sh 'echo "ay noooo"'

	sh """
		echo ${tfeToken}
		echo ${MY_PASSWORD}
	"""

	wrap([$class: "MaskPasswordsBuildWrapper",
			varPasswordPairs: [[password: MY_PASSWORD], [password: tfeToken]]]) {
		echo "Password: ${MY_PASSWORD}"
		echo "test: ${tfeToken}"

		sh """
			chmod +x scripts/tfe-private-module.sh
			sh ./scripts/tfe-private-module.sh ${tfeToken}
		"""
	}
}
def call() {

	sh 'echo "before to declate the variable"'
	def MY_PASSWORD = '`cat .terraformrc | grep "token" | awk \'{printf $2}\' | tr -d \' " \' `'
	sh 'echo "after to declate the variable"'

	// catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE', message: "THE TERRATEST FAILS") {
		sh """
			set +x
			echo ${MY_PASSWORD}
			chmod +x scripts/tfe-private-module.sh
			sh ./scripts/tfe-private-module.sh
		"""
	// }

	sh "echo 'hey = ${currentBuild.currentResult}'"

	// wrap([$class: "MaskPasswordsBuildWrapper",
	// 		varPasswordPairs: [[password: MY_PASSWORD]]]) {
	// 	echo "TFE_TOKEN: ${MY_PASSWORD}"

	// 	sh """
	// 		set +x
	// 		sh ./scripts/tfe-private-module.sh ${MY_PASSWORD}
	// 	"""
	// }
}
def call(String execution = 'none'){
    def MY_PASSWORD = '`cat .terraformrc | grep "token" | awk \'{printf $2}\' | tr -d \' " \' `'
    def MAX_RUN_TIME = "${currentBuild.durationString.replace(' y contando', '')}"

    if (execution == 'create') {
        sh """
            set +x
            echo "create"
			echo ${MAX_RUN_TIME}
        """
    } else if (execution == 'delete') {
        // currentBuild.result = 'FAILED'
        // return
         sh """
            set +x
			echo ${MAX_RUN_TIME}
            echo "delete"
        """
    } else {
        sh """
            set +x
			echo ${MY_PASSWORD}
            echo "none"
        """
    }
}
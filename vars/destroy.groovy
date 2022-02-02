def call(String execution = 'none'){
    def MY_PASSWORD = '`cat .terraformrc | grep "token" | awk \'{printf $2}\' | tr -d \' " \' `'

    if (${execution} == 'create') {
        sh """
            set +x
            echo "create"
			echo ${MY_PASSWORD}
        """
    } else if (${execution} == 'delete') {
        // currentBuild.result = 'FAILED'
        // return
         sh """
            set +x
			echo ${MY_PASSWORD}
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
def call(){
    script{
        env.LATEST_VERSION = sh(script: "eval git tag | sort -V | tail -1", returnStdout: true).trim()
    }

    if (env.LATEST_VERSION != ""){
        sh "echo 'inside of ---if--- ${LATEST_VERSION}'"
    }

    sh "echo 'outside of --if--' "

    def MAX_RUN_TIME = ${currentBuild.durationString.replace(' and counting', '')}
    
    sh """
        echo "${MAX_RUN_TIME}"
    """
}
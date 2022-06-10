def call(){
    script{
        env.LATEST_VERSION = sh(script: "eval git tag | sort -V | tail -1", returnStdout: true).trim()
    }

    if (env.GIT_REPO_NAME.contains("web")){
        env.APP_NAME="${APP_NAME}-web"
    } else if (env.GIT_REPO_NAME.contains("app")){
        env.APP_NAME="${APP_NAME}-app"
    }

    sh "echo 'My APP_NAME: ${APP_NAME}'"

    // def MAX_RUN_TIME = "${currentBuild.durationString.replace(' y contando', '')}"
    def exists = fileExists 'scripts/terratest.sh'

    if (exists){
        sh """
            echo 'hello'
        """
    }

    def rep = checkout(scm).repo
    sh "echo 'Repository is: ${rep}'"
}
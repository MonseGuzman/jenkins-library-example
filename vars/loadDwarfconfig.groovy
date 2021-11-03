def call(){
    sh '''
        chmod +x scripts/export.sh scripts/script-spw.sh

        source scripts/export.sh
        sh ./scripts/script-spw.sh

        echo $TERRAFORM_DESTROY
    '''

    script{
        TF_DESTROY=sh(script: "eval echo `sh ./scripts/export.sh`", returnStdout: true).trim()
    }

    sh 'echo ${TF_DESTROY}'

    // env.TF_DESTROY = sh'''
    //     source scripts/export.sh
    //     eval echo '$TERRAFORM_DESTROY'
    // '''

    withEnv(["TF_DESTROY=${TF_DESTROYYY}"]) {
        echo "TF_DESTROY_TEST = ${env.TF_DESTROY}" // prints: FOO = newbar
    }

    sh 'printenv | sort'
}
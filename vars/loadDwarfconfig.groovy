def call(){
    sh '''
        chmod +x scripts/export.sh scripts/script-spw.sh

        source scripts/export.sh
        sh ./scripts/script-spw.sh

        echo $TERRAFORM_DESTROY
    '''

    script{
        env.TF_DESTROY=sh(script: "./scripts/export.sh", returnStdout: true).trim()
    }

    // env.TF_DESTROY = sh'''
    //     source scripts/export.sh
    //     eval echo '$TERRAFORM_DESTROY'
    // '''

    // withEnv(["TERRAFORM_DESTROY=newbar"]) {
    //     echo "TERRAFORM_DESTROY = ${env.TERRAFORM_DESTROY}" // prints: FOO = newbar
    // }

    sh 'printenv | sort'
}
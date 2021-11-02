def call(){
    sh '''
        chmod +x scripts/export.sh scripts/script-spw.sh

        source scripts/export.sh
        sh ./scripts/script-spw.sh
    '''

    // withEnv(["TERRAFORM_DESTROY=newbar"]) {
    //     echo "TERRAFORM_DESTROY = ${env.TERRAFORM_DESTROY}" // prints: FOO = newbar
    // }

    sh 'printenv | sort'
}
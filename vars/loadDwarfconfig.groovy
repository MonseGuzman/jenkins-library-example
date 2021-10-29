def call(){
    sh '''
        chmod +x scripts/export.sh
        sh ./scripts/export.sh

        source $BASH_ENV

        echo $TERRAFORM_DESTROY
    '''

    // withEnv(["TERRAFORM_DESTROY=newbar"]) {
    //     echo "TERRAFORM_DESTROY = ${env.TERRAFORM_DESTROY}" // prints: FOO = newbar
    // }

    sh 'printenv'
}
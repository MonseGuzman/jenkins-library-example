def call(){
    sh '''
        export TERRAFORM_DESTROY=''

        chmod +x scripts/export.sh
        sh ./scripts/export.sh

        echo $TERRAFORM_DESTROY
    '''

    // withEnv(["TERRAFORM_DESTROY=newbar"]) {
    //     echo "TERRAFORM_DESTROY = ${env.TERRAFORM_DESTROY}" // prints: FOO = newbar
    // }

    sh 'printenv'
}
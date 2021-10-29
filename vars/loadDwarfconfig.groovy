def call(){
    sh '''
        chmod +x scripts/export.sh
        sh ./scripts/export.sh

        echo $TERRAFORM_DESTROY
    '''

    sh 'printenv'
}
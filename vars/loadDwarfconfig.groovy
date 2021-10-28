def call(){
    sh '''
        chmod +x scripts/export.sh
        sh ./scripts/export.sh
    '''

    sh 'printenv'
}
def call(){
    sh '''
        chmod +x scripts/export.sh
        ./scripts/export.sh
    '''

    sh 'printenv'
}
def call(){
    def MY_PASSWORD = '`cat .terraformrc | grep "token" | awk \'{printf $2}\' | tr -d \' " \' `'

    linux 'destroy'

    sh '''
        echo "MY_PASSWORD"
        echo "${MY_PASSWORD}"
    '''
}
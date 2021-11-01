// def env = System.getenv()

def call(){
    sh '''
        chmod +x scripts/export.sh

        source scripts/export.sh

        env.TERRAFORM_DESTROY=$TERRAFORM_DESTROY
    '''

    // withEnv(["TERRAFORM_DESTROY=newbar"]) {
    //     echo "TERRAFORM_DESTROY = ${env.TERRAFORM_DESTROY}" // prints: FOO = newbar
    // }

    sh'''
        echo env.TERRAFORM_DESTROY
    '''

    sh 'printenv | sort'
}
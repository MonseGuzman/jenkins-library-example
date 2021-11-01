// def env = System.getenv()

def call(){
    sh '''
        chmod +x scripts/export.sh

        source scripts/export.sh

        echo $TERRAFORM_DESTROY > destroy
        set ${TERRAFORM_DESTROY}=$TERRAFORM_DESTROY
        set env.TERRAFORM_DESTROY=$TERRAFORM_DESTROY
    '''

    // withEnv(["TERRAFORM_DESTROY=newbar"]) {
    //     echo "TERRAFORM_DESTROY = ${env.TERRAFORM_DESTROY}" // prints: FOO = newbar
    // }

    sh'''
        echo "otro"
        echo ${TERRAFORM_DESTROY}
    '''

    sh 'printenv | sort'
}
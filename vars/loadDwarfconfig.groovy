// def myVariable = "TRUE"

def call(){
    sh '''
        chmod +x scripts/export.sh

        source scripts/export.sh

        echo $TERRAFORM_DESTROY
    '''

    // withEnv(["TERRAFORM_DESTROY=newbar"]) {
    //     echo "TERRAFORM_DESTROY = ${env.TERRAFORM_DESTROY}" // prints: FOO = newbar
    // }

    sh'''
        echo $TERRAFORM_DESTROY
    '''

    sh 'printenv | sort'
}
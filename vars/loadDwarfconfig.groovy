// def env = System.getenv()

// import hudson.EnvVars
// import hudson.model.Environment

def call(){
    sh """
        chmod +x scripts/export.sh

        source scripts/export.sh

        echo "$TERRAFORM_DESTROY" > destroy
        set ${TERRAFORM_DESTROY}=$TERRAFORM_DESTROY
        set env.TERRAFORM_DESTROY=$TERRAFORM_DESTROY
    """

    // withEnv(["TERRAFORM_DESTROY=newbar"]) {
    //     echo "TERRAFORM_DESTROY = ${env.TERRAFORM_DESTROY}" // prints: FOO = newbar
    // }

    updatedDestroy()

    sh'''
        echo "otro"
        echo ${TERRAFORM_DESTROY}
    '''

    sh 'printenv | sort'
}

// def updatedDestroy(){
//     def build = Thread.currentThread().executable
//     def vars = [TERRAFORM_DESTROY: 'value1']

//     build.environments.add(0, Environment.create(new EnvVars(vars)))
// }
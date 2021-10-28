def loadEnvironmentVariables(){
    def props = readProperties file: 'dwarf.config'

    keys=props.keySet()
    for(key in keys){
        value = props["${key}"]
        env."${key}" = "${value}"
    }
}

def call(){
    loadEnvironmentVariables()

    sh 'printenv'
}
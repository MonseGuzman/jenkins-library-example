def call(){
    sh '''
    echo "##[group] dwarf.config logic"
    echo "##[debug] ENV_NAME: $ENV_NAME"

    echo "##[debug] Reading dwarf.config file and exporting ENV variables"
    echo $(grep -v '^#' dwarf.config | xargs) 
    echo "##[debug] Declared variables are:"
    array=($(awk -F '=' '{ print $1 }' dwarf.config))
    array2=($(awk -F '=' '{ print $2 }' dwarf.config))
    i=0
    while [ $i -lt ${#array[*]} ]; do
        if [ "${array[$i]}" == "LANDING_ZONE" ]; then
            LANDING_ZONE=${array2[$i]}
        fi

        if [ "${array[$i]}" == "SUPPORTED_PRODUCT_WORKLOAD" ]; then
            SUPPORTED_PRODUCT_WORKLOAD=${array2[$i]}
        fi

        echo "##[debug] setting ${array[$i]} = ${array2[$i]}"
        export ${array[$i]}=${array2[$i]}
        i=$(( $i + 1));
    done

    echo "##[endgroup]"
    '''

    sh 'printenv'
}
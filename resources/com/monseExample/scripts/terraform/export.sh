#!/bin/bash

# echo "##[debug] Reading dwarf.config file and exporting ENV variables"
# echo "##[debug] Declared variables are:"

array=( `awk -F '=' '{ print $1 }' dwarf.config` )
array2=( `awk -F '=' '{ print $2 }' dwarf.config` )

i=0
while [ $i -lt ${#array[*]} ]; do
    if [ "${array[$i]}" == "$1" ]; then
        # echo "##[debug] setting ${array[$i]} = ${array2[$i]}"
        export ${array[$i]}=${array2[$i]}
        set ${array[$i]}=${array2[$i]}
        echo "${array2[$i]}"
    fi
    i=$(( $i + 1));
done

# echo "##[endgroup]"
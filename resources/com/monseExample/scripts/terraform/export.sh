#!/bin/bash

echo "##[group] dwarf.config logic"

echo "##[debug] Reading dwarf.config file and exporting ENV variables"
echo $(grep -v '^#' dwarf.config | xargs) 
echo "##[debug] Declared variables are:"

export TERRAFORM_DESTROY=FALSE
export variable=no
export variable1=no
export variable2=no
export variable3=no
echo $TERRAFORM_DESTROY

# array=( `awk -F '=' '{ print $1 }' dwarf.config` )
# array2=( `awk -F '=' '{ print $2 }' dwarf.config` )
# i=0
# while [ $i -lt ${#array[*]} ]; do
#     if [ "${array[$i]}" == "TERRAFORM_DESTROY" ]; then
#         LANDING_ZONE=${array2[$i]}
#         echo "##[debug] setting ${array[$i]} = ${array2[$i]}"
#         # set ${array[$i]}=${array2[$i]}
#         eval ${array[$i]}=${array2[$i]}
#     fi
#     i=$(( $i + 1));
# done

# echo "##[endgroup]"
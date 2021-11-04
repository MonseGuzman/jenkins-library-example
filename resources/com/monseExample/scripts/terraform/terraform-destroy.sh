#!/bin/bash

echo "##[command]terraform init"
terraform init
# terraform workspace new $TF_WORKSPACE 2> /dev/null
# # This should be set using the TF_WORKSPACE environment variable
# echo "Current workspace: $(terraform workspace show)"

echo "##[command]terraform destroy"
terraform destroy -auto-approve
status=$?

if [ $status -eq 3 ]; then
    echo "##[error]*****Terraform issues found and will be published as failed.*****"
fi

exit $status
#!/bin/bash
##### Variables to export
export TFE_ORG="CNE-Solutions-Azure-Example"
export TFE_HOST="app.terraform.io"
export TFE_TOKEN="$1"
export TFE_WORKSPACE="terratest-2"

# set -e

function add_tfe_workspace_var() {
    echo "##[debug]Retrieving variables..."

    TF_VARIABLES=$(curl \
        --silent \
        --header "Authorization: Bearer $TFE_TOKEN" \
        --header "Content-Type: application/vnd.api+json" \
        --request GET \
        https://$TFE_HOST/api/v2/workspaces/$WORKSPACE_ID/vars | jq -r ".data[].attributes")

    echo $TF_VARIABLES
    echo "####################"
    echo $TF_VARIABLES | jq -r ".key"

    VAR_KEY=(`echo $TF_VARIABLES | jq -r '.key' `)
    # VAR_VALUE=( `echo $TF_VARIABLES | jq -r ".value"` )

    # i=0
    # while [ $i -lt ${#VAR_KEY[*]} ]; do
    #     echo "##[debug] setting ${VAR_KEY[$i]} = ${VAR_VALUE[$i]}"
    #     export ${VAR_KEY[$i]}=${VAR_VALUE[$i]}
        
    #     i=$(( $i + 1));
    # done
}

echo "##[debug]Retrieving workspace ID..."
export WORKSPACE_ID=$(curl \
    --silent \
    --header "Authorization: Bearer $TFE_TOKEN" \
    --header "Content-Type: application/vnd.api+json" \
    --request GET \
    https://$TFE_HOST/api/v2/organizations/$TFE_ORG/workspaces/$TFE_WORKSPACE | jq -r ".data.id")
echo "##[debug]Workspace ID is $WORKSPACE_ID"

add_tfe_workspace_var
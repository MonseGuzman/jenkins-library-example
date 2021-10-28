#!/bin/bash

export REPO_NAME="${JOB_NAME}"
export TFE_WORKSPACE="terratest-${BUILD_NUMBER}"

set -e

function validate_args(){
    echo "##[debug]Verifying the provided arguments..."
    if [ -n "$TFE_TOKEN" ] && [ -n "${TFE_HOST}" ] && [ -n "${TFE_ORG}" ]; then
        echo "##[debug]Verification sucessful!"
    else
        echo "##vso[task.logissue type=error]Missing or invalid arguments. Verify your inputs and restart the script."
        exit 1
    fi
}

function verify_tfe_org() {
    if [ -n "$TFE_TOKEN" ] && [ -n "${TFE_ORG}" ]; then
    echo "##[debug]Verifying if organization exists on Terraform Enterprise"
    if ! check_org=$(curl \
        --silent \
        --header "Authorization: Bearer $TFE_TOKEN" \
        --header "Content-Type: application/vnd.api+json" \
        --request GET \
        https://${TFE_HOST}/api/v2/organizations | jq -r ".data[].attributes.name" | grep -w "$TFE_ORG"); then
        echo "##vso[task.logissue type=error]Organization doesn't exist on Terraform enterprise"
        exit 1
    else
        echo "##[debug]Organization name provided has been validated"
    fi
    else
    echo "##vso[task.logissue type=error]Missing arguments, a value for TFE_TOKEN or TFE_ORG wasn't provided"
    fi
}

function create_tfe_workspace() {
    echo "##[debug]Checking Terraform Enterprise organization"
    verify_tfe_org "${TFE_TOKEN}" "$TFE_ORG"

    if [ -z "$TFE_WORKSPACE" ]; then 
        TFE_WORKSPACE=$REPO_NAME-terratest-${BUILD_ID}
        export TFE_WORKSPACE="$TFE_WORKSPACE"
        echo "##[debug]Generating a random workspace name: $TFE_WORKSPACE"
    else
        export TFE_WORKSPACE="$TFE_WORKSPACE"
    fi
    echo "##vso[task.setvariable variable=tfWorkspace;isOutput=true]$TFE_WORKSPACE"
    
    echo "##[debug]Verifying if workspace $TFE_WORKSPACE doesn't exist already in organization $TFE_ORG"
    check_ws_response=$(curl \
    --silent \
    --output /dev/null \
    --write-out '%{http_code}' \
    --header "Authorization: Bearer $TFE_TOKEN" \
    --header "Content-Type: application/vnd.api+json" \
    --request GET https://$TFE_HOST/api/v2/organizations/"$TFE_ORG"/workspaces/"$TFE_WORKSPACE")

    if [ "$check_ws_response" -eq 404 ]; then
        echo "##[debug]Creating workspace $TFE_WORKSPACE"
        echo "##[debug]Creating payload configuration"
        PAYLOAD=$(cat <<EOF
{
"data": {
    "attributes": {
        "name": "$TFE_WORKSPACE",
        "allow-destroy-plan": true,
        "auto-apply": true,
        "terraform_version": "$TF_VERSION",
        "execution-mode": "remote",
        "working-directory": "$TFE_WORKING_DIRECTORY"
    },
    "type": "workspaces"
    }
}
EOF
)
        create_ws_response=$(curl \
        --silent \
        --output /dev/null \
        --write-out '%{http_code}' \
        --header "Authorization: Bearer $TFE_TOKEN" \
        --header "Content-Type: application/vnd.api+json" \
        --request POST \
        --data "$PAYLOAD" https://$TFE_HOST/api/v2/organizations/"$TFE_ORG"/workspaces/)

        if [ "$create_ws_response" -eq 201 ]; then
            echo "##[debug]Workspace $TFE_WORKSPACE has been created successfully within organization $TFE_ORG"
        else
            echo "##vso[task.logissue type=error]An error has occured while creating the workspace. Response code $create_ws_response"
            exit 1
        fi
    else
        echo "##vso[task.logissue type=error]Workspace $TFE_WORKSPACE already exists within organization $TFE_ORG"
        exit 1
    fi
}
  

validate_args
verify_tfe_org

create_tfe_workspace

echo "##[debug]Retrieving workspace ID"
    WORKSPACE_ID=$(curl \
      --silent \
      --header "Authorization: Bearer $TFE_TOKEN" \
      --header "Content-Type: application/vnd.api+json" \
      --request GET \
      https://$TFE_HOST/api/v2/organizations/"$TFE_ORG"/workspaces/"$TFE_WORKSPACE" | jq -r ".data.id")
echo "##[debug]Workspace ID is $WORKSPACE_ID"
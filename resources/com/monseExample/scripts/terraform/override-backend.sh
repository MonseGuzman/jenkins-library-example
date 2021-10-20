#!/bin/bash

set -e

function create_backend() {
    echo "##[debug] Creating override.tf for backend configuration"
    cat <<EOF > "override.tf"
terraform {
    backend "remote" {
        hostname = "$TFE_HOST"
        organization = "$TFE_ORG"
        
        workspaces {
            name = "test-dev"
        }
    }
}
EOF
    echo "####[command]cat override.tf"
    cat override.tf
    echo "####[debug] Finishing to create override.tf for backend configuration"
}

create_backend
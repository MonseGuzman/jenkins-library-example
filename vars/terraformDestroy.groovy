sh '''
    ls 
    echo "####[command] Terraform destroy"
    
    chmod +x scripts/export.sh scripts/terraform-destroy.sh

    source scripts/export.sh
    sh ./scripts/terraform-destroy.sh
'''
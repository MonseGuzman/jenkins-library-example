#!/bin/bash

if [ -n "$1" ]; then
    echo "im not empty :("
    echo $ARM_TENANT_ID
    echo $ARM_SUBSCRIPTION_ID
    echo $ARM_CLIENT_SECRET
    echo $ARM_CLIENT_ID
else 
    echo "im empty :)"
fi

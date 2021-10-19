#!/usr/bin/env groovy
package org.monseExample

class GlobalVars implements Serializable {
    static String TFE_WORKSPACE="terratest-${BUILD_NUMBER}"
}
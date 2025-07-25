pipeline {
  agent any
  tools {
    maven 'Maven_3.9.11'
    // jdk 'JDK_17' nếu cần
  }
  stages {
    stage('Checkout') {
      steps {
        git url: 'https://github.com/hoangmai29/BTGiuaKy.git', branch: 'main'
      }
    }
    stage('Build') {
      steps {
        dir('CICD_BT') {
          withMaven(maven: 'Maven_3.9.11') {
            sh 'mvn clean install'
          }
        }
      }
    }
    stage('Test') {
      steps {
        dir('CICD_BT') {
          sh 'mvn test'
        }
      }
    }
  }
}

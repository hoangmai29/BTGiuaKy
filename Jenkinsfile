pipeline {
  agent any
  tools {
    maven 'Maven 3.9.11'
    // nếu bạn đã cài cấu hình JDK thì thêm: jdk 'JDK_17'
  }
  stages {
    stage('Build') {
      steps {
        withMaven(maven: 'Maven 3.9.11') {
          sh 'mvn clean install'
        }
      }
    }
    stage('Test') {
      steps {
        sh 'mvn test'
      }
    }
    stage('Deploy') {
      steps {
        echo 'Deploying...'
        // thêm bước deploy nếu có
      }
    }
  }
}

pipeline {
  agent any

  tools {
    maven 'Maven 3.9.11'  // Tên Maven đã khai báo trong "Global Tool Configuration"
    // jdk 'JDK_17'        // Nếu cần JDK, đảm bảo cấu hình trước trong Jenkins
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build') {
      steps {
        withMaven(maven: 'Maven 3.9.11') {
          sh 'mvn clean install'
        }
      }
    }

    stage('Test') {
      steps {
        withMaven(maven: 'Maven 3.9.11') {
          sh 'mvn test'
        }
      }
    }

    stage('Package') {
      steps {
        echo 'Packaging...'
        // nếu muốn: sh 'mvn package'
      }
    }

    stage('Deploy') {
      steps {
        echo 'Deploying...'
        // thêm code deploy nếu có
      }
    }
  }

  post {
    success {
      echo '✅ Build succeeded!'
    }
    failure {
      echo '❌ Build failed!'
    }
    always {
      echo 'Done.'
    }
  }
}

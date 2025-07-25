pipeline {
  agent any

  tools {
    git 'Git'  // Tên Git đã cấu hình trong Global Tool Configuration
    maven 'Maven 3.9.11'  // Nếu bạn sử dụng Maven, đảm bảo đã cấu hình Maven tool tương tự
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

    stage('Deploy') {
      steps {
        echo 'Deploying...'
        // Thêm lệnh deploy nếu cần
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

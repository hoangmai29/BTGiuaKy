pipeline {
  agent any

  tools {
    maven 'Maven_3.9.11'  // Đảm bảo đã cấu hình trong Jenkins global tool config
  }

  environment {
    SONARQUBE = 'SonarQube'  // Trùng với cấu hình SonarQube trong Jenkins
  }

  stages {

    stage('Checkout') {
      steps {
        git url: 'https://github.com/hoangmai29/BTGiuaKy.git', branch: 'main'
      }
    }

    stage('Build') {
      steps {
        withMaven(maven: 'Maven_3.9.11') {
          sh 'mvn clean install'
        }
      }
    }

    stage('Test') {
      steps {
        sh 'mvn test'
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
        }
      }
    }

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv(SONARQUBE) {
          sh 'mvn sonar:sonar'
        }
      }
    }

    stage('Build Docker Image') {
      steps {
        sh 'docker build -t library-management:latest .'
      }
    }

    stage('Deploy Local (Docker Compose)') {
      steps {
        sh 'docker-compose down && docker-compose up -d --build'
      }
    }

    stage('Deploy to Server') {
      steps {
        sshagent(['ssh-key-id']) {
          sh '''
          ssh user@your-server-ip "cd /home/user/library && git pull && docker-compose down && docker-compose up -d"
          '''
        }
      }
    }

  }

  post {
    always {
      echo '✅ Pipeline finished!'
    }
  }
}

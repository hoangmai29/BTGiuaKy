pipeline {
  agent any

  tools {
    maven 'Maven 3.9.11'
  }

  environment {
    SONARQUBE = 'SonarQube'                         // Tên server đã cấu hình trong Jenkins
    REMOTE_HOST = 'your.server.ip.or.hostname'     
    REMOTE_USER = 'your-ssh-username'              
    REMOTE_PATH = '/home/your-ssh-username/app'    
  }

  stages {
    stage('Checkout') {
      steps {
        git url: 'https://github.com/hoangmai29/BTGiuaKy.git', branch: 'main'
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
        sh 'mvn test'
      }
      post {
        always {
          junit 'target/surefire-reports/*.xml'
        }
      }
    }

    // ✅ PHÂN TÍCH VỚI SONARQUBE
    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv("${SONARQUBE}") {
          sh 'mvn sonar:sonar'
        }
      }
    }

    stage('Build Docker Image') {
      steps {
        sh 'docker build -t library-management:latest .'
      }
    }

    stage('Deploy Local') {
      steps {
        sh 'docker-compose down && docker-compose up -d --build'
      }
    }

    stage('Deploy to Remote Server via SSH') {
      steps {
        sshagent (credentials: ['my-ssh-key']) {
          sh """
            ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} '
              mkdir -p ${REMOTE_PATH} &&
              cd ${REMOTE_PATH} &&
              git clone https://github.com/hoangmai29/BTGiuaKy.git . || git pull &&
              docker-compose down &&
              docker-compose up -d --build
            '
          """
        }
      }
    }
  }

  post {
    always {
      echo '✅ CI/CD pipeline hoàn thành!'
    }
  }
}

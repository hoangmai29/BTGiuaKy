pipeline {
  agent any

  tools {
    maven 'Maven_3.9.11'
  }

  environment {
    SONARQUBE = 'SonarQube'
    REMOTE_HOST = 'your.server.ip.or.hostname'       // <- cập nhật lại
    REMOTE_USER = 'your-ssh-username'                // <- cập nhật lại
    REMOTE_PATH = '/home/your-ssh-username/app'      // <- cập nhật lại
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
        withSonarQubeEnv('SonarQube') {
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

    stage('Deploy to Server via SSH') {
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

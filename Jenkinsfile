pipeline {
  agent any

tools {
    maven 'Maven 3.9.11'  // 👈 Dùng đúng tên như trong Jenkins
}


  environment {
    SONARQUBE = 'SonarQube'
    REMOTE_HOST = '192.168.56.101'           // <-- Nhập IP của máy Ubuntu/VPS
    REMOTE_USER = 'ubuntu'                   // <-- Nhập username SSH (ví dụ: ubuntu hoặc root)
    REMOTE_PATH = '/home/ubuntu/app'         // <-- Thư mục để deploy code trên server
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
        sh 'docker-compose down || true'
        sh 'docker-compose up -d --build'
      }
    }

    stage('Deploy to Remote Server via SSH') {
      steps {
        sshagent (credentials: ['my-ssh-key']) {
          sh """
            ssh -o StrictHostKeyChecking=no ${REMOTE_USER}@${REMOTE_HOST} '
              mkdir -p ${REMOTE_PATH} &&
              cd ${REMOTE_PATH} &&
              if [ ! -d ".git" ]; then
                git clone https://github.com/hoangmai29/BTGiuaKy.git .;
              else
                git pull;
              fi &&
              docker-compose down || true &&
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

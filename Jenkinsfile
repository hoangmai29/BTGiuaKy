tools {
  maven 'Maven 3.9.11'  // Tên đúng với Global Tool Config
}

pipeline {
  agent any

  environment {
    SONARQUBE = 'SonarQube' // Trùng với config trong Jenkins
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
        // Dùng bat nếu chạy trên Windows agent
        bat 'mvn clean install'
      }
    }

    stage('Test') {
      steps {
        bat 'mvn test'
      }
      post {
        always {
          junit '**/target/surefire-reports/*.xml'
        }
      }
    }

    stage('SonarQube Analysis') {
      steps {
        withSonarQubeEnv("${SONARQUBE}") {
          bat 'mvn sonar:sonar'
        }
      }
    }

    stage('Build Docker Image') {
      steps {
        bat 'docker build -t library-management:latest .'
      }
    }

    stage('Deploy Local') {
      steps {
        bat 'docker-compose down'
        bat 'docker-compose up -d --build'
      }
    }

    stage('Deploy to Server via SSH') {
      steps {
        sshagent (credentials: ['my-ssh-key']) {
          bat """
            ssh -o StrictHostKeyChecking=no %REMOTE_USER%@%REMOTE_HOST% ^
              "mkdir -p %REMOTE_PATH% && cd %REMOTE_PATH% && ^
               if [ ! -d .git ]; then git clone https://github.com/hoangmai29/BTGiuaKy.git .; else git pull; fi && ^
               docker-compose down && docker-compose up -d --build"
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

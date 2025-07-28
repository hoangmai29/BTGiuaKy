pipeline {
  agent any

  environment {
    SONARQUBE = 'SonarQube' // Tên cấu hình SonarQube đã setup trong Jenkins
    REMOTE_HOST = 'your.server.ip.or.hostname'
    REMOTE_USER = 'your-ssh-username'
    REMOTE_PATH = '/home/your-ssh-username/app'
    MAVEN_HOME = '' // sẽ được gán trong bước Build
  }

  stages {
    stage('Checkout') {
      steps {
        git url: 'https://github.com/hoangmai29/BTGiuaKy.git', branch: 'main'
      }
    }

    stage('Build') {
      steps {
        script {
          MAVEN_HOME = tool name: 'Maven 3.9.11', type: 'hudson.tasks.Maven$MavenInstallation'
        }
        bat "${MAVEN_HOME}\\bin\\mvn clean install"
      }
    }

    stage('Test') {
      steps {
        bat "${MAVEN_HOME}\\bin\\mvn test"
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
          bat "${MAVEN_HOME}\\bin\\mvn sonar:sonar"
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
        bat 'docker-compose down && docker-compose up -d --build'
      }
    }

    stage('Deploy to Server via SSH') {
      steps {
        sshagent (credentials: ['my-ssh-key']) {
          bat """
            ssh -o StrictHostKeyChecking=no %REMOTE_USER%@%REMOTE_HOST% ^
              "mkdir -p %REMOTE_PATH% && ^
               cd %REMOTE_PATH% && ^
               git clone https://github.com/hoangmai29/BTGiuaKy.git . || git pull && ^
               docker-compose down && ^
               docker-compose up -d --build"
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

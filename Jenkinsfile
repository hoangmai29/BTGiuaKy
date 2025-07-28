pipeline {
  agent any

  tools {
    maven 'Maven_3.9.11'
  }

  environment {
    SONARQUBE = 'SonarQube'
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
  }

  post {
    always {
      echo '✅ CI/CD pipeline hoàn thành trên máy local!'
    }
  }
}

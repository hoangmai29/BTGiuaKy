pipeline {
  agent any
  tools {
    maven 'Maven_3.9.11'
  }
  environment {
    SONARQUBE = 'SonarQube' // trùng tên trong Jenkins config
  }
  stages {
    stage('Checkout') {
      steps {
        git url: 'https://github.com/hoangmai29/BTGiuaKy.git', branch: 'main'
      }
    }
    stage('Build') {
      steps {
        dir('CICD_BT') {
          withMaven(maven: 'Maven_3.9.11') {
            sh 'mvn clean install'
          }
        }
      }
    }
    stage('Test') {
      steps {
        dir('CICD_BT') {
          sh 'mvn test'
        }
      }
      post {
        always {
          junit 'CICD_BT/target/surefire-reports/*.xml'
        }
      }
    }
stage('SonarQube Analysis') {
  steps {
    dir('CICD_BT') {
      withSonarQubeEnv(SONARQUBE) {
        sh 'mvn sonar:sonar -X'
      }
    }
  }
}

    stage('Build Docker Image') {
      steps {
        sh 'docker build -t myapp:latest CICD_BT/'
      }
    }
    stage('Deploy') {
      steps {
        sh 'ssh user@server "cd /path/to/docker-compose && docker-compose up -d --build"'
      }
    }
  }
  post {
    always {
      echo 'Pipeline finished!'
    }
  }
}

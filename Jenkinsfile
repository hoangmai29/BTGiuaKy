pipeline {
  agent any
  tools {
    maven 'Maven_3.9.11'
    // jdk 'JDK_17' nếu cần
  }
  environment {
    SONARQUBE = 'SonarQubeServer' // tên SonarQube config trong Jenkins
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
          junit 'CICD_BT/target/surefire-reports/*.xml' // báo cáo test
        }
      }
    }
    stage('SonarQube Analysis') {
      steps {
        dir('CICD_BT') {
          withSonarQubeEnv(SONARQUBE) {
            sh 'mvn sonar:sonar'
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
        // ví dụ ssh deploy lên server public, bạn chỉnh theo server thực tế
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

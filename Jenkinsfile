pipeline {
    agent any

   tools {
        jdk 'Java17' 
        maven 'M3' 
    }

    stages {
        stage('Checkout') {
            steps {
                // This pulls your code from GitHub
                checkout scm
            }
        }
        
        stage('Build & Test') {
            steps {
                // This runs your Maven test suite defined in your pom.xml
                bat 'mvn clean test' 
            }
        }
    }

    post {
        always {
            // Optional: Archive results if you have a reports folder
            archiveArtifacts artifacts: 'target/surefire-reports/**', fingerprint: true
        }
    }
}

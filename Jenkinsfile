pipeline {
    agent any

    tools {
        // Make sure you have 'maven' and 'jdk' configured in Jenkins Global Tool Configuration
        maven 'maven3' 
        jdk 'jdk11'
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

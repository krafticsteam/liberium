pipeline {
    agent any

    stages {
        stage('Setup') {
            steps {
                sh 'chmod +x gradlew'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew assemble testClasses'
            }

            post {
                success {
                    archiveArtifacts '*/build/libs/*.jar'
                }
            }
        }

        stage('Test') {
            steps {
                sh './gradlew check'
            }

            junit '*/build/test-results/test/*.xml'
        }
    }

    post {
        always {
            sh './gradlew clean'
        }
    }
}
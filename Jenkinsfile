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
                sh "./gradlew assemble testClasses -Dversionsuf=+$currentBuild.number"
            }

            post {
                success {
                    archiveArtifacts '*/build/libs/*.jar'
                }
            }
        }

        stage('Test') {
            steps {
                sh "./gradlew check -Dversionsuf=+$currentBuild.number"

                withChecks('Tests') {
                    junit '*/build/test-results/test/*.xml'
                }
            }
        }
    }

    post {
        always {
            sh './gradlew clean'
        }
    }
}
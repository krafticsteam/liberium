pipeline {
    agent any

    stages {
        stage('Setup') {
            steps {
                sh 'chmod +x gradlew'
                sh 'echo "\nversion \'$BUILD_NUMBER-SNAPSHOT\'" >> build.gradle'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew assemble'
            }

            post {
                success {
                    archiveArtifacts 'build/libs/*.jar'
                }
            }
        }

        stage('Test') {
            steps {
                sh './gradlew check'
            }
        }
    }

    post {
        always {
            sh './gradlew clean'
        }
    }
}
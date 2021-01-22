pipeline {
    agent any

    stages {
        stage('Setup') {
            steps {
                sh 'chmod +x gradlew'
                sh 'echo "\nversion \'SNAPSHOT-$BUILD_NUMBER\'" >> build.gradle'
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
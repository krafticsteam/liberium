pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
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

            post {
                always {
                    junit 'build/test-results/test/*.xml'
                }
            }
        }

        stage('Deployment') {
            steps {
                nexusPublisher nexusInstanceId: '1', nexusRepositoryId: 'maven-releases', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'build/libs/*.jar']], mavenCoordinate: [artifactId: 'krafticslib-snapshot', groupId: 'com.kraftics', packaging: 'jar', version: currentBuild.number]]]
            }
        }
    }
}
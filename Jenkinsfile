pipeline {
    agent any

    stages {
        stage('Setup') {
            steps {
                dir ('build') {
                    deleteDir()
                }
                sh 'chmod +x gradlew'
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

        stage('Deployment') {
            steps {
                nexusPublisher nexusInstanceId: '1', nexusRepositoryId: 'maven-releases', packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: 'build/libs/*.jar']], mavenCoordinate: [artifactId: 'krafticslib-snapshot', groupId: 'com.kraftics', packaging: 'jar', version: currentBuild.number]]]
            }
        }
    }
}
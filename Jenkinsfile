pipeline {
    agent any

    tools {
        maven 'maven'
    }

    environment {
        APP_NAME = "user-service"
        RELEASE  = "1.0.0"
        DOCKER_USER = "coolravi"
        IMAGE_NAME = "${DOCKER_USER}/${APP_NAME}"
        IMAGE_TAG  = "${RELEASE}-${BUILD_NUMBER}"
    }

    stages {

        stage('Build Maven') {
            steps {
                checkout scmGit(
                        branches: [[name: '*/main']],
                        userRemoteConfigs: [[url: 'https://github.com/Reach-Ravi/user-service.git']]
                )
                bat 'mvn clean install'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t %IMAGE_NAME%:%IMAGE_TAG% .'
            }
        }

        stage('Push Image to Docker Hub') {
            steps {
                withCredentials([string(credentialsId: 'dockerhub-token', variable: 'DOCKER_TOKEN')]) {
                    bat '''
                        docker logout
                        echo %DOCKER_TOKEN% | docker login -u coolravi --password-stdin
                        docker push %IMAGE_NAME%:%IMAGE_TAG%
                    '''
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                powershell '''
                    (Get-Content k8s-deployment.yaml) `
                      -replace "image:.*", "image: $env:IMAGE_NAME:$env:IMAGE_TAG" |
                      Set-Content k8s-deployment.yaml
        
                    kubectl apply -f k8s-deployment.yaml
                '''
            }
        }
    }
}
pipeline {
    agent any
    tools{
        maven 'maven'
    }
environment {
    APP_NAME="jenkins-ci-cd-new"
    RELEASE= "1.0.0"
    DOCKER_USER="coolravi"
    IMAGE_NAME="${DOCKER_USER}"+"/"+"${APP_NAME}"
    IMAGE_TAG="${RELEASE}-${BUILD_NUMBER}"
}
    stages{
        stage('Build Maven'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Reach-Ravi/jenkins-ci-cd-new.git']])
                bat 'mvn clean install'
            }
        }


        stage('Build docker image'){
            steps{
                script{
                    bat 'docker build -t %IMAGE_NAME%:%IMAGE_TAG% .'
                }
            }
        }
        stage('Push image to Hub'){
            steps{
                script{
                   withCredentials([string(credentialsId: 'dockerhub-token', variable: 'DOCKER_TOKEN')]) {
                       bat '''
                           if "%DOCKER_TOKEN%"=="" (
                               echo TOKEN IS EMPTY
                           ) else (
                               echo TOKEN RECEIVED
                           )
                       '''
                   bat '''
                           docker logout
                           echo %DOCKER_TOKEN% | docker login -u coolravi --password-stdin
                       '''
                   bat 'docker push %IMAGE_NAME%:%IMAGE_TAG%'
                }
            }
        }
        /*stage('Deploy to k8s'){
            steps{
                script{
                    kubernetesDeploy (configs: 'deploymentservice.yaml',kubeconfigId: 'k8sconfigpwd')
                }
            }
        }*/
    }
    }
}
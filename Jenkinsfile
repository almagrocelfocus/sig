pipeline {
	agent any
	
	triggers {
		pollSCM('H/30 * * * *')
	}
	
	environment {
		//GIT_CREDENTIALS_PWD = credentials('my-git-pwd')
	}
	
	stages {
	
		stage('Build') {
			steps {
				sh "echo Building..."
				sh "mvn clean ${env.GIT_CREDENTIALS_PWD}"
				
				/**
				withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'jonathan-ci', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD']]) {
						 sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@${env.GIT_REPO} HEAD:master"
				}
				**/
			}
		}
		
		stage('Deploy') {
			steps {
				sh "echo Deploying..."
			}
		}
		
		stage('Test') {
			steps {
				sh "echo Testing..."
			}
		}
	}
}
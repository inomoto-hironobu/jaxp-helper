pipeline {
	agent any
	stages {
		stage ('test') {
			steps {
			    sh 'mvn clean test'
		    }
		}
		stage ('install') {
		    steps {
		        sh 'mvn install'
		    }
		}
		stage ('analysis') {
			steps {
			    sh 'mvn --batch-mode -V -U -e checkstyle:checkstyle pmd:pmd pmd:cpd findbugs:findbugs'
			}
		}
	}
}
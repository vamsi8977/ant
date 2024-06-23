pipeline {
  agent any
  options {
    buildDiscarder(logRotator(numToKeepStr:'5' , artifactNumToKeepStr: '5'))
    timestamps()
    }
  stages {
    stage('CheckOut') {
      steps {
        echo 'Checking out project from Bitbucket....'
        cleanWs()
        git(url: 'git@github.com:vamsi8977/ant.git', branch: 'main')
      }
    }
    stage('Build') {
      steps {
        script {
          sh "ant -buildfile build.xml"
        }
      }
    }
  }
  post {
    success {
      echo "The build passed."
      archiveArtifacts artifacts: "build/jar/*.jar"
    }
    failure {
      echo "The build failed."
    }
    cleanup {
      deleteDir()
    }
  }
}

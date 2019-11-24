pipeline {
   agent any

   tools {
      // Install the Maven version configured as "M3" and add it to the path.
      maven "M3"
   }

   stages {
        stage('CHECKOUT') {
            steps {
                git 'https://github.com/pavel-lucik/gw-spring-boot.git'
            }
        }
        stage('BUILD') {
            steps {
                sh "mvn clean install -f ./spring-boot-tests/spring-boot-smoke-tests/spring-boot-smoke-test-web-ui/pom.xml"
            }
        }


        //  post {
        //     // If Maven was able to run the tests, even if some of the test
        //     // failed, record the test results and archive the jar file.
        //     success {
        //       archiveArtifacts 'spring-boot-tests/spring-boot-smoke-tests/spring-boot-smoke-test-web-ui/target/*.jar'
        //         }
        //     }


        stage("UPLOAD ARTIFACT") {
            steps {
                nexusArtifactUploader(
                    nexusVersion: 'nexus3',
                    protocol: 'http',
                    nexusUrl: 'localhost:8081',
                    groupId: 'java',
                    version: 'build-${BUILD_NUMBER}',
                    repository: 'maven-public1',
                    credentialsId: 'nexus-credentials',
                    artifacts: [
                        [artifactId: 'spring-boot-smoke-test-web-ui',
                         classifier: '',
                         file: 'spring-boot-tests/spring-boot-smoke-tests/spring-boot-smoke-test-web-ui/target/spring-boot-smoke-test-web-ui-2.2.0.BUILD-SNAPSHOT.jar',
                         type: 'jar']
                    ]
                )
            }
        }

        stage ('DEPLOY') {
            steps {
                build job: 'deploy_jar_QA'
                build job: 'deploy_jar_CI'
            }
        }
    }
}

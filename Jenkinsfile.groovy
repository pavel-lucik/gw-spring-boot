// properties([pipelineTriggers([cron('*/5 * * * *')])])
properties([
    pipelineTriggers([
        GenericTrigger(
            causeString: 'Push to master', 
            genericVariables: [[
                defaultValue: '',
                key: 'ref', 
                regexpFilter: '', 
                value: '$.ref'
            ]], 
            printContributedVariables: true, 
            printPostContent: true, 
            regexpFilterExpression: 'master$', 
            regexpFilterText: '$ref', 
            silentResponse: true, 
            token: '71B6B68DFC8C34235B642BE2LPOHJ195874'
        )
    ])
])

node () {
	
    currentBuild.displayName = "#${BUILD_NUMBER} text1"
	ansiColor('xterm') {
            //printlnGreen "ttexttt"
	}
	
    stage ("average") {
        sh 'uptime'
        deleteDir()
        
    }
    
    stage ("git") {
        //withCredentials([usernamePassword(credentialsId: 'd1188926-b2e6-41b1-a7fa-1adc494fa6fc', passwordVariable: 'password', usernameVariable: 'username')]) {
        //sh 'git clone https://$username:$password@github.com/pavel-lucik/gw-spring-boot.git'}
		
		checkout scm
		sh 'ls -lh'
    }    

    stage ("pwd and build"){
        //def workDir = sh(returnStdout: true, script: "pwd").trim()
        //sh "cd $workDir && cd gw-spring-boot/spring-boot-tests/spring-boot-smoke-tests/spring-boot-smoke-test-web-ui && ls -lh && mvn clean install"
		sh "cd spring-boot-tests/spring-boot-smoke-tests/spring-boot-smoke-test-web-ui && ls -lh && mvn clean install"
    }
}

//input("Please approve deploy to...")

node(){
    stage ("upload artifact"){
        //def workDir = sh(returnStdout: true, script: "pwd").trim()
        //sh "cd $workDir && cd gw-spring-boot/spring-boot-tests/spring-boot-smoke-tests/spring-boot-smoke-test-web-ui && ls -lh"
		sh "cd spring-boot-tests/spring-boot-smoke-tests/spring-boot-smoke-test-web-ui && ls -lh"
        archiveArtifacts artifacts: "**/target/*.jar", fingerprint: true
    }
}
	
//def printlnGreen(text) {
    //println "\033[1;4;37;42m$text\033[0m"
//}

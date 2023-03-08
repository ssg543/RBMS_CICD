import com.mongodb.MongoClient
import com.mongodb.MongoClientURI

pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', 
                          branches: [[name: '*/master']], 
                          doGenerateSubmoduleConfigurations: false, 
                          extensions: [], 
                          submoduleCfg: [], 
                          userRemoteConfigs: [[url: 'https://github.com/YogeshPitale/rbms.git']]])
            }
        }
        stage('Start ConfigServer') {
            steps {
                bat 'start "ConfigServer" cmd /c "cd C:\\ConfigServer\\config-server && gradlew.bat bootrun"'
                bat 'ping 127.0.0.1 -n 20 > nul'
                archiveArtifacts artifacts: 'build/test-results/test/**/*.xml', allowEmptyArchive: true
            }
        }
                stage('MongoDB Connection') {
            steps {
                script {
                    def uri = "mongodb://localhost:27017"
                    echo "Connecting to: ${uri}"
                    def client = new com.mongodb.MongoClient(new com.mongodb.MongoClientURI(uri))
                    echo "Client: ${client}"
                    def db = uri.getDatabase("rbms_db").withCodecRegistry(com.mongodb.MongoClients.getDefaultCodecRegistry())
                    echo "Database: ${db}"
                    def collection = db.getCollection("DroolsRules")
                    echo "Collection: ${collection}"
                }
            }
        }
        stage('Start RulesBasedApplication') {
         steps {
        bat 'gradlew.bat bootrun &'
        bat 'ping 127.0.0.1 -n 60 > nul'
        //sleep time: 20, unit: 'SECONDS'
    		  }
  		  }
        stage('Run RulesBasedApplicationTest') {
            steps {
                bat 'gradlew.bat test --tests com.wf.stp.rmbs.unit.*'
                def testResultFile = "build/test-results/test/TEST-com.wf.stp.rmbs.unit.IntermediatorServiceUnitTests-${BUILD_NUMBER}.xml"
                archiveArtifacts artifacts: testResultFile, allowEmptyArchive: true
                archiveArtifacts artifacts: 'build/libs/*.jar'
            }
        }
        stage('Build WAR file') {
            steps {
                bat 'gradlew.bat war'
                archiveArtifacts artifacts: 'build/libs/*.war', allowEmptyArchive: false
            }
        }
     stage('Build Jar file') {
         steps {
             archiveArtifacts artifacts: 'build/libs/*.jar'
         }
     }
        stage('Logging') {
            steps{
                echo "Logging"
                archiveArtifacts artifacts: 'build/test-results/test/**/*.xml', allowEmptyArchive: false
            }
        }
		stage('Stop RulesBasedApplication') {
            steps {
                bat 'taskkill /f /im java.exe' 
            }
        }
     }
}

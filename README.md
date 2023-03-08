# RBMS_CICD

SetUP:

Install Jenkins (https://www.jenkins.io/download/)
Configure the required port for jenkins to run (Default 8080)
In Dashboard -> NewItem -> Pipeline -> Ok

Pipeline Configuration:

Give description to the pipeline
Select GitHub project 
Enter project URL https://github.com/YogeshPitale/rbms/
Office 365 Connector - Enter the teams Webhook URL to get the regular notification - https://persistentsystems.webhook.office.com/webhookb2/2d67cd29-8e05-4f76-94c6-364e129632bd@1f4beacd-b7aa-49b2-aaa1-b8525cb257e0/IncomingWebhook/6cbf6fd299c94cfb88a54d149d0af2c6/ab0036d5-341a-4d5d-8f4b-4a595ada95a2
Name - Integration Test Status(Give any name)
Click on Pipeline speed/durability override - Custom Pipeline Speed/Durability Level - Performance Optimized
Under BuildTrigger
    - GitHub hook trigger for GITScm polling - For regular check in the GIT
    - Poll SCM - Schedule a time here as H/5 * * * *  - This will check git for every 5 minutes
Advanced Project Options - Display Name
Under Pipeline - PipeLine Script
    - Type the script(Groovy)
    - Check the Use Groovy Sandbox
Apply & Save 
BuildNow

Manage Plugins:

ManageJenkins -> ManagePlugins -> Available plugins , download the required plugins here
    - Pipeline: Deprecated Groovy Libraries
    - Pipeline: Groovy Libraries
    - MongoDB Plugin
    - mongodb-document-upload
    - Gradle
    - JUnit
    - Git plugin
    - Pipeline
    - Pipeline Graph Analysis Plugin
    
 Note : If The currently installed plugin version not be safe to use. Please review the security notices (https://www.jenkins.io/security/advisory/2023-02-15/)
 
 Configure System:
 
    In Home directory - Enter the directory from local system - By default, Jenkins stores all of its data in this directory on the file system
    # of executors - 2
    Usage - Use this node as much as possible
    Quiet Period - 5
    SCM checkout retry count - 1
    Default view -all
    
  Jenkins Location:
    
    Jenkins URL
    System Admin e-mail address
    
 Global properties
 
    Name : java.lang.String.getDatabase()
    Value : rbms_db
    
 Global Tool Configuration
    
    Git
     - Name : Git
     Path to Git executable - Local git installed path
     
    Gradle
     - Name : Gradle
     Path to gradle in local system
     
    MongoDB
    - Name : Mongo
    Install directory

Apply and Save


In-process Script Approval

    - Under Signatures already approved:
            new com.mongodb.MongoClient com.mongodb.MongoClientURI
            new com.mongodb.MongoClientURI java.lang.String
            new java.io.File java.lang.String
            
    - new java.io.File java.lang.String 
    - may have introduced a security vulnerability (recommend clearing):
    - Check all the information under System Properties

Also find the attched document for the demo.



















[Jenkins Pipeline For Integration Test.docx](https://github.com/ssg543/RBMS_CICD/files/10920159/Jenkins.Pipeline.For.Integration.Test.docx)

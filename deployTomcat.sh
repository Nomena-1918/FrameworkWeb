#!/bin/bash

# Compilation du projet test
bash compileTest.sh

# Déclaration des variables
projectName="Test-webapp"
classDir="binTest/"
webXmlPath="Test-framework-webapp/config-webapp/web.xml"
viewDir="Test-framework-webapp/views/"
warDest="archives_java"
frameworkJarPath=$warDest"/framework.jar"
gsonJar="Test-framework-webapp/config-webapp/gson-2.8.2.jar"
postgreJar="Test-framework-webapp/config-webapp/postgresql-42.6.0.jar"
tomcatPath="/Applications/apache-tomcat-8.5.87/webapps/"


#Constitution du projet
mkdir $projectName 
# shellcheck disable=SC2164
cd $projectName
mkdir WEB-INF WEB-INF/classes WEB-INF/lib


cp -r -f ../$classDir WEB-INF/classes
cp -r -f ../$webXmlPath WEB-INF/
cp -r -f ../$frameworkJarPath WEB-INF/lib
cp -r -f ../$viewDir .
cp -r -f ../$gsonJar WEB-INF/lib
cp -r -f ../$postgreJar WEB-INF/lib

#Transformation en .war
jar cf ../$warDest/$projectName.war  *;

#Copie vers Tomcat8
cp -f ../$warDest/$projectName.war  $tomcatPath;

#Clean
cd ..
rm -r -f $projectName






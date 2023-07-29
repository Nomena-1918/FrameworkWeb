#!/bin/bash

# Compilation du projet test
bash compileTest.sh

# Déclaration des variables
projectName="Test-webapp"
classDir="binTest/"
webXmlPath="Test-framework-webapp/config-webapp/web.xml"
viewDir="Test-framework-webapp/views/"
warDest="archives_java"
jar_dir="Test-framework-webapp/lib"

tomcatPath="/Applications/apache-tomcat-8.5.87/webapps/"


#Constitution du projet
mkdir $projectName 
# shellcheck disable=SC2164
cd $projectName
mkdir WEB-INF WEB-INF/classes WEB-INF/lib


cp -r -f ../$classDir WEB-INF/classes
cp -r -f ../$webXmlPath WEB-INF/
cp -r -f ../$jar_dir/* WEB-INF/lib
cp -r -f ../$viewDir .


#Transformation en .war
# shellcheck disable=SC2035
jar cf ../$warDest/$projectName.war  * ;

#Copie vers Tomcat8
cp -f ../$warDest/$projectName.war  $tomcatPath;

#Clean
# shellcheck disable=SC2103
cd ..
rm -r -f $projectName






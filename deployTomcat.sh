#!/bin/bash

# Préparation du projet Test-framework-webapp
cp  -r  "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/out/production/Test-framework-webapp/"  "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/Test-framework-webapp/WEB-INF/classes/";


# Exportation projet Test-framework-webapp -> Test-framework-webapp.war
# shellcheck disable=SC2164
cd "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/Test-framework-webapp";
# shellcheck disable=SC2035
jar   cvf  "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/archives_java/Test-framework-webapp.war"   *;


# Déploiement vers Tomcat
cp  "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/archives_java/Test-framework-webapp.war"  "/Applications/apache-tomcat-8.5.87/webapps/";



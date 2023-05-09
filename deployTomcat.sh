#!/bin/bash

# Prochaine étape : 
# Utiliser des variables bash
# Utilisation d'un répertoire temporaire pour la préparation du projet
# Tout copier là-bas
# (Pour rendre les script.sh plus flexibles)
# Créer le war à partir de ce répertoire temporaire

# Préparation du projet Test-framework-webapp
cp  -r -f -v "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/out/production/Test-framework-webapp/"  "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/Test-framework-webapp/Test-framework/WEB-INF/classes/";


# Exportation projet Test-framework-webapp -> Test-framework-webapp.war
# shellcheck disable=SC2164
cd "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/Test-framework-webapp/Test-framework/";

# shellcheck disable=SC2035
jar  cvf  "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/archives_java/Test-framework-webapp.war"   *;


# Déploiement vers Tomcat
# Tomcat 10
#cp -f -v "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/archives_java/Test-framework-webapp.war"  "/Applications/apache-tomcat-10/webapps/";

# Tomcat 8
cp -f -v "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/archives_java/Test-framework-webapp.war"  "/Applications/apache-tomcat-8.5.87/webapps/";



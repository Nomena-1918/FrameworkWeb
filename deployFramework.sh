#!/bin/bash

# IMPORTANT : A exécuter après la compilation du projet framework (via l'IDE)

# Utiliser des variables bash

# Répertoire contenant les class
# shellcheck disable=SC2034
repertoireClass="/Users/nomena/TAFF-S4-PC-ITU/S4/Web_Dynamique/FrameworkWeb/out/production/Framework"

# Exportation project Framework -> framework.jar

# shellcheck disable=SC2164
cd $repertoireClass;

# shellcheck disable=SC2035
#jar cvmf  "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/Framework/manifest.mf"    "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/archives_java/framework.jar"  *;

# Déploiement vers Test-framework-webapp
#cp -f -v "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/archives_java/framework.jar"     "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/Test-framework-webapp/Test-framework/WEB-INF/lib/";





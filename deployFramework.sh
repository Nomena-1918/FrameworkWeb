#!/bin/bash

# IMPORTANT : A exécuter après la compilation du projet framework

# Exportation projet Framework -> framework.jar
# shellcheck disable=SC2164
cd "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/out/production/Framework";
# shellcheck disable=SC2035
jar cvmf  "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/Framework/manifest.mf"    "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/archives_java/framework.jar"  *;

# Déploiement vers test-framework
cp -r -f "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/archives_java/framework.jar"     "/Users/nomena/TAFF S4 - PC ITU/S4/Web_Dynamique/FrameworkWeb/Test-framework-webapp/WEB-INF/lib";


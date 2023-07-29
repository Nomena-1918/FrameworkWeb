#!/bin/bash

# Framework -> jar
bash deployFramework.sh --repertoireClass "binFramework"  --pathJar "Test-framework-webapp/lib"


# Préparation projet de test : dossier temporaire
# Test-framework -> war et déploiement tomcat
bash deployTomcat.sh;


echo "" ;
echo " Deployement done ! 🔥";
echo "" ;

#!/bin/bash

# Framework -> jar
bash deployFramework.sh --repertoireClass "binFramework" --manifestFile "Framework/manifest.mf" --pathJar "Test-framework-webapp/lib"


# Préparation projet de test : dossier temporaire
# Test-framework -> war et déploiement tomcat
bash deployTomcat.sh;


echo "" ;
echo " Deployement done ! 🔥";
echo "" ;

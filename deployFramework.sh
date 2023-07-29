#!/bin/bash

# Compilation du Framework
bash compileFramework.sh

# shellcheck disable=SC2034
repertoireClass="/Users/nomena/TAFF-S4-PC-ITU/S4/Web_Dynamique/FrameworkWeb/binFramework"
manifestFile="/Users/nomena/TAFF-S4-PC-ITU/S4/Web_Dynamique/FrameworkWeb/Framework/manifest.mf"
pathJar="/Users/nomena/TAFF-S4-PC-ITU/S4/Web_Dynamique/FrameworkWeb/archives_java"

#Clean
find $pathJar -mindepth 1 -delete

# Exportation project Framework -> framework.jar
# shellcheck disable=SC2164
cd $repertoireClass;

# shellcheck disable=SC2035
jar cmf  $manifestFile  $pathJar/framework.jar   *;

cp -r -f $pathJar/framework.jar "/Users/nomena/TAFF-S4-PC-ITU/S4/Web_Dynamique/FrameworkWeb/Test-framework-webapp/lib/"




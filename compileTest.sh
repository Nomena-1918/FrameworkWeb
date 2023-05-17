#!/bin/bash

# Specify the source and destination directories
source_dir="Test-framework-webapp/src"
destination_dir="/Users/nomena/TAFF-S4-PC-ITU/S4/Web_Dynamique/FrameworkWeb/binTest"
jar_file="/Users/nomena/TAFF-S4-PC-ITU/S4/Web_Dynamique/FrameworkWeb/archives_java/framework.jar"

# Find all Java files recursively and write their paths to a text file
find "$source_dir" -name "*.java" > java_files.txt

# Compile all Java files using 'javac' with the filelist option and classpath
javac -cp "$jar_file" @java_files.txt -d "$destination_dir"

# Clean up: remove the temporary file
rm java_files.txt

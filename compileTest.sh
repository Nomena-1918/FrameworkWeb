#!/bin/bash

# Specify the source and destination directories
source_dir="Test-framework-webapp/src"
destination_dir="binTest"
lib_dir="Test-framework-webapp/lib"

# Create a variable to hold the classpath
classpath=""

# Loop through all the jar files in the lib directory and add them to the classpath
for jar in "$lib_dir"/*.jar; do
  classpath="$classpath:$jar"
done
# Find all Java files recursively and write their paths to a text file
find "$source_dir" -name "*.java" > java_files.txt

#echo $classpath
#cat java_files.txt

# Compile all Java files using 'javac' with the filelist option and classpath
javac -cp "$classpath" @java_files.txt -d "$destination_dir"

# Clean up: remove the temporary file
rm java_files.txt

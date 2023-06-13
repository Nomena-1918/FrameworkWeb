#!/bin/bash

# Specify the source and destination directories
source_dir="Framework/src"
destination_dir="binFramework"
jar_file="Framework/javaee-api-6.0-6.jar"

# Find all Java files recursively and write their paths to a text file
find "$source_dir" -name "*.java" > java_files.txt

# Compile all Java files using 'javac' with the filelist option and classpath
javac -cp "$jar_file" @java_files.txt -d "$destination_dir" #-Xlint:unchecked

# Clean up: remove the temporary file
rm java_files.txt

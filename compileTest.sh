#!/bin/bash

# Specify the source and destination directories
source_dir="Test-framework-webapp/src"
destination_dir="binTest"
jar_file="Test-framework-webapp/lib/framework.jar:Test-framework-webapp/lib/gson-2.8.2.jar:Test-framework-webapp/lib/postgresql-42.6.0.jar"

# Find all Java files recursively and write their paths to a text file
find "$source_dir" -name "*.java" > java_files.txt

# Compile all Java files using 'javac' with the filelist option and classpath
javac -cp "$jar_file" @java_files.txt -d "$destination_dir"

# Clean up: remove the temporary file
rm java_files.txt

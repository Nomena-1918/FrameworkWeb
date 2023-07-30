#!/bin/bash

# Specify the source and destination directories
source_dir="Framework/src"
destination_dir="binFramework"

# Initialize the 'jar_file' variable
jar_file=""

# Directory containing the jar files
jar_dir="Framework/lib"

# Loop through each jar file in the directory
for file in "$jar_dir"/*.jar
do
    # Check if 'jar_file' is empty
    if [ -z "$jar_file" ]
    then
        # If 'jar_file' is empty, simply add the file path
        jar_file="$file"
    else
        # Otherwise, add a colon and the file path
        jar_file="$jar_file:$file"
    fi
done

#echo "jarfile framework :"$jar_file

# Find all Java files recursively and write their paths to a text file
find "$source_dir" -name "*.java" > java_files.txt

# Compile all Java files using 'javac' with the filelist option and classpath
javac -cp "$jar_file" @java_files.txt -d "$destination_dir" #-Xlint:unchecked

# Clean up: remove the temporary file
rm java_files.txt

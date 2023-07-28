#!/bin/bash

# Initialize our own variables
source_dir=""
destination_dir=""
lib_dir=""

# Parse command line options
while (( "$#" )); do
  case "$1" in
    --source_dir)
      source_dir="$2"
      shift 2
      ;;
    --destination_dir)
      destination_dir="$2"
      shift 2
      ;;
    --lib_dir)
      lib_dir="$2"
      shift 2
      ;;
    *)
      echo "Unknown option: $1" >&2
      exit 1
      ;;
  esac
done

# Check if variables are set
if [ -z "$source_dir" ] || [ -z "$destination_dir" ] || [ -z "$lib_dir" ]; then
  echo "Both --source_dir, --destination_dir and --lib_dir options must be provided" >&2
  exit 1
fi

# Create a variable to hold the classpath
classpath=""

# Loop through all the jar files in the lib directory and add them to the classpath
for jar in "$lib_dir"/*.jar; do
  classpath="$classpath:$jar"
done

# Find all Java files recursively and write their paths to a text file
find "$source_dir" -name "*.java" > java_files.txt

# Compile all Java files using 'javac' with the filelist option and classpath
javac -cp "$classpath" @java_files.txt -d "$destination_dir" #-Xlint:unchecked

# Clean up: remove the temporary file
rm java_files.txt

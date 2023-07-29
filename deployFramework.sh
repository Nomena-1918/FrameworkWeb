#!/bin/bash

# Compilation du Framework
bash compile.sh --source_dir "Framework/src" --destination_dir "binFramework" --lib_dir "Framework/lib"

# Initialize our own variables
repertoireClass=""
pathJar=""

# Parse command line options
while (( "$#" )); do
  case "$1" in
    --repertoireClass)
      repertoireClass="$2"
      shift 2
      ;;
    --manifestFile)
      manifestFile="$2"
      shift 2
      ;;
    --pathJar)
      pathJar="$2"
      shift 2
      ;;
    *)
      echo "Unknown option: $1" >&2
      exit 1
      ;;
  esac
done

# Check if variables are set
if [ -z "$repertoireClass" ]  || [ -z "$pathJar" ]; then
  echo "Both --repertoireClass and --pathJar options must be provided" >&2
  exit 1
fi

# Exportation project Framework -> framework.jar
jar cfe   $pathJar/framework.jar  $repertoireClass/*;




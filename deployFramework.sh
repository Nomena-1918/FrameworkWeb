#!/bin/bash

# Compilation du Framework
bash compile.sh --source_dir "Framework/src" --destination_dir "binFramework" --lib_dir "Framework/lib"

# Initialize our own variables
repertoireClass=""
manifestFile=""
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
if [ -z "$repertoireClass" ] || [ -z "$manifestFile" ] || [ -z "$pathJar" ]; then
  echo "Both --repertoireClass, --manifestFile and --pathJar options must be provided" >&2
  exit 1
fi

# shellcheck disable=SC2034
#Clean
#find $pathJar -mindepth 1 -delete

# Exportation project Framework -> framework.jar
jar cmf  $manifestFile  $pathJar/framework.jar   $repertoireClass/*;




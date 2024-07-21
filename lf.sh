#!/bin/bash

if [ $# -eq 0 ]; then
  ./gradlew --console=plain -q run
else
  ./gradlew --console=plain -q run --args="$*"
fi

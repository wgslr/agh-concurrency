#!/bin/bash -e

TIME=$(date '+%Y%m%dT%H%M%S')
PREFIX="$(pwd)/./times/${TIME}_"

cd out/production/lab4b/

for m in {10,10000,100000}; do
  for pk in {10,100,1000}; do
    java ProdConsApp $pk $m > ${PREFIX}${pk}_${m}
  done
done
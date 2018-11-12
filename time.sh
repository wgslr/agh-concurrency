#!/bin/bash -e

TMP=/tmp/time.csv
TIME=$(date '+%Y%m%dT%H%M%S')
PREFIX="$(pwd)/times/${TIME}/"
REPEATS=10

mkdir -p "$PREFIX"

cd out/production/lab4b/
for _ in {1..$REPEATS}; do
  for m in {10,10000,100000}; do
    for pk in {10,100,1000}; do
      java ProdConsApp $pk $m | grep -v start > $TMP
      cat $TMP >> ${PREFIX}${pk}_${m}.csv
    done
  done
done

rm $TMP || true

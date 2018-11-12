#!/bin/bash -e

TMP=/tmp/time.csv
TIME=$(date '+%Y%m%dT%H%M%S')
FILE="$(pwd)/times/${TIME}.csv"
REPEATS=10

#mkdir -p "$PREFIX"

cd out/production/lab4b/
for _ in {1..$REPEATS}; do
  for m in {10,10000,100000}; do
    for pk in {10,100,1000}; do
      echo Size $m Workers $pk: START
      java ProdConsApp $pk $m | grep -v start | cut -d ';' -f 2-5 > $TMP
      LINES=$(wc -l $TMP | cut -d ' ' -f 1)
      paste <(yes "$pk; $m; " | head -n $LINES) $TMP >> $FILE # ${PREFIX}${pk}_${m}.csv
      echo Size $m Workers $pk: DONE
    done
  done
done

rm $TMP || true

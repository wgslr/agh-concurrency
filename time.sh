#!/bin/bash -e

TMP=/tmp/time.csv
TIME=$(date '+%Y%m%dT%H%M%S')
FILE="$(pwd)/times/${TIME}.csv"
REPEATS=200
TIMEOUT=60


echo $TIME

cd out/production/lab4b/

for ((n=0;n<$REPEATS;n++)); do
  for m in {10,10000,100000}; do
    for pk in {10,100,1000}; do
      echo Size $m Workers $pk: START
      timeout $TIMEOUT java ProdConsApp $pk $m | grep -v start | cut -d ';' -f 2-5 > $TMP
      LINES=$(wc -l $TMP | cut -d ' ' -f 1)
      paste <(yes "$pk; $m; " | head -n $LINES) $TMP >> $FILE # ${PREFIX}${pk}_${m}.csv
      echo Size $m Workers $pk: DONE
    done
  done
done

rm $TMP || true

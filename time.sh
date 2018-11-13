#!/bin/bash -e

TMP=/tmp/time.csv
TMP2=/tmp/time2.csv
TIME=$(date '+%Y%m%dT%H%M%S')
FILE="$(pwd)/times/${TIME}.csv"
REPEATS=1
TIMEOUT=10


echo $TIME

cd out/production/lab4b/

for ((n=0;n<$REPEATS;n++)); do
  for m in {1000,10000,100000}; do
    for pk in {10,100,1000}; do
      echo Size $m Workers $pk: START
      timeout $TIMEOUT java ProdConsApp $pk $m  > $TMP || true
      cat $TMP | grep -v start | cut -d ';' -f 2-5 > $TMP2
      LINES=$(wc -l $TMP2 | cut -d ' ' -f 1)
      paste <(yes "$pk; $m; " | head -n $LINES) $TMP2 >> $FILE # ${PREFIX}${pk}_${m}.csv
      echo Size $m Workers $pk: DONE
    done
  done
done

rm $TMP || true

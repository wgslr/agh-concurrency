#!/bin/bash -e

TMP=/tmp/time.csv
TMP2=/tmp/time2.csv
TIME=$(date '+%Y%m%dT%H%M%S')
FILE="$(pwd)/times/${TIME}.csv"
REPEATS=200
TIMEOUT=60


echo $TIME

cd out/production/lab5/

for ((n=0;n<$REPEATS;n++)); do
  timeout $TIMEOUT java MandelbrotApp > $TMP || true
  cat $TMP | grep -v Start > $TMP2
  cat $TMP2 >> $FILE # ${PREFIX}${pk}_${m}.csv
  echo Iteration $n DONE $((REPEATS - n)) LEFT
done

rm $TMP || true

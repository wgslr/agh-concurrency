#!/usr/bin/fish

for i in (seq 1 10);
  for N in (seq 2 20);
    echo "i = $i; N = $N" 1>&2
    node phil5.js $N;
  end
end

#!/bin/sh

THE_CLASSPATH=
GridLock=GridLock.java
cd src
for i in `ls ../src/*.java`
  do
  THE_CLASSPATH=${THE_CLASSPATH}:${i}
done

java -classpath ".:${THE_CLASSPATH}" $GridLock

if [ $? -eq 0 ]
then
  echo "run worked!"
fi
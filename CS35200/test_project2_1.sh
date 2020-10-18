#!/bin/bash
proj='/homes/yoon161/Desktop/Purdue-Courses/CS35200/project2_1'
test='/homes/yoon161/Desktop/Purdue-Courses/CS35200/tests/project2_1_tests'

echo "****************************************"
echo "PROJECT 2_1 TEST"
echo "****************************************"
for file in $(ls $test); do
    echo "[$file]"
    $proj/interpret $test/$file
    echo "- - - - - - - - - - - - - - - - - - - - -"
done
#!/bin/bash
proj='/homes/yoon161/Desktop/Purdue-Courses/CS35200/project2_3'
test='/homes/yoon161/Desktop/Purdue-Courses/CS35200/tests/project2_3_tests'

echo "****************************************"
echo "PROJECT 2_3 TEST"
echo "****************************************"
# for file in $(ls $test); do
#     echo "[$file]"
#     $proj/interpret $test/$file
#     echo "- - - - - - - - - - - - - - - - - - - - -"
# done

file='part2.java'
echo "[$file]"
$proj/interpret $test/$file
echo "- - - - - - - - - - - - - - - - - - - - -"

file='curr.java'
echo "[$file]"
$proj/interpret $test/$file
echo "- - - - - - - - - - - - - - - - - - - - -"

echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ POSTIVE TEST CASES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
for file in $(ls $test/pos); do
    echo "[$file]"
    $proj/interpret $test/pos/$file
    echo "- - - - - - - - - - - - - - - - - - - - -"
done

echo "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ NEGATIVE TEST CASES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
for file in $(ls $test/neg); do
    echo "[$file]"
    $proj/interpret $test/neg/$file
    echo "- - - - - - - - - - - - - - - - - - - - -"
done
#!/bin/bash

test_num=$1

echo "TEST FRAMEWORK"
echo "==================== bftpd_{clean} ===================="
python framework.py -p ./bin/bftpd_clean -f ./testing_environment/testcases/test_input_$test_num.txt

for i in {1..10}
do
    echo "==================== bftpd_{$i} ===================="
    python framework.py -p ./bin/bftpd_$i -f ./testing_environment/testcases/test_input_$test_num.txt
done

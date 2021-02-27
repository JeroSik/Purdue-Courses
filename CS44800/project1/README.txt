CS 448 - Spring 2020
Name: Youngsik Yoon
Team Member: Jeremy Chang
Purdue ID: 0029846135

# Project 1:  Buffer Manager
The structure of the client and engine was not modified.

## Running Test Files
There are two test files created. Both are located in the buffer folder for the SimpleDB Engine.
+ /project1/SimpleDB Engine/src/simpledb/buffer/PinTest.java
+ /project1/SimpleDB Engine/src/simpledb/buffer/QueryTest.java

Due to a Piazza post about printing instructions, I have commented out the print statements in the two tests. 
+ For PinTest.java, the print statement is on line 25.
+ For QueryTest.java, the print statement is on lines 20, 21 and lines 46, 47.

### PinTest.java
No additional setup is required other than removing the comments from the print statements. Users should just be able to run the PinTest within the buffer folder with no arugments necessary. After every run, we deleted the "pin_test" folder created by the test. This ensured consistency among the tests.
### QueryTest.java
For QueryTest.java, please change the *n* value on line 13 to change the number of table entries. On the report we used the *n* values 1000, 2000, 3000, 4000, and 5000. After every run, we deleted the "querytestdb" folder created by the test. This ensured consistency among the tests.

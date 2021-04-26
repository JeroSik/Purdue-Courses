CS 448 - Spring 2021
Name: Youngsik Yoon
Team Member: Jeremy Chang
Purdue ID: 0029846135

# Project 3: Deadlock Detection and Prevention
The structure of the client and engine was not modified.

## Running Test Files
There are two testcases included: WoundWaitTest.java and DeadlockTest.java. WoundWaitTest.java is the same as the given test (WaitDieTest.java), but the comments and the sleep location have been changed to reflect the wound-wait scheme over the wait-die scheme.
+ /project3/SimpleDB Engine/src/simpledb/tx/concurrency/WoundWaitTest.java
A new test, DeadlockTest, was added which is another testcase to evaluate and compare our projects' schemes.
+ /project3/SimpleDB Engine/src/simpledb/tx/DeadlockTest.java

Before running any test file, refresh the cache before computing to ensure consistent results.

## Files Modified
The following files were modified:
+ /project2/SimpleDB Engine/src/simpledb/tx/DeadlockTest.java
+ /project2/SimpleDB Engine/src/simpledb/tx/Transaction.java
+ /project2/SimpleDB Engine/src/simpledb/tx/concurrency/ConcurrencyMgr.java
+ /project2/SimpleDB Engine/src/simpledb/tx/concurrency/LockTable.java
+ /project2/SimpleDB Engine/src/simpledb/tx/concurrency/WoundWaitTest.java

Details for changes can be found in the lab report.

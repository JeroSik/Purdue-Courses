CS 448 - Spring 2020
Name: Youngsik Yoon
Team Member: Jeremy Chang
Purdue ID: 0029846135

# Project 2: Query Optimization
The structure of the client and engine was not modified.

## Running Test Files
The two given tests, PlannerTest2 and PlannerTest3, were unchanged. A new test, PlannerTest4, was added which contains the test with 3 tables and 2 joins.
+ /project2/SimpleDB Engine/src/simpledb/plan/PlannerTest4.java

Before running any test file, the cache must be refreshed before computing the cost every time.

## Files Modified
The following files were modified:
+ /project2/SimpleDB Engine/src/simpledb/query/Predicate.java
+ /project2/SimpleDB Engine/src/simpledb/query/ProductScan2.java
+ /project2/SimpleDB Engine/src/simpledb/query/Term.java
+ /project2/SimpleDB Engine/src/simpledb/plan/BasicQueryPlanner.java
+ /project2/SimpleDB Engine/src/simpledb/plan/OptimizedProductPlan.java
+ /project2/SimpleDB Engine/src/simpledb/plan/PlannerTest4.java
+ /project2/SimpleDB Engine/src/simpledb/plan/ProductPlan2.java

Details for changes can be found in the lab report.

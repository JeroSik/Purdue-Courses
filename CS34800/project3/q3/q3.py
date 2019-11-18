##########
# q3.py
# expects two command line arguments:
#  - HDFS dir containing users_utf8.dat, movies_utf8.dat, ratings_utf8.dat
#    (no trailing forward-slash)
#  - HDFS dir to write results
##########

from pyspark import SparkConf, SparkContext
from pyspark.sql import *
from pyspark.sql.types import *
import sys

# create a spark context;
# these two lines not needed if running pyspark shell;
conf = SparkConf().setAppName("spark query")
sc = SparkContext(conf=conf)

# we will read tables from HDFS and register them with Spark SQL;
# to do this, we need a Spark SQL context;
sqlContext = SQLContext(sc)

# table users;
lines = sc.textFile(sys.argv[1] + "/users_utf8.dat")
parts = lines.map(lambda l: l.split("::"))
parts_stripped = parts.map(lambda p: (p[0], p[1], p[2], p[3], p[4].strip()))
schemaString = "userid gender age occupation zipcode"
fields = [
    StructField(field_name, StringType(), True) for field_name in schemaString.split()
]
schema = StructType(fields)
schemaDF = sqlContext.createDataFrame(parts_stripped, schema)
schemaDF.registerTempTable("users")

# table movies;
lines = sc.textFile(sys.argv[1] + "/movies_utf8.dat")
parts = lines.map(lambda l: l.split("::"))
parts_stripped = parts.map(lambda p: (p[0], p[1], p[2].strip()))
schemaString = "movieid title genres"
fields = [
    StructField(field_name, StringType(), True) for field_name in schemaString.split()
]
schema = StructType(fields)
schemaDF = sqlContext.createDataFrame(parts_stripped, schema)
schemaDF.registerTempTable("movies")

# table ratings;
lines = sc.textFile(sys.argv[1] + "/ratings_utf8.dat")
parts = lines.map(lambda l: l.split("::"))
parts_stripped = parts.map(lambda p: (p[0], p[1], p[2], p[3].strip()))
schemaString = "userid movieid rating timestamp"
fields = [
    StructField(field_name, StringType(), True) for field_name in schemaString.split()
]
schema = StructType(fields)
schemaDF = sqlContext.createDataFrame(parts_stripped, schema)
schemaDF.registerTempTable("ratings")

query = (
    "select occupation, count(*) as num "
    + "from users "
    + "group by occupation"
)
results = sqlContext.sql(query)

# write data back to HDFS;
# repartition down to 10 partitions to reduce number of HDFS files written;
results.rdd.map(lambda p: str(p.occupation) + "::" + str(p.num)).repartition(10).saveAsTextFile(sys.argv[2])


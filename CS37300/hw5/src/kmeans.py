"""
Name: Youngsik Yoon
Email: yoon161@purdue.edu
Due date: April 30, 2019
"""

# Include neseccary imports
import numpy as np
import pandas as pd
import math
import sys

class Kmeans(object):
  def __init__(self, df, k, distCalc):
    self.c = self.m = None
    self.df = df
    self.k = k
    self.distCalc = distCalc

  def findDistance(self, point, centroid):
    dist = 0
    if self.distCalc == "Euclidean":
      for i in range(len(self.df.columns)):
        dist += (centroid[i] - point[i]) ** 2
      dist = math.sqrt(dist)
    elif self.distCalc == "Manhattan":
      for i in range(len(self.df.columns)):
        dist += abs(centroid[i] - point[i])

    return dist

  def reassignPoints(self):
    # Go through each row in dataframe
    df_index = 0
    for i, sample in self.df.iterrows():
      # Calculate distance from each centroid to sample point
      minDist = sys.maxint
      centroid_index = 0
      for j, centroid in self.c.iterrows():
        tmpDist = self.findDistance(sample, centroid)
        if (tmpDist < minDist):
          self.m[df_index] = centroid_index
          minDist = tmpDist
        centroid_index += 1
      df_index += 1

  def relocateCentroids(self):
    temp_c = self.c.copy()
    for c_ID in range(self.k):
      # Get subset of columns corresponding to centroid ID
      c_index = np.where(self.m==c_ID)
      c_df = self.df.iloc[c_index]
      # Go through each attribute and find mean
      for att in self.c.columns:
        temp_c.at[self.c.index[c_ID], att] = c_df[att].mean()
    return temp_c

  def convergence(self, temp_c):
    #TODO find better convergence
    return self.c.equals(temp_c)

  def cluster(self):
    # Initialize cluster representatives c by randomly choosing k data points
    self.c = self.df.sample(n=self.k)

    # Initialize cluster membership vector m
    self.m = np.zeros(len(self.df.index))
    
    # Repeat until convergence
    while True:
      print(self.c)

      # Data Assignment
      self.reassignPoints()

      # Relocate the centroids
      temp_c = self.relocateCentroids()

      # Check for convergence
      if self.convergence(temp_c):
        print(self.c)
        break
      else:
        self.c = temp_c

  def printScore(self):
    score = 0
    for c_ID in range(self.k):
      r_k = self.c.iloc[c_ID]
      c_index = np.where(self.m==c_ID)
      c_df = self.df.iloc[c_index]
      for i, x_i in c_df.iterrows():
        score += self.findDistance(x_i, r_k) ** 2
      
    print("WC-SSE={}".format(score))

  def printClusters(self):
    for cluster_num in range(1, self.k + 1):
      row = self.c.iloc[cluster_num - 1]
      print("Centroid{}=[{},{},{},{}]".format(cluster_num, row["latitude"], row["longitude"], row["reviewCount"], row["checkins"]))


if __name__ == "__main__":
  pd.options.mode.chained_assignment = None

  # Check if there are enough arguments
  if len(sys.argv) < 4:
    print("The correct arguments were not passed. Please try again.")
    sys.exit()

  # Parse through system arguments: training data filename, k-value, and clustering option
  trainingDataFileName = sys.argv[1]
  K = int(sys.argv[2])
  clusteringOption = int(sys.argv[3])

  # Read from csv file
  df_data_file = pd.read_csv("./../data/given/"+trainingDataFileName, delimiter=',', index_col=None, engine='python')
  
  # Get appropriate parameters from the clustering option
  df = df_data_file[['latitude', 'longitude', 'reviewCount', 'checkins']]
  distCalc = "Euclidean"
  if clusteringOption == 2:
    sub_df1 = df[['latitude', 'longitude']]
    sub_df2 = df[['reviewCount', 'checkins']].apply(np.log)
    df = pd.concat([sub_df1, sub_df2], axis=1)
  elif clusteringOption == 3:
    sub_df1 = df[['latitude', 'longitude']]
    sub_df2 = df[['reviewCount', 'checkins']]
    sub_df2 = sub_df2 - sub_df2.mean()
    sub_df2 = sub_df2 / sub_df2.std()
    df = pd.concat([sub_df1, sub_df2], axis=1)
  elif clusteringOption == 4:
    distCalc = "Manhattan"
  elif clusteringOption == 5:
    df = df.sample(frac=0.03)
  elif clusteringOption != 1:
    print("The clustering option is not valid. Please try again.")
    sys.exit()

  # Initialize K-means clustering
  kmeans = Kmeans(df, K, distCalc)
  kmeans.cluster()

  # Print results
  kmeans.printScore()
  kmeans.printClusters()
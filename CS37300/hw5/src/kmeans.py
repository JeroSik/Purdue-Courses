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
import time

class Kmeans(object):
  def __init__(self, df, k, distCalc):
    self.c = np.array(df.sample(n=k, random_state=1))
    self.m = np.zeros(len(df.index))
    self.df = df
    self.k = k
    self.distCalc = distCalc

  def findDistance(self, centroid, point):
    dist = 0
    if self.distCalc == "Euclidean":
      dist = np.sqrt(np.sum([np.power(centroid[0] - point[0],2), np.power(centroid[1] - point[1],2), np.power(centroid[2] - point[2],2), np.power(centroid[3] - point[3],2)]))
    elif self.distCalc == "Manhattan":
      for i in range(len(self.df.columns)):
        dist += abs(centroid[i] - point[i])

    return dist
  
  def findScore(self):
    score = 0
    for c_ID in range(self.k):
      r_k = self.c[c_ID]
      c_index = np.where(self.m==c_ID)
      c_df = self.df.iloc[c_index]
      for i, x_i in c_df.iterrows():
        score += self.findDistance(r_k, x_i) ** 2
    return score

  def cluster(self):
    start = time.time()
    prev_score = np.Inf

    while True:
      curr_score = 0

      # Go through each row in dataframe
      df_index = 0
      for i, sample in self.df.iterrows():
        data_point = np.array([sample["latitude"], sample["longitude"], sample["reviewCount"], sample["checkins"]])

        minDist = np.Inf
        centroid_index = 0
        for index in range(self.k):
          tmpDist = self.findDistance(self.c[index], sample)
          if (tmpDist < minDist):
            centroid_index = index
            minDist = tmpDist

        self.m[df_index] = centroid_index
        df_index += 1

      # Relocate the centroids
      for c_ID in range(self.k):
        # Get subset of columns corresponding to centroid ID
        c_index = np.where(self.m==c_ID)
        c_df = self.df.iloc[c_index]

        # Go through each attribute and find mean
        self.c[c_ID][0] = c_df["latitude"].mean()
        self.c[c_ID][1] = c_df["longitude"].mean()
        self.c[c_ID][2] = c_df["reviewCount"].mean()
        self.c[c_ID][3] = c_df["checkins"].mean()

      curr_score = self.findScore()
      end = time.time()
      if prev_score - curr_score < 1:
        break
      elif end - start > (7.25 * 60):
        break
      else:
        prev_score = curr_score

  def printScore(self):
    score = self.findScore()
    print("WC-SSE={}".format(score))

  def printClusters(self):
    for cluster_num in range(1, self.k + 1):
      row = self.c[cluster_num - 1]
      print("Centroid{}=[{},{},{},{}]".format(cluster_num, row[0], row[1], row[2], row[3]))

if __name__ == "__main__":
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
    df = df - df.mean()
    df = df / df.std()
  elif clusteringOption == 4:
    distCalc = "Manhattan"
  elif clusteringOption == 5:
    df = df.sample(frac=0.06)
  elif clusteringOption != 1:
    print("The clustering option is not valid. Please try again.")
    sys.exit()

  # Initialize K-means clustering
  kmeans = Kmeans(df, K, distCalc)
  kmeans.cluster()

  # Print results
  kmeans.printScore()
  kmeans.printClusters()
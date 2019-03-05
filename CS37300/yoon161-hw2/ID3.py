"""
Part 1 Decision Trees

Name: Youngsik Yoon
Collaborators: Julian Haresco, Harjas Monga, Aditya Subramaniam
Email: yoon161@purdue.edu
Due date: March 4, 2019
"""

# Include neseccary imports
import numpy as np
import pandas as pd
import sys
import os
import queue

# Calculate the entropy of a given frequencies
def entropy(freqs):
    all_freq = sum(freqs)
    entropy = 0
    if all_freq == 0:
        return 0
    for fq in freqs:
        prob = fq * 1.0 / all_freq
        if abs(prob) > 1e-8:
            entropy += -prob * np.log2(prob)
    return entropy
    
# Calculate information gain of given data
def infor_gain(before_split_freqs, after_split_freqs):
    gain = entropy(before_split_freqs)
    overall_size = sum(before_split_freqs)
    for freq in after_split_freqs:
        ratio = sum(freq) * 1.0 / overall_size
        gain -= ratio * entropy(freq)
    return gain

# Find max information gain of given dataframe
def maxInfoGain(df):
    max = maxSplitVal = -1
    maxAtt = ""

    # Find dead and alive ratio
    alive = df[df.survived == 1]
    dead = df[df.survived == 0]
    before_split = [len(dead), len(alive)]

    # Find attribute with max attribute
    for att in df:
        # Skip survived
        if att == 'survived':
            continue

        uniqueVals = df[att].unique()
        uniqueVals.sort()

        # Find split value medium value with greatest information gain
        for i in range(0, len(uniqueVals) - 1):
            mid = (uniqueVals[i] + uniqueVals[i + 1]) / 2

            after_split = []
            after_split.append([len(dead[dead[att] < mid]), len(alive[alive[att] < mid])])
            after_split.append([len(dead[dead[att] >= mid]), len(alive[alive[att] >= mid])])
            temp = infor_gain(before_split, after_split)

            if temp > max:
                max = temp
                maxSplitVal = mid
                maxAtt = att

    return max, maxAtt, maxSplitVal

# Node object for binary tree
class Node(object):
    def __init__(self, attribute, df, infoGain, samples, splitVal, depth):
        self.left = self.right = self.survived = None
        self.df = df
        self.attribute = attribute
        self.infoGain = infoGain
        self.samples = samples
        self.splitVal = splitVal
        self.depth = depth
        pass

    # Helper function to print node
    def print(self):
        print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
        print("ATTRIBUTE: " + str(self.attribute))
        print("Information gain: " + str(self.infoGain))
        print("Sample size: " + str(self.samples))
        print("Split value: " + str(self.splitVal))
        print("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

# Decision tree object
class Tree(object):
    def __init__(self, train_file, test_file, model, train_set_size, validation_set_size, max_depth, min_split):
        self.df = train_file
        self.test = test_file
        self.model = model
        self.train_set_size = train_set_size
        self.validation_set_size = validation_set_size
        self.max_depth = max_depth
        self.min_split = min_split

    # Builds vanilla tree
    def build(self):
        self.numNodes = 0
        numPoints = round((self.train_set_size / 100) * self.df.shape[0])
        cutdf = self.df[:numPoints]
        infoGain, att, splitVal = maxInfoGain(cutdf)
        self.root = Node(att, cutdf, infoGain, len(cutdf), splitVal, 0)
        self.split(self.root)

    # Recursive split method to build tree
    def split(self, node):
        # Get dataframe of potential left and right nodes
        leftDF = node.df[node.df[node.attribute] < node.splitVal]
        rightDF = node.df[node.df[node.attribute] >= node.splitVal]

        #Base cases
        stopLeft = stopRight = False
        if self.isLeaf(leftDF) or leftDF.shape[0] < self.min_split:
            node.left = Node("LEFT TERMINAL", None, None, None, None, node.depth + 1)
            node.left.survived = leftDF['survived'].value_counts().idxmax()
            stopLeft = True
        if self.isLeaf(rightDF) or rightDF.shape[0] < self.min_split:
            node.right = Node("RIGHT TERMINAL", None, None, None, None, node.depth + 1)
            node.right.survived = rightDF['survived'].value_counts().idxmax()
            stopRight = True
        if node.depth == self.max_depth:
            node.left = Node("LEFT TERMINAL", None, None, None, None, node.depth + 1)
            node.left.survived = leftDF['survived'].value_counts().idxmax()
            node.right = Node("RIGHT TERMINAL", None, None, None, None, node.depth + 1)
            node.right.survived = rightDF['survived'].value_counts().idxmax()
            return

        # Build left or right node
        if not stopLeft:
            leftIG, leftAtt, leftSV = maxInfoGain(leftDF)
            node.left = Node(leftAtt, leftDF, leftIG, len(leftDF), leftSV, node.depth + 1)
            self.split(node.left)
        if not stopRight:
            rightIG, rightAtt, rightSV = maxInfoGain(rightDF)
            node.right = Node(rightAtt, rightDF, rightIG, len(rightDF), rightSV, node.depth + 1)
            self.split(node.right)
        return

    # Builds prune tree
    def reduceError(self):
        startPoint = round((self.train_set_size / 100) * self.df.shape[0])
        endPoint = startPoint + round((self.validation_set_size / 100) * self.df.shape[0])
        valid_test = self.df[startPoint:endPoint]
        accuracy = self.calcAccuracy(valid_test)
        self.prune(self.root, self.root, valid_test, accuracy)

    # Recursive pruning function
    def prune(self, root, node, validation_set, min_acc):
        if node.left.survived is not None and node.right.survived is not None:
            left = node.left
            right = node.right
            curr = node
            node.left = None
            node.right = None
            node.survived = node.df['survived'].value_counts().idxmax()
            node = Node("TERMINAL", None, None, None, None, node.depth - 1)

            
            newAcc = self.calcAccuracy(validation_set)
            if newAcc >= min_acc:
                self.prune(root, root, validation_set, newAcc)
            else:
                node.left = left
                node.right = right
                node = curr
        else:
            if node.left is not None and node.left.survived is None:
                self.prune(root, node.left, validation_set, min_acc)
            if node.right is not None and node.right.survived is None:
                self.prune(root, node.right, validation_set, min_acc) 

    # Helper function to detect if node is a leaf node    
    def isLeaf(self, df):
        if len(df['survived'].unique()) == 1:
            return True
        count = 0
        for col in df:
            if len(df[col].unique()) == 1:
                count += 1
        if count == len(df.columns) - 1:
            return True
        return False

    # Help function to print level-order tree
    def print(self):
        explored = []
        q = [self.root]

        while len(q) != 0:
            node = q.pop(0)
            if node not in explored:
                explored.append(node)
                node.print()
                if node.left is not None:
                    q.append(node.left)
                if node.right is not None:
                    q.append(node.right)


    def nodeCount(self):
        explored = []
        q = [self.root]

        while len(q) != 0:
            node = q.pop(0)
            self.numNodes += 1
            if node not in explored:
                explored.append(node)
                if node.left is not None:
                    q.append(node.left)
                if node.right is not None:
                    q.append(node.right)

        return self.numNodes

    # Calculate accuracy of tree
    def calcAccuracy(self, df):
        accuracy = 0
        for i in range(0, df.shape[0]):
            row = df.iloc[i]
            status = self.validate(self.root, row)
            if status == row['survived']:
                accuracy += 1
        return accuracy / df.shape[0]
    
    # Recursive method to traverse nodes down tree
    def validate(self, node, row):
        if node.survived is not None:
            return int(node.survived)
        else:
            if row[node.attribute] < node.splitVal:
                return self.validate(node.left, row)
            else:
                return self.validate(node.right, row)

if __name__ == "__main__":
	# Parse arguments from input
    train_file = test_file = model = train_set_size = validation_set_size = max_depth = None
    min_split = -1
    train_file = sys.argv[1]
    test_file = sys.argv[2]
    model = sys.argv[3]
    train_set_size = int(sys.argv[4])

    # Capture additional arguments if necessary
    if model == "depth":
        validation_set_size = int(sys.argv[5])
        max_depth = int(sys.argv[6])
    if model == "min_split":
        validation_set_size = int(sys.argv[5])
        min_split = int(sys.argv[6])
    if model == "prune":
        validation_set_size = int(sys.argv[5])
    
    # Combine data file and label file
    df_data_file = pd.read_csv(train_file, delimiter = ',', index_col=None, engine='python')
    df_label_file = pd.read_csv(train_file.replace(".data", ".label"), delimiter = ',', index_col=None, engine='python')
    df = pd.concat([df_data_file, df_label_file], axis = 1)
    test_data_file = pd.read_csv(test_file, delimiter = ',', index_col=None, engine='python')
    test_label_file = pd.read_csv(test_file.replace(".data", ".label"), delimiter = ',', index_col=None, engine='python')
    test = pd.concat([test_data_file, test_label_file], axis = 1)

    # build vanilla decision tree
    tree = Tree(df, test, model, train_set_size, validation_set_size, max_depth, min_split)
    tree.build()

    # Prune tree is necessary
    if model == "prune":
        tree.reduceError()

    # predict on testing set & evaluate the testing accuracy
    accuracy = tree.calcAccuracy(df)
    print("Train set accuracy: %.4f" % accuracy)

    # Print additional information is necessary
    if model == "depth" or model == "min_split":
        startPoint = round((train_set_size / 100) * df.shape[0])
        endPoint = startPoint + round((validation_set_size / 100) * df.shape[0])
        valid_test = df[startPoint:endPoint]
        accuracy = tree.calcAccuracy(valid_test)
        print("Validation set accuracy: %.4f" % accuracy)

    accuracy = tree.calcAccuracy(test)
    print("Test set accuracy: %.4f" % accuracy)

#!/usr/bin/env python
# coding: utf-8
"""© 2019 Rajkumar Pujari All Rights Reserved

- Original Version

    Author: Rajkumar Pujari
    Last Modified: 03/12/2019

"""

from classifier import BinaryClassifier
from utils import get_feature_vectors
import numpy as np
import random

class Perceptron(BinaryClassifier):
    def __init__(self, args):
        # Parse through the configurations
        self.bin_feats = args.bin_feats
        self.f_dim = args.f_dim
        self.lr = args.lr
        self.num_iter = args.num_iter
        self.vocab_size = args.vocab_size
    
    # Helper function to return sign of number
    def sign(self, number):
        if number > 0:
            return 1
        else:
            return -1
        
    def fit(self, train_data):
        random.seed(5)

        # Initialize weight vector and bias as zero
        self.w = np.zeros(self.f_dim)
        self.b = 0

        # Get feature vector from training data
        feature = get_feature_vectors(train_data[0], self.bin_feats)

        # Iterate through the number specified in configurations
        for iter in range(self.num_iter):
            # Shuffle the reviews to allow for unsorted reviews
            indexList = list(range(len(feature)))
            random.shuffle(indexList)

            # Go through each sample in training data
            for i in indexList:
                # Convert feature list to numpy array
                x = np.asarray(feature[i])

                # Calculte predicted y value and gold value
                y_prime = self.sign(np.dot(self.w, x) + self.b)
                y = train_data[1][i]

                # If y values differ, then update weight vector
                if y != y_prime:
                    self.b = self.b + self.lr * y
                    self.w = self.w + self.lr * y * x
        
    def predict(self, test_x):
        # Initialize prediction list
        pred_y = []

        # Get feature vector from testing data
        feature = get_feature_vectors(test_x, self.bin_feats)

        # Go through each sample
        for sample in feature:
            # Convert sample list to numpy array
            x = np.asarray(sample)

            # Calculte predicted y value
            y_prime = self.sign(np.dot(self.w, x) + self.b)

            # Append prediction to list
            pred_y.append(y_prime)

        # Return predicted list
        return pred_y

class AveragedPerceptron(BinaryClassifier):
    def __init__(self, args):
        # Parse through the configurations
        self.bin_feats = args.bin_feats
        self.f_dim = args.f_dim
        self.lr = args.lr
        self.num_iter = args.num_iter
        self.vocab_size = args.vocab_size

    # Helper function to return sign of number
    def sign(self, number):
        if number > 0:
            return 1
        else:
            return -1
                
    def fit(self, train_data):
        random.seed(5)

        # Initialize weight vector and bias as zero and survival counter as 1
        self.w = np.zeros(self.f_dim)
        self.b = 0
        survival = 1

        # Get feature vector from training data
        feature = get_feature_vectors(train_data[0], self.bin_feats)

        # Iterate through the number specified in configurations
        for iter in range(self.num_iter):
            # Shuffle the reviews to allow for unsorted reviews
            indexList = list(range(len(feature)))
            random.shuffle(indexList)

            # Go through each sample in training data
            for i in indexList:
                # Convert feature list to numpy array
                x = np.asarray(feature[i])

                # Calculte predicted y value and gold value
                y_prime = self.sign(np.dot(self.w, x) + self.b)
                y = train_data[1][i]

                # If y values differ, then update weight vector, otherwise update survival counter
                if y != y_prime:
                    self.b = self.b + self.lr * y
                    w_prime = self.w + self.lr * y * x
                    self.w = ((np.dot(survival, self.w)) + w_prime) / (survival + 1)
                    survival = 1
                else:
                    survival = survival + 1
        
    def predict(self, test_x):
        # Initialize prediction list
        pred_y = []

        # Get feature vector from testing data
        feature = get_feature_vectors(test_x, self.bin_feats)

        # Go through each sample
        for sample in feature:
            # Convert sample list to numpy array
            x = np.asarray(sample)

            # Calculte predicted y value
            y_prime = self.sign(np.dot(self.w, x) + self.b)

            # Append prediction to list
            pred_y.append(y_prime)

        # Return predicted list
        return pred_y
#!/usr/bin/env python
# coding: utf-8
"""

- Original Version

    Author: Susheel Suresh
    Last Modified: 04/03/2019

"""

from classifier import BinaryClassifier
from utils import get_feature_vectors
import numpy as np
import math
import random

class SGDHinge(BinaryClassifier):
    def __init__(self, args):
        self.bin_feats = args.bin_feats
        self.f_dim = args.f_dim
        self.la = args.la
        self.lr_sgd = args.lr_sgd
        self.num_iter = args.num_iter
        self.vocab_size = args.vocab_size
        
    def fit(self, train_data):
        random.seed(5)

        # Get feature vector from testing data
        feature_vector = get_feature_vectors(train_data[0], self.bin_feats)

        # Initialize weights and bias
        self.w = np.zeros(self.f_dim)
        self.b = 0

        # Iterate through set number of times
        for iter_count in range(len(feature_vector)):
            # Shuffle the reviews to allow for unsorted reviews
            indexList = list(range(len(feature_vector)))
            random.shuffle(indexList)

            # Initialize gradient weights and gradient bias
            g_w = np.zeros(self.f_dim)
            g_b = 0

            # Randomly select a sample
            i = random.randint(0, len(feature_vector) - 1)
            x = np.asarray(feature_vector[i])
            y = train_data[1][i]

            # Update gradient weights and bias
            if y * (np.dot(self.w, x)) <= 1:
                g_w = g_w - [y * x_i for x_i in x]
                g_b = g_b - y

            # Update weights and bias
            self.w = self.w - self.lr_sgd * g_w
            self.b = self.b - self.lr_sgd * g_b 

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
            if (np.dot(self.w, x) + self.b) > 0:
                y_prime = 1
            else:
                y_prime = -1

            # Append prediction to list
            pred_y.append(y_prime)

        # Return predicted list
        return pred_y

class SGDLog(BinaryClassifier):
    def __init__(self, args):
        self.bin_feats = args.bin_feats
        self.f_dim = args.f_dim
        self.la = args.la
        self.lr_sgd = args.lr_sgd
        self.num_iter = args.num_iter
        self.vocab_size = args.vocab_size
        
    def fit(self, train_data):
        random.seed(5)

        # Get feature vector from testing data
        feature_vector = get_feature_vectors(train_data[0], self.bin_feats)

        # Initialize weights and bias
        self.w = np.zeros(self.f_dim)
        self.b = 0

        # Iterate through set number of times
        for iter_count in range(len(feature_vector)):
            # Shuffle the reviews to allow for unsorted reviews
            indexList = list(range(len(feature_vector)))
            random.shuffle(indexList)

            # Initialize gradient weights and gradient bias
            g_w = np.zeros(self.f_dim)
            g_b = 0

            # Randomly select a sample
            i = random.randint(0, len(feature_vector) - 1)
            x = np.asarray(feature_vector[i])
            y = train_data[1][i]
            if train_data[1][i] == -1:
                y = 0

            # Find sigmoid function
            z = np.dot(self.w, x) + self.b
            sigmoid = 1 / (1 + math.e ** -z)

            # Update gradient weights and bias
            # g_w = g_w - np.multiply((y - sigmoid), x)
            # g_b = g_b - (y - sigmoid)
            g_w = np.subtract(g_w, np.multiply((y - sigmoid), x))
            g_b = np.subtract(g_b, (y - sigmoid))

            # Update weights and bias
            self.w = self.w - self.lr_sgd * g_w
            self.b = self.b - self.lr_sgd * g_b
        
    def predict(self, test_x):
        # Initialize prediction list
        pred_y = []

        # Get feature vector from testing data
        feature = np.array(get_feature_vectors(test_x, self.bin_feats))
    
        # Go through each sample
        for i, x in enumerate(feature):
            # Find sigmoid function
            z = np.dot(self.w, x) + self.b
            sigmoid = 1 / (1 + math.e ** -z)

            # Calculte predicted y value
            if sigmoid > 0.5:
                y_prime = 1
            else:
                y_prime = -1
        
            # Append prediction to list
            pred_y.append(y_prime)

        # Return predicted list
        return pred_y

class SGDHingeReg(BinaryClassifier):
    def __init__(self, args):
        self.bin_feats = args.bin_feats
        self.f_dim = args.f_dim
        self.la = args.la
        self.lr_sgd = args.lr_sgd
        self.num_iter = args.num_iter
        self.vocab_size = args.vocab_size
        
    def fit(self, train_data):
        random.seed(5)

        # Get feature vector from testing data
        feature_vector = get_feature_vectors(train_data[0], self.bin_feats)

        # Initialize weights and bias
        self.w = np.zeros(self.f_dim)
        self.b = 0

        # Iterate through set number of times
        for iter_count in range(len(feature_vector)):
            # Shuffle the reviews to allow for unsorted reviews
            indexList = list(range(len(feature_vector)))
            random.shuffle(indexList)

            # Initialize gradient weights and gradient bias
            g_w = np.zeros(self.f_dim)
            g_b = 0

            # Randomly select a sample
            i = random.randint(0, len(feature_vector) - 1)
            x = np.asarray(feature_vector[i])
            y = train_data[1][i]

            # Update gradient weights and bias
            if y * (np.dot(self.w, x)) <= 1:
                g_w = g_w - [y * x_i for x_i in x]
                g_b = g_b - y

            # Update weights and bias
            g_w = np.add(g_w, self.la * self.w)
            self.w = self.w - self.lr_sgd * g_w
            self.b = self.b - self.lr_sgd * g_b 
        
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
            if (np.dot(self.w, x) + self.b) > 0:
                y_prime = 1
            else:
                y_prime = -1

            # Append prediction to list
            pred_y.append(y_prime)

        # Return predicted list
        return pred_y

class SGDLogReg(BinaryClassifier):
    def __init__(self, args):
        self.bin_feats = args.bin_feats
        self.f_dim = args.f_dim
        self.la = args.la
        self.lr_sgd = args.lr_sgd
        self.num_iter = args.num_iter
        self.vocab_size = args.vocab_size
        
    def fit(self, train_data):
        random.seed(5)

        # Get feature vector from testing data
        feature_vector = get_feature_vectors(train_data[0], self.bin_feats)

        # Initialize weights and bias
        self.w = np.zeros(self.f_dim)
        self.b = 0

        # Iterate through set number of times
        for iter_count in range(len(feature_vector)):
            # Shuffle the reviews to allow for unsorted reviews
            indexList = list(range(len(feature_vector)))
            random.shuffle(indexList)

            # Initialize gradient weights and gradient bias
            g_w = np.zeros(self.f_dim)
            g_b = 0

            # Randomly select a sample
            i = random.randint(0, len(feature_vector) - 1)
            x = np.asarray(feature_vector[i])
            y = train_data[1][i]
            if train_data[1][i] == -1:
                y = 0

            # Find sigmoid function
            z = np.dot(self.w, x) + self.b
            sigmoid = 1 / (1 + math.e ** -z)

            # Update gradient weights and bias
            # g_w = g_w - np.multiply((y - sigmoid), x)
            # g_b = g_b - (y - sigmoid)
            g_w = np.subtract(g_w, np.multiply((y - sigmoid), x))
            g_b = np.subtract(g_b, (y - sigmoid))

            # Update weights and bias
            g_w = np.add(g_w, self.la * self.w)
            self.w = self.w - self.lr_sgd * g_w
            self.b = self.b - self.lr_sgd * g_b
        
    def predict(self, test_x):
        # Initialize prediction list
        pred_y = []

        # Get feature vector from testing data
        feature = np.array(get_feature_vectors(test_x, self.bin_feats))
    
        # Go through each sample
        for i, x in enumerate(feature):
            # Find sigmoid function
            z = np.dot(self.w, x) + self.b
            sigmoid = 1 / (1 + math.e ** -z)

            # Calculte predicted y value
            if sigmoid > 0.5:
                y_prime = 1
            else:
                y_prime = -1
        
            # Append prediction to list
            pred_y.append(y_prime)

        # Return predicted list
        return pred_y
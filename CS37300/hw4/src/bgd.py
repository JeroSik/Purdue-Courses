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

class BGDHinge(BinaryClassifier):
    def __init__(self, args):
        self.bin_feats = args.bin_feats
        self.f_dim = args.f_dim
        self.la = args.la
        self.lr_bgd = args.lr_bgd
        self.num_iter = args.num_iter
        self.vocab_size = args.vocab_size
        
    def fit(self, train_data):
        # Get feature vector from testing data
        feature_vector = get_feature_vectors(train_data[0], self.bin_feats)

        # Initialize weights and bias
        self.w = np.zeros(self.f_dim)
        self.b = 0

        # Iterate through set number of times
        for iter_count in range(self.num_iter):
            # Initialize gradient weights and gradient bias
            g_w = np.zeros(self.f_dim)
            g_b = 0

            # Iterate through all data samples
            for i, x in enumerate(feature_vector):
                y = train_data[1][i]

                # Update gradient weights and bias
                if y * (np.dot(self.w, x)) <= 1:
                    g_w = g_w - [y * x_i for x_i in x]
                    g_b = g_b - y

            # Break if local minimum is found
            if (np.sum(g_w) == 0):
                break

            # Update weights and bias
            self.w = self.w - self.lr_bgd * g_w
            self.b = self.b - self.lr_bgd * g_b
        
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

class BGDLog(BinaryClassifier):
    def __init__(self, args):
        self.bin_feats = args.bin_feats
        self.f_dim = args.f_dim
        self.la = args.la
        self.lr_bgd = args.lr_bgd
        self.num_iter = args.num_iter
        self.vocab_size = args.vocab_size
        
    def fit(self, train_data):
        # Get feature vector from testing data
        feature_vector = get_feature_vectors(train_data[0], self.bin_feats)

        # Initialize weights and bias
        self.w = np.zeros(self.f_dim)
        self.b = 0

        # Iterate through set number of times
        for iter_count in range(self.num_iter):
            # Initialize gradient weights and gradient bias
            g_w = np.zeros(self.f_dim)
            g_b = 0

            # Iterate through all data samples
            for i, x in enumerate(feature_vector):
                y = train_data[1][i]
                if train_data[1][i] == -1:
                    y = 0

                # Find sigmoid function
                z = np.dot(self.w, x) + self.b
                sigmoid = 1 / (1 + math.e ** -z)

                # Update gradient weights and bias
                g_w = g_w - np.multiply((y - sigmoid), x)
                g_b = g_b - (y - sigmoid)

            # Break if local minimum is found
            if (np.sum(g_w) == 0):
                break

            # Update weights and bias
            self.w = self.w - self.lr_bgd * g_w
            self.b = self.b - self.lr_bgd * g_b
            
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

class BGDHingeReg(BinaryClassifier):
    def __init__(self, args):
        self.bin_feats = args.bin_feats
        self.f_dim = args.f_dim
        self.la = args.la
        self.lr_bgd = args.lr_bgd
        self.num_iter = args.num_iter
        self.vocab_size = args.vocab_size
        
    def fit(self, train_data):
        # Get feature vector from testing data
        feature_vector = get_feature_vectors(train_data[0], self.bin_feats)

        # Initialize weights and bias
        self.w = np.zeros(self.f_dim)
        self.b = 0

        # Iterate through set number of times
        for iter_count in range(self.num_iter):
            # Initialize gradient weights and gradient bias
            g_w = np.zeros(self.f_dim)
            g_b = 0

            # Iterate through all data samples
            for i, x in enumerate(feature_vector):
                y = train_data[1][i]

                # Update gradient weights and bias
                if y * (np.dot(self.w, x)) <= 1:
                    g_w = g_w - [y * x_i for x_i in x]
                    g_b = g_b - y
            
            # Break if local minimum is found
            if (np.sum(g_w) == 0):
                break

            # Add regularization term and update weights and bias
            g_w = g_w + self.la * self.w
            self.w = self.w - self.lr_bgd * g_w
            self.b = self.b - self.lr_bgd * g_b
        
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

class BGDLogReg(BinaryClassifier):
    def __init__(self, args):
        self.bin_feats = args.bin_feats
        self.f_dim = args.f_dim
        self.la = args.la
        self.lr_bgd = args.lr_bgd
        self.num_iter = args.num_iter
        self.vocab_size = args.vocab_size
        
    def fit(self, train_data):
        # Get feature vector from testing data
        feature_vector = get_feature_vectors(train_data[0], self.bin_feats)

        # Initialize weights and bias
        self.w = np.zeros(self.f_dim)
        self.b = 0

        # Iterate through set number of times
        for iter_count in range(self.num_iter):
            # Initialize gradient weights and gradient bias
            g_w = np.zeros(self.f_dim)
            g_b = 0

            # Iterate through all data samples
            for i, x in enumerate(feature_vector):
                y = train_data[1][i]
                if train_data[1][i] == -1:
                    y = 0

                # Find sigmoid function
                z = np.dot(self.w, x) + self.b
                sigmoid = 1 / (1 + math.e ** -z)

                # Update gradient weights and bias
                g_w = g_w - np.multiply((y - sigmoid), x)
                g_b = g_b - (y - sigmoid)

            # Break if local minimum is found
            if (np.sum(g_w) == 0):
                break

            # Update weights and bias
            g_w = g_w + self.la * self.w
            self.w = self.w - self.lr_bgd * g_w
            self.b = self.b - self.lr_bgd * g_b
        
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
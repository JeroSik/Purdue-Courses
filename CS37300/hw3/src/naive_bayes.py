#!/usr/bin/env python
# coding: utf-8
"""© 2019 Rajkumar Pujari All Rights Reserved

- Original Version

    Author: Rajkumar Pujari
    Last Modified: 03/12/2019

"""
from __future__ import division
from classifier import BinaryClassifier
from utils import get_feature_vectors
import numpy as np
import math

class NaiveBayes(BinaryClassifier):
    
    def __init__(self, args):
        self.bin_feats = args.bin_feats
        self.f_dim = args.f_dim
        self.lr = args.lr
        self.num_iter = args.num_iter
        self.vocab_size = args.vocab_size
        
    def fit(self, train_data):
        feature = get_feature_vectors(train_data[0], self.bin_feats)
        y = train_data[1]
        
        # Probability of two classes
        self.c_pos_size = y.count(1)
        self.c_neg_size = y.count(-1)
        print(self.c_pos_size)
        print(self.c_neg_size)
        self.prob_c_pos = self.c_pos_size / (self.c_pos_size + self.c_neg_size)
        self.prob_c_neg = self.c_neg_size / (self.c_pos_size + self.c_neg_size)
        print(self.prob_c_neg)

        # Seperate data into two classes
        self.list_c_pos = np.zeros(self.f_dim)
        self.list_c_neg = np.zeros(self.f_dim)
        for i, c in enumerate(y):
            if c == 1:
                self.list_c_pos += feature[i]
            else:
                self.list_c_neg += feature[i]
        
    def predict(self, test_x):
        feature = get_feature_vectors(test_x, self.bin_feats)
        pred_y = []

        # Sum of total words in the two classes
        sum_c_pos = 0
        sum_c_neg = 0
        for i in self.list_c_pos:
            sum_c_pos += i
        for i in self.list_c_neg:
            sum_c_neg += i
        
        # Go through each sample in testing data
        for d in feature:
            log_c_pos = math.log(self.prob_c_pos)
            log_c_neg = math.log(self.prob_c_neg)

            # Go through each word in review
            for i, w in enumerate(d):
                # Check for smoothing
                if w == 0:
                    continue
                
                tmp_w_pos = self.list_c_pos[i] + 1
                tmp_w_neg = self.list_c_neg[i] + 1

                d_pos = sum_c_pos + self.c_pos_size
                d_neg = sum_c_neg + self.c_neg_size

                log_c_pos += math.log(w * tmp_w_pos / d_pos)
                log_c_neg += math.log(w * tmp_w_neg / d_neg)
            
            # Assign the class with higher probability score
            if log_c_pos >= log_c_neg:
                pred_y.append(1)
            else:
                pred_y.append(-1)
        
        return pred_y
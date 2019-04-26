#!/usr/bin/env python
# coding: utf-8
"""

- Original Version

    Author: Susheel Suresh
    Last Modified: 04/03/2019

"""

from classifier import BinaryClassifier
from bgd import BGDHinge,BGDLog,BGDHingeReg,BGDLogReg
from utils import read_data, build_vocab
import utils
from config import args

import matplotlib.pyplot as plt

if __name__ == '__main__':
    filepath = '../data/given/'
    
    build_vocab(filepath, vocab_size=args.vocab_size)
    train_data, test_data = read_data(filepath)

    bgd_l_classifier = BGDLog(args)
    bgd_l_classifier.fit(train_data)
    testList = bgd_l_classifier.evaluateAll(test_data)
    trainList = bgd_l_classifier.evaluateAll(train_data)

    epochList = list(range(1, len(trainList) + 1))
    plt.plot(epochList, trainList, label = "Training")
    epochList = list(range(1, len(testList) + 1))
    plt.plot(epochList, testList, label = "Test")
    plt.title("3.3.1) BGD Log Loss: Accuracy vs Epoch")
    plt.ylabel("Accuracy")
    plt.xlabel("Epoch")
    plt.savefig("3_3_1_bgdlog_accuracy_vs_epoch.png")
    plt.clf()

    bgd_l_r_classifier = BGDLogReg(args)
    bgd_l_r_classifier.fit(train_data)
    testList = bgd_l_r_classifier.evaluateAll(test_data)
    trainList = bgd_l_r_classifier.evaluateAll(train_data)

    epochList = list(range(1, len(trainList) + 1))
    plt.plot(epochList, trainList, label = "Training")
    epochList = list(range(1, len(testList) + 1))
    plt.plot(epochList, testList, label = "Test")
    plt.title("4.3.1) BGD Log Loss with Regularization: Accuracy vs Epoch")
    plt.ylabel("Accuracy")
    plt.xlabel("Epoch")
    plt.savefig("4_3_1_bgdlogreg_accuracy_vs_epoch.png")
    plt.clf()

    bgd_h_classifier = BGDHinge(args)
    bgd_h_classifier.fit(train_data)
    testList = bgd_h_classifier.evaluateAll(test_data)
    trainList = bgd_h_classifier.evaluateAll(train_data)

    epochList = list(range(1, len(trainList) + 1))
    plt.plot(epochList, trainList, label = "Training")
    epochList = list(range(1, len(testList) + 1))
    plt.plot(epochList, testList, label = "Test")
    plt.title("3.3.1) BGD Hinge Loss: Accuracy vs Epoch")
    plt.ylabel("Accuracy")
    plt.xlabel("Epoch")
    plt.savefig("3_3_1_bgdhinge_accuracy_vs_epoch.png")
    plt.clf()

    bgd_h_r_classifier = BGDHingeReg(args)
    bgd_h_r_classifier.fit(train_data)
    testList = bgd_h_r_classifier.evaluateAll(test_data)
    trainList = bgd_h_r_classifier.evaluateAll(train_data)

    epochList = list(range(1, len(trainList) + 1))
    plt.plot(epochList, trainList, label = "Training")
    epochList = list(range(1, len(testList) + 1))
    plt.plot(epochList, testList, label = "Test")
    plt.title("4.3.1) BGD Hinge Loss with Regularization: Accuracy vs Epoch")
    plt.ylabel("Accuracy")
    plt.xlabel("Epoch")
    plt.savefig("4_3_1_bgdhingereg_accuracy_vs_epoch.png")
    plt.clf()
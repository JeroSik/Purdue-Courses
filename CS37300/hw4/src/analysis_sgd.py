#!/usr/bin/env python
# coding: utf-8

from classifier import BinaryClassifier
from sgd import SGDHinge,SGDLog,SGDHingeReg,SGDLogReg
from utils import read_data, build_vocab
import utils
from config import args

import matplotlib.pyplot as plt

if __name__ == '__main__':
    filepath = '../data/given/'
    
    build_vocab(filepath, vocab_size=args.vocab_size)
    train_data, test_data = read_data(filepath)

    sgd_l_classifier = SGDLog(args)
    sgd_l_classifier.fit(train_data)
    testList = sgd_l_classifier.evaluateAll(test_data)
    trainList = sgd_l_classifier.evaluateAll(train_data)

    epochList = list(range(1, len(trainList) + 1))
    plt.plot(epochList, trainList, label = "Training")
    epochList = list(range(1, len(testList) + 1))
    plt.plot(epochList, testList, label = "Test")
    plt.title("4.3.1) SGD Log Loss: Accuracy vs Epoch")
    plt.ylabel("Accuracy")
    plt.xlabel("Epoch")
    plt.savefig("4_3_1_sgdlog_accuracy_vs_epoch.png")
    plt.clf()

    sgd_l_r_classifier = SGDLogReg(args)
    sgd_l_r_classifier.fit(train_data)
    testList = sgd_l_r_classifier.evaluateAll(test_data)
    trainList = sgd_l_r_classifier.evaluateAll(train_data)

    epochList = list(range(1, len(trainList) + 1))
    plt.plot(epochList, trainList, label = "Training")
    epochList = list(range(1, len(testList) + 1))
    plt.plot(epochList, testList, label = "Test")
    plt.title("4.3.2) SGD Log Loss with Regularization: Accuracy vs Epoch")
    plt.ylabel("Accuracy")
    plt.xlabel("Epoch")
    plt.savefig("4_3_2_sgdlogreg_accuracy_vs_epoch.png")
    plt.clf()

    sgd_h_classifier = SGDHinge(args)
    sgd_h_classifier.fit(train_data)
    testList = sgd_h_classifier.evaluateAll(test_data)
    trainList = sgd_h_classifier.evaluateAll(train_data)

    epochList = list(range(1, len(trainList) + 1))
    plt.plot(epochList, trainList, label = "Training")
    epochList = list(range(1, len(testList) + 1))
    plt.plot(epochList, testList, label = "Test")
    plt.title("4.3.1) SGD Hinge Loss: Accuracy vs Epoch")
    plt.ylabel("Accuracy")
    plt.xlabel("Epoch")
    plt.savefig("4_3_1_sgdhinge_accuracy_vs_epoch.png")
    plt.clf()

    sgd_h_r_classifier = SGDHingeReg(args)
    sgd_h_r_classifier.fit(train_data)
    testList = sgd_h_r_classifier.evaluateAll(test_data)
    trainList = sgd_h_r_classifier.evaluateAll(train_data)

    epochList = list(range(1, len(trainList) + 1))
    plt.plot(epochList, trainList, label = "Training")
    epochList = list(range(1, len(testList) + 1))
    plt.plot(epochList, testList, label = "Test")
    plt.title("4.3.2) SGD Hinge Loss with Regularization: Accuracy vs Epoch")
    plt.ylabel("Accuracy")
    plt.xlabel("Epoch")
    plt.savefig("4_3_2_sgdhingereg_accuracy_vs_epoch.png")
    plt.clf()
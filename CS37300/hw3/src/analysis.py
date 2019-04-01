"""
Part 4 Analysis

Name: Youngsik Yoon
Email: yoon161@purdue.edu
Due date: April 1, 2019
"""

import matplotlib.pyplot as plt

# Question 2
plt.plot([1, 2, 5, 10, 20, 50], [56.81, 59.47, 74.42, 77.74, 78.07, 80.40])

plt.title("4.2) Test Accuracy vs Number of Iterations for Averaged Perceptron")
plt.ylabel("Test Accuracy")
plt.xlabel("Number of Iterations")

plt.savefig("4_2_accuracy_vs_num_of_iterations_avg_perceptron.png")
plt.clf()

# Question 3
plt.plot([100, 500, 1000, 5000, 10000, 20000], [0, 0, 0, 0, 0, 0], label = "Naive Bayes")
plt.plot([100, 500, 1000, 5000, 10000, 20000], [0, 0, 0, 0, 0, 0], label = "Averaged Perceptron")

plt.title("4.3) Test Accuracy vs Vocabulary Size")
plt.ylabel("Test Accuracy")
plt.xlabel("Vocabulary Size")
plt.legend()

plt.savefig("4_3_accuracy_vs_vocab_size.png")
plt.clf()
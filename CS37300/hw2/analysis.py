"""
Part 2 Analysis

Name: Youngsik Yoon
Email: yoon161@purdue.edu
Due date: March 4, 2019
"""

import matplotlib.pyplot as plt

# Question 1
plt.plot([0.4, 0.6, 0.8, 1.0], [0.8587, 0.9005, 0.9230, 0.9647], label = "Training Set Accuracy")
plt.plot([0.4, 0.6, 0.8, 1.0], [0.8060, 0.7724, 0.7687, 0.8284], label = "Test Set Accuracy")

plt.title("2.1) Accuracy vs Training Set Percentage")
plt.ylabel("Accuracy")
plt.xlabel("Training Set Percentage")
plt.legend()

plt.savefig("2.1_accuracy_vs_training_set_percentage.png")
plt.clf()

plt.plot([0.4, 0.6, 0.8, 1.0], [121, 171, 221, 291])

plt.title("2.1) Number of Nodes vs Training Set Percentage")
plt.ylabel("Number of Nodes")
plt.xlabel("Training Set Percentage")
plt.legend()

plt.savefig("2.1_number_of_nodes_vs_training_set_percentage.png")
plt.clf()

# Question 2
plt.plot([0.4, 0.5, 0.6, 0.7, 0.8], [0.8620, 0.8427, 0.8925, 0.8507, 0.8507], label = "Training Set Accuracy")
plt.plot([0.4, 0.5, 0.6, 0.7, 0.8], [0.8209, 0.8097, 0.7612, 0.8321, 0.8134], label = "Test Set Accuracy")

plt.title("2.2) Accuracy vs Training Set Percentage")
plt.ylabel("Accuracy")
plt.xlabel("Training Set Percentage")
plt.legend()

plt.savefig("2.2_accuracy_vs_training_set_percentage.png")
plt.clf()

plt.plot([0.4, 0.5, 0.6, 0.7, 0.8], [101, 49, 129, 49, 47], label = "Training Set Accuracy")

plt.title("2.2) Number of Nodes vs Training Set Percentage")
plt.ylabel("Number of Nodes")
plt.xlabel("Training Set Percentage")
plt.legend()

plt.savefig("2.2_number_of_nodes_vs_training_set_percentage.png")
plt.clf()

plt.plot([0.4, 0.5, 0.6, 0.7, 0.8], [10, 5, 10, 5, 5], label = "Maximum depth 5")

plt.title("2.2) Optimal Depth vs Training Set Percentage")
plt.ylabel("Optimal Depth")
plt.xlabel("Training Set Percentage")
plt.legend()

plt.savefig("2.2_depth_vs_training_set_percentage.png")
plt.clf()

# Question 3
plt.plot([0.4, 0.5, 0.6, 0.7, 0.8], [0.7817, 0.7817, 0.8780, 0.8780, 0.7817], label = "Training Set Accuracy")
plt.plot([0.4, 0.5, 0.6, 0.7, 0.8], [0.7985, 0.7985, 0.7761, 0.7873, 0.7985], label = "Test Set Accuracy")

plt.title("2.3) Accuracy vs Training Set Percentage")
plt.ylabel("Accuracy")
plt.xlabel("Training Set Percentage")
plt.legend()

plt.savefig("2.3_accuracy_vs_training_set_percentage.png")
plt.clf()

plt.plot([0.4, 0.5, 0.6, 0.7, 0.8], [3, 3, 133, 153, 3], label = "Training Set Accuracy")

plt.title("2.3) Number of Nodes vs Training Set Percentage")
plt.ylabel("Number of Nodes")
plt.xlabel("Training Set Percentage")
plt.legend()

plt.savefig("2.3_number_of_nodes_vs_training_set_percentage.png")
plt.clf()
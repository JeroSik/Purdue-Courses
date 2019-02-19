##############
# Name:
# email:
# Date:

import numpy as np
import sys
import os

def entropy(freqs):
    """ 
    entropy(p) = -SUM (Pi * log(Pi))
    >>> entropy([10.,10.])
    1.0
    >>> entropy([10.,0.])
    0
    >>> entropy([9.,3.])
    0.811278
    """
    all_freq = sum(freqs)
    entropy = 0 
    for fq in freqs:
        prob = ____ * 1.0 / ____
        if abs(prob) > 1e-8:
            entropy += -____ * np.log2(____)
    return entropy
    
def infor_gain(before_split_freqs, after_split_freqs):
    """
    gain(D, A) = entropy(D) - SUM ( |Di| / |D| * entropy(Di) )
    >>> infor_gain([9,5], [[2,2],[4,2],[3,1]])
    0.02922
    """
    gain = entropy(____)
    overall_size = sum(____)
    for freq in after_split_freqs:
        ratio = sum(____) * 1.0 / ____
        gain -= ratio * entropy(___)
    return gain
    
    
class Node(object):
    def __init__(self, ____):
		pass


class Tree(object):
	def __init__(self, ____):
		pass
	
def ID3(____):
	pass


if __name__ == "__main__":
	# parse arguments
	for x in sys.argv:
		print('arg: ', x)

	# build decision tree
	
	# predict on testing set & evaluate the testing accuracy
	

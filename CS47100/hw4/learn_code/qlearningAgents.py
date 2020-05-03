# qlearningAgents.py
# ------------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
# 
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from game import *
from learningAgents import ReinforcementAgent
from featureExtractors import *

import random,util,math

class QLearningAgent(ReinforcementAgent):
    """
      Q-Learning Agent

      Functions you should fill in:
        - computeValueFromQValues
        - computeActionFromQValues
        - getQValue
        - getAction
        - update

      Instance variables you have access to
        - self.epsilon (exploration prob)
        - self.alpha (learning rate)
        - self.discount (discount rate)

      Functions you should use
        - self.getLegalActions(state)
          which returns legal actions for a state
    """
    def __init__(self, **args):
        "You can initialize Q-values here..."
        ReinforcementAgent.__init__(self, **args)

        "*** YOUR CODE HERE ***"
        # Initialize Q-values to zero [Q_0(s,a) = 0]
        self.qvalues = util.Counter()

    def getQValue(self, state, action):
        """
          Returns Q(state,action)
          Should return 0.0 if we have never seen a state
          or the Q node value otherwise
        """
        "*** YOUR CODE HERE ***"
        # Return Q-values of action in state
        return self.qvalues[(state, action)]

    def computeValueFromQValues(self, state):
        """
          Returns max_action Q(state,action)
          where the max is over legal actions.  Note that if
          there are no legal actions, which is the case at the
          terminal state, you should return a value of 0.0.
        """
        "*** YOUR CODE HERE ***"
        # Check if there are no legal actions
        if len(self.getLegalActions(state)) == 0:
            return 0

        # Initialize max value of actions
        maxValue = float("-inf")

        # Iterate through legal actions
        for action in self.getLegalActions(state):
            # Get Q-value of action in state
            value = self.qvalues[(state,action)]
            # Store max value so far
            maxValue = max(maxValue, value)

        # Return max value of actions
        return maxValue

    def computeActionFromQValues(self, state):
        """
          Compute the best action to take in a state.  Note that if there
          are no legal actions, which is the case at the terminal state,
          you should return None.
        """
        "*** YOUR CODE HERE ***"
        # Initialize next action and max value of actions
        nextAction = None
        maxValue = float("-inf")

        # Iterate through legal actions
        for action in self.getLegalActions(state):
            # Get Q-value of action in state
            value = self.qvalues[(state,action)]

            # Compare current action value and current best action value, adjust best action accordingly
            if value > maxValue:
                maxValue = value
                nextAction = action

        # Return best action 
        return nextAction

    def getAction(self, state):
        """
          Compute the action to take in the current state.  With
          probability self.epsilon, we should take a random action and
          take the best policy action otherwise.  Note that if there are
          no legal actions, which is the case at the terminal state, you
          should choose None as the action.

          HINT: You might want to use util.flipCoin(prob)
          HINT: To pick randomly from a list, use random.choice(list)
        """
        # Pick Action
        legalActions = self.getLegalActions(state)
        action = None
        "*** YOUR CODE HERE ***"
        # Simulate binary variable with probability epsilon
        if util.flipCoin(self.epsilon):
            # Choose random action
            action = random.choice(legalActions)
        else:
            # Initialize max value of actions
            maxValue = float("-inf")

            # Iterate through legal actions
            for nextAction in self.getLegalActions(state):
                # Get Q-value of action in state
                value = self.qvalues[(state,nextAction)]

                # Compare current action value and current best action value, adjust best action accordingly
                if value > maxValue:
                    maxValue = value
                    action = nextAction

        # Return action
        return action

    def update(self, state, action, nextState, reward):
        """
          The parent class calls this to observe a
          state = action => nextState and reward transition.
          You should do your Q-Value update here

          NOTE: You should never call this function,
          it will be called on your behalf
        """
        "*** YOUR CODE HERE ***"
        # Initialize max value of actions for next state
        maxValue = float("-inf")

        # Check if there are no legal actions for next state
        if len(self.getLegalActions(nextState)) == 0:
            maxValue = 0

        # Iterate through legal actions for next state
        for nextAction in self.getLegalActions(nextState):
            # Get Q-value of action in next state
            value = self.qvalues[(nextState,nextAction)]
            # Store max value so far for next state
            maxValue = max(maxValue, value)

        # Calculate sample = (R(s,pi(s),s') + (gamma)V(s'))
        sample = reward + (self.discount * maxValue)
        # Calculate V(s) = (1 - a)V(s) + (a)sample
        updateValue = (1 - self.alpha) * self.qvalues[(state, action)] + self.alpha * sample
        # Update Q-value to (1 - a)V(s) + a(R(s,pi(s),s') + (gamma)V(s'))
        self.qvalues[(state, action)] = updateValue

    def getPolicy(self, state):
        return self.computeActionFromQValues(state)

    def getValue(self, state):
        return self.computeValueFromQValues(state)


class PacmanQAgent(QLearningAgent):
    "Exactly the same as QLearningAgent, but with different default parameters"

    def __init__(self, epsilon=0.05,gamma=0.8,alpha=0.2, numTraining=0, **args):
        """
        These default parameters can be changed from the pacman.py command line.
        For example, to change the exploration rate, try:
            python pacman.py -p PacmanQLearningAgent -a epsilon=0.1

        alpha    - learning rate
        epsilon  - exploration rate
        gamma    - discount factor
        numTraining - number of training episodes, i.e. no learning after these many episodes
        """
        args['epsilon'] = epsilon
        args['gamma'] = gamma
        args['alpha'] = alpha
        args['numTraining'] = numTraining
        self.index = 0  # This is always Pacman
        QLearningAgent.__init__(self, **args)

    def getAction(self, state):
        """
        Simply calls the getAction method of QLearningAgent and then
        informs parent of action for Pacman.  Do not change or remove this
        method.
        """
        action = QLearningAgent.getAction(self,state)
        self.doAction(state,action)
        return action


class ApproximateQAgent(PacmanQAgent):
    """
       ApproximateQLearningAgent

       You should only have to overwrite getQValue
       and update.  All other QLearningAgent functions
       should work as is.
    """
    def __init__(self, extractor='IdentityExtractor', **args):
        self.featExtractor = util.lookup(extractor, globals())()
        PacmanQAgent.__init__(self, **args)
        self.weights = util.Counter()

    def getWeights(self):
        return self.weights

    def getQValue(self, state, action):
        """
          Should return Q(state,action) = w * featureVector
          where * is the dotProduct operator
        """
        "*** YOUR CODE HERE ***"
        util.raiseNotDefined()

    def update(self, state, action, nextState, reward):
        """
           Should update your weights based on transition
        """
        "*** YOUR CODE HERE ***"
        util.raiseNotDefined()

    def final(self, state):
        "Called at the end of each game."
        # call the super-class final method
        PacmanQAgent.final(self, state)

        # did we finish training?
        if self.episodesSoFar == self.numTraining:
            # you might want to print your weights here for debugging
            "*** YOUR CODE HERE ***"
            pass

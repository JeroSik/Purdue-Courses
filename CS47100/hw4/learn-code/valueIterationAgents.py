# valueIterationAgents.py
# -----------------------
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


import mdp, util

from learningAgents import ValueEstimationAgent

class ValueIterationAgent(ValueEstimationAgent):
    """
        * Please read learningAgents.py before reading this.*

        A ValueIterationAgent takes a Markov decision process
        (see mdp.py) on initialization and runs value iteration
        for a given number of iterations using the supplied
        discount factor.
    """
    def __init__(self, mdp, discount = 0.9, iterations = 100):
        """
          Your value iteration agent should take an mdp on
          construction, run the indicated number of iterations
          and then act according to the resulting policy.

          Some useful mdp methods you will use:
              mdp.getStates()
              mdp.getPossibleActions(state)
              mdp.getTransitionStatesAndProbs(state, action)
              mdp.getReward(state, action, nextState)
              mdp.isTerminal(state)
        """
        self.mdp = mdp
        self.discount = discount
        self.iterations = iterations
        self.values = util.Counter() # A Counter is a dict with default 0

        # Write value iteration code here
        "*** YOUR CODE HERE ***"
        # Iterate based on the number of times passed into the parameter
        for i in range(self.iterations):
            # Store old optimal values[V_k(*)]
            oldValues = self.values.copy()

            # s: Iterate through all possible states
            for state in self.mdp.getStates():
                # Store value of max action for current state
                maxAction = float("-inf")

                # Check if state is non-terminal state
                if not self.mdp.isTerminal(state):
                    # Iterate through all possible actions
                    for action in self.mdp.getPossibleActions(state):
                        # Store value for current action for current state
                        actionValue = 0

                        # Iterate through all possible transitions
                        for transition in self.mdp.getTransitionStatesAndProbs(state, action):
                            # Get transition state [s'], probability [P(s'|s,a)], reward value [R(s,a,s')], and value of transition state [V_k(s')]
                            transitionState = transition[0]
                            transitionProb = transition[1]
                            reward = self.mdp.getReward(state, action, transitionState)
                            transitionStateValue = oldValues[transitionState]

                            # Calculate sum of P(s'|s,a)[R(s,a,s') + (gamma)V_k(s')] for all transition states
                            actionValue += transitionProb * (reward + self.discount * transitionStateValue)
                        
                        # Store max value so far
                        maxAction = max(maxAction, actionValue)
                    
                    # Store optimal value for state
                    self.values[state] = maxAction


    def getValue(self, state):
        """
          Return the value of the state (computed in __init__).
        """
        return self.values[state]


    def computeQValueFromValues(self, state, action):
        """
          Compute the Q-value of action in state from the
          value function stored in self.values.
        """
        "*** YOUR CODE HERE ***"
        # Initialize Q-value as zero
        qvalue = 0
        
        # Iterate through all possible transitions for action in state
        for transition in self.mdp.getTransitionStatesAndProbs(state, action):
            # Get transition state [s'], probability [P(s'|s,a)], reward value [R(s,a,s')], and value of transition state [V_k(s')]
            transitionState = transition[0]
            transitionProb = transition[1]
            reward = self.mdp.getReward(state, action, transitionState)
            transitionStateValue = self.values[transitionState]

            # Calculate sum of P(s'|s,a)[R(s,a,s') + (gamma)V_k(s')] for all transition states
            qvalue += transitionProb * (reward + self.discount * transitionStateValue)
        
        # Return computed Q-value of action in state
        return qvalue

    def computeActionFromValues(self, state):
        """
          The policy is the best action in the given state
          according to the values currently stored in self.values.

          You may break ties any way you see fit.  Note that if
          there are no legal actions, which is the case at the
          terminal state, you should return None.
        """
        "*** YOUR CODE HERE ***"
        # Initialize new action and new action value
        newAction = None
        newActionValue = float("-inf")

        # Check if state is non-terminal state
        if not self.mdp.isTerminal(state):
            # Iterate through all possible actions in the given state
            for action in self.mdp.getPossibleActions(state):
                # Compute the Q-value of action in state
                actionQvalue = self.getQValue(state, action)

                # Compare current action value and current best action value, adjust best action accordingly
                if actionQvalue > newActionValue:
                    newActionValue = actionQvalue
                    newAction = action
        
        # Return the best action
        return newAction

    def getPolicy(self, state):
        return self.computeActionFromValues(state)

    def getAction(self, state):
        "Returns the policy at the state (no exploration)."
        return self.computeActionFromValues(state)

    def getQValue(self, state, action):
        return self.computeQValueFromValues(state, action)

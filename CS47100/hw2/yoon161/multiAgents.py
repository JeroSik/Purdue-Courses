# multiAgents.py
# --------------
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


from util import manhattanDistance
from game import Directions
import random, util

from game import Agent

class ReflexAgent(Agent):
    """
      A reflex agent chooses an action at each choice point by examining
      its alterevaluationFunctionnatives via a state evaluation function.

      The code below is provided as a guide.  You are welcome to change
      it in any way you see fit, so long as you don't touch our method
      headers.
    """


    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {North, South, West, East, Stop}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()

        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices) # Pick randomly among the best

        "Add more of your code here if you want to"

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newGhostPos = successorGameState.getGhostPositions()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

        "*** YOUR CODE HERE ***"
        score = successorGameState.getScore()

        # Get useful information for current game state
        currPos = currentGameState.getPacmanPosition()
        currFood = currentGameState.getFood()
        currGhostStates = currentGameState.getGhostStates()
        currGhostPos = currentGameState.getGhostPositions()
        currScaredTimes = [ghostState.scaredTimer for ghostState in currGhostStates]

        """ Distance to food feature """
        # Initialize useful information
        currFoodList = currFood.asList()
        newFoodList = newFood.asList()
        # Find closest food from successor and current state
        currDist = float("inf")
        newDist = float("inf")
        for food in currFoodList:
            currDist = min(manhattanDistance(currPos, food), currDist)
        for food in newFoodList:
            newDist = min(manhattanDistance(newPos, food), newDist)
        # Only score if moving closer to food
        if newDist <= currDist:
            score += 1.0/(1+newDist)

        """ Distance from Ghost feature """
        # Find closest ghost
        newDist = float("inf")
        for ghostPos in newGhostPos:
            newDist = min(manhattanDistance(newPos, ghostPos), newDist)
        # Distance is 0 means game lost.
        if newDist == 0:
            return float("-inf")
        # Decrement score to avoid closest ghost
        score -= 1.0/newDist

        return score

def scoreEvaluationFunction(currentGameState):
    """
      This default evaluation function just returns the score of the state.
      The score is the same one displayed in the Pacman GUI.

      This evaluation function is meant for use with adversarial search agents
      (not reflex agents).
    """
    return currentGameState.getScore()

class MultiAgentSearchAgent(Agent):
    """
      This class provides some common elements to all of your
      multi-agent searchers.  Any methods defined here will be available
      to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

      You *do not* need to make any changes here, but you can if you want to
      add functionality to all your adversarial search agents.  Please do not
      remove anything, however.

      Note: this is an abstract class: one that should not be instantiated.  It's
      only partially specified, and designed to be extended.  Agent (game.py)
      is another abstract class.
    """

    def __init__(self, evalFn = 'scoreEvaluationFunction', depth = '2'):
        self.index = 0 # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)

class MinimaxAgent(MultiAgentSearchAgent):
    """
      Your minimax agent (question 2)
    """

    def getAction(self, gameState):
        """
          Returns the minimax action from the current gameState using self.depth
          and self.evaluationFunction.

          Here are some method calls that might be useful when implementing minimax.

          gameState.getLegalActions(agentIndex):
            Returns a list of legal actions for an agent
            agentIndex=0 means Pacman, ghosts are >= 1

          gameState.generateSuccessor(agentIndex, action):
            Returns the successor game state after an agent takes an action

          gameState.getNumAgents():
            Returns the total number of agents in the game
        """
        "*** YOUR CODE HERE ***"
        nextAction = None
        maxScore = float("-inf")

        """ Minimax action for Pacman """
        # Iterate through Pacman's legal actions
        for action in gameState.getLegalActions(0):
            # Get Minimax value from action
            newState = gameState.generateSuccessor(0, action)
            score = self.value(newState, 1, 0)

            # Get best action
            if score > maxScore:
                maxScore = score
                nextAction = action
        
        return nextAction
    
    """ Dispatch Fuction for Minimax Search """
    def value(self, gameState, agent, currDepth):
        # Consider multiple MIN layers for every MAX layer
        if agent == gameState.getNumAgents():
            currDepth += 1
            agent = 0
        
        # Terminal Conditions
        if currDepth == self.depth or gameState.isWin() or gameState.isLose():
            return self.evaluationFunction(gameState)
        
        # MAX layer if agent is Pacman
        if agent == 0:
            return self.maxValue(gameState, agent, currDepth)
        # MIN layer if agent is Ghost
        else:
            return self.minValue(gameState, agent, currDepth)
    
    """ Calculate value for MAX agent """
    def maxValue(self, gameState, agent, currDepth):
        v = float("-inf")
        # Get max value from other layers
        for newState in gameState.getLegalActions(agent):
            v = max(v, self.value(gameState.generateSuccessor(agent, newState), agent + 1, currDepth))

        return v

    """ Calculate value for MIN agent """
    def minValue(self, gameState, agent, currDepth):
        v = float("inf")
        # Get min value from other layers
        for newState in gameState.getLegalActions(agent):
            v = min(v, self.value(gameState.generateSuccessor(agent, newState), agent + 1, currDepth))

        return v

class AlphaBetaAgent(MultiAgentSearchAgent):
    """
      Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        """
          Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"
        nextAction = None
        maxScore = float("-inf")
        alpha = float("-inf")
        beta = float("inf")

        """ Minimax action with alpha-beta pruning for Pacman """
        # Iterate through Pacman's legal actions
        for action in gameState.getLegalActions(0):
            # Get Minimax with alpha-beta pruning value from action
            newState = gameState.generateSuccessor(0, action)
            score = self.value(newState, 1, 0, alpha, beta)

            # Get best action
            if score > maxScore:
                maxScore = score
                nextAction = action

            # MAX configuration 
            if score > beta:
                return score
            alpha = max(alpha, score)
        
        return nextAction
    
    """ Dispatch Fuction for Minimax Search with alpha-beta pruning """
    def value(self, gameState, agent, currDepth, alpha, beta):
        # Consider multiple MIN layers for every MAX layer
        if agent == gameState.getNumAgents():
            currDepth += 1
            agent = 0
        
        # Terminal Conditions
        if currDepth == self.depth or gameState.isWin() or gameState.isLose():
            return self.evaluationFunction(gameState)
        
        # MAX layer if agent is Pacman
        if agent == 0:
            return self.maxValue(gameState, agent, currDepth, alpha, beta)
        # MIN layer if agent is Ghost
        else:
            return self.minValue(gameState, agent, currDepth, alpha, beta)
    
    """ Calculate value for MAX agent """
    def maxValue(self, gameState, agent, currDepth, alpha, beta):
        v = float("-inf")
        # Get max value from other layers
        for newState in gameState.getLegalActions(agent):
            v = max(v, self.value(gameState.generateSuccessor(agent, newState), agent + 1, currDepth, alpha, beta))

            # MAX configuration
            if v > beta:
                return v
            alpha = max(alpha, v)

        return v

    """ Calculate value for MIN agent """
    def minValue(self, gameState, agent, currDepth, alpha, beta):
        v = float("inf")
        # Get min value from other layers
        for newState in gameState.getLegalActions(agent):
            v = min(v, self.value(gameState.generateSuccessor(agent, newState), agent + 1, currDepth, alpha, beta))

            # MIN configuration
            if v < alpha:
                return v
            beta = min(beta, v)

        return v

class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
          Returns the expectimax action using self.depth and self.evaluationFunction

          All ghosts should be modeled as choosing uniformly at random from their
          legal moves.
        """
        "*** YOUR CODE HERE ***"
        util.raiseNotDefined()

def betterEvaluationFunction(currentGameState):
    """
      Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
      evaluation function (question 5).

      DESCRIPTION:
      Evaluation function considers 2 features.
      1. Food feature
        - Finds the closest food.
        - Higher score for being closer food.
      2. Ghost feature
        - Case 1: Ghost-hunting
          > Higher score for being closer to ghost
        - Case 2: Ghost-avoiding
          > Higher score for being further from ghost
    """
    "*** YOUR CODE HERE ***"
    """ Initialize variables and other information """
    # Extract useful information from current game state
    currPos = currentGameState.getPacmanPosition()
    currFoodList = currentGameState.getFood().asList()
    currGhostStates = currentGameState.getGhostStates()
    currGhostPos = currentGameState.getGhostPositions()
    currScaredTimes = [ghostState.scaredTimer for ghostState in currGhostStates]

    # Initialize score to current score
    score = currentGameState.getScore()

    """ Food feature """
    # Find closest food from current state
    closestFood = float("inf")
    for food in currFoodList:
        closestFood = min(manhattanDistance(currPos, food), closestFood)

    """ Ghost feature """
    # Find closest ghost
    closestGhost = float("inf")
    for ghostPos in currGhostPos:
        closestGhost = min(manhattanDistance(currPos, ghostPos), closestGhost)

    # Distance is 0 means game lost.
    if closestGhost == 0:
        return float("-inf")
    
    # Check if ghosts are scared
    if sum(currScaredTimes) > 0:
        closestGhost = -closestGhost

    """ Return combined score from features """
    return score + 1.0/(1.0 + closestFood) - (1.0/closestGhost)

# Abbreviation
better = betterEvaluationFunction


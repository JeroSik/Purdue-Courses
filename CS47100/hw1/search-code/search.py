# search.py
# ---------
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


"""
In search.py, you will implement generic search algorithms which are called by
Pacman agents (in searchAgents.py).
"""

import util


class SearchProblem:
    """
    This class outlines the structure of a search problem, but doesn't implement
    any of the methods (in object-oriented terminology: an abstract class).

    You do not need to change anything in this class, ever.
    """

    def getStartState(self):
        """
        Returns the start state for the search problem.
        """
        util.raiseNotDefined()

    def isGoalState(self, state):
        """
          state: Search state

        Returns True if and only if the state is a valid goal state.
        """
        util.raiseNotDefined()

    def getSuccessors(self, state):
        """
          state: Search state

        For a given state, this should return a list of triples, (successor,
        action, stepCost), where 'successor' is a successor to the current
        state, 'action' is the action required to get there, and 'stepCost' is
        the incremental cost of expanding to that successor.
        """
        util.raiseNotDefined()

    def getCostOfActions(self, actions):
        """
         actions: A list of actions to take

        This method returns the total cost of a particular sequence of actions.
        The sequence must be composed of legal moves.
        """
        util.raiseNotDefined()


def tinyMazeSearch(problem):
    """
    Returns a sequence of moves that solves tinyMaze.  For any other maze, the
    sequence of moves will be incorrect, so only use this for tinyMaze.
    """
    from game import Directions
    s = Directions.SOUTH
    w = Directions.WEST
    return [s, s, w, s, w, w, s, w]


def depthFirstSearch(problem):
    """
    Search the deepest nodes in the search tree first.

    Your search algorithm needs to return a list of actions that reaches the
    goal. Make sure to implement a graph search algorithm.

    To get started, you might want to try some of these simple commands to
    understand the search problem that is being passed in:

    print "Start:", problem.getStartState()
    print "Is the start a goal?", problem.isGoalState(problem.getStartState())
    print "Start's successors:", problem.getSuccessors(problem.getStartState())
    """
    "*** YOUR CODE HERE ***"
    # Initialize direction counter for backtracking and stack for DFS
    dircounter = util.Counter()
    stack = util.Stack()

    # Initialize start of DFS
    startState = problem.getStartState()
    goalState = None
    dircounter[startState] = (None, None)
    visited = set()

    # Begin DFS with startState
    stack.push(startState)
    while not stack.isEmpty():
        curState = stack.pop()

        # If state is goalState then stop DFS
        if problem.isGoalState(curState):
            goalState = curState
            break

        # If state has not already been visited, run DFS on successors
        if not curState in visited:
            visited.add(curState)

            successors = problem.getSuccessors(curState)
            for sucTriple in successors:
                sucState, action, cost = sucTriple
                if not sucState in visited:
                    dircounter[sucState] = (curState, action)
                    stack.push(sucState)
    
    # Backtrack to find path
    if not goalState:
        raise AssertionError("Can not find a valid solution")
    else:
        actions = []
        state = goalState
        while state != startState:
            prevState, action = dircounter[state]
            actions.append(action)
            state = prevState
        return actions[::-1]


def breadthFirstSearch(problem):
    """Search the shallowest nodes in the search tree first."""
    "*** THE EXAMPLE CODE HERE ***"
    dircounter = util.Counter()
    queue = util.Queue()

    startState = problem.getStartState()
    goalState = None
    dircounter[startState] = (None, None)

    queue.push(startState)
    while not queue.isEmpty():
        curState = queue.pop()
        # time.sleep(1)
        if problem.isGoalState(curState):
            goalState = curState
            break

        successors = problem.getSuccessors(curState)
        for sucTriple in successors:
            sucState, action, cost = sucTriple
            if dircounter[sucState] == 0:
                dircounter[sucState] = (curState, action)
                queue.push(sucState)

    if not goalState:
        raise AssertionError("Can not find a valid solution")
    else:
        actions = []
        state = goalState
        while state != startState:
            prevState, action = dircounter[state]
            actions.append(action)
            state = prevState
        return actions[::-1]


def uniformCostSearch(problem):
    """Search the node of least total cost first."""
    "*** YOUR CODE HERE ***"
    # Initialize direction counter for backtracking and priority queue for UCS
    dircounter = util.Counter()
    pQueue = util.PriorityQueue()

    # Initialize start of UCS
    startState = problem.getStartState()
    goalState = None
    dircounter[startState] = (None, None, 0)
    visited = set()

    # Begin UCS with startState at cost 0
    pQueue.push((startState, 0), 0)
    while not pQueue.isEmpty():
        curState, curCost = pQueue.pop()

        # If state is goalState then stop UCS
        if problem.isGoalState(curState):
            goalState = curState
            break

        # If state has not already been visited, add successors to the priority queue with the cumulative cost
        if not curState in visited:
            visited.add(curState)

            successors = problem.getSuccessors(curState)
            for sucTriple in successors:
                sucState, action, cost = sucTriple
                if not sucState in visited:
                    if (dircounter[sucState] == 0 or dircounter[sucState][2] > curCost + cost):
                        dircounter[sucState] = (curState, action, curCost + cost)
                    pQueue.update((sucState, curCost + cost), curCost + cost)
    
    # Backtrack to find path
    if not goalState:
        raise AssertionError("Can not find a valid solution")
    else:
        actions = []
        state = goalState
        while state != startState:
            prevState, action, cost = dircounter[state]
            actions.append(action)
            state = prevState
        return actions[::-1]


        

def nullHeuristic(state, problem=None):
    """
    A heuristic function estimates the cost from the current state to the nearest
    goal in the provided SearchProblem.  This heuristic is trivial.
    """
    return 0


def aStarSearch(problem, heuristic=nullHeuristic):
    """Search the node that has the lowest combined cost and heuristic first."""
    "*** YOUR CODE HERE ***"
    # Initialize direction counter for backtracking and priority queue for A*
    dircounter = util.Counter()
    pQueue = util.PriorityQueue()

    # Initialize start of A*
    startState = problem.getStartState()
    goalState = None
    dircounter[startState] = (None, None, 0)
    visited = set()

    # Begin A* with startState at cost 0
    pQueue.push((startState, 0), 0)
    while not pQueue.isEmpty():
        curState, curCost = pQueue.pop()

        # If state is goalState then stop A*
        if problem.isGoalState(curState):
            goalState = curState
            break

        # If state has not already been visited, add successors to the priority queue with the cumulative cost
        if not curState in visited:
            visited.add(curState)

            successors = problem.getSuccessors(curState)
            for sucTriple in successors:
                sucState, action, cost = sucTriple
                if not sucState in visited:
                    g = curCost + cost
                    h = heuristic(sucState, problem)

                    if (dircounter[sucState] == 0 or g < dircounter[sucState][2]):
                        dircounter[sucState] = (curState, action, g)
                    pQueue.update((sucState, g), g + h)
    
    # Backtrack to find path
    if not goalState:
        raise AssertionError("Can not find a valid solution")
    else:
        actions = []
        state = goalState
        while state != startState:
            prevState, action, cost = dircounter[state]
            actions.append(action)
            state = prevState
        return actions[::-1]


# Abbreviations
bfs = breadthFirstSearch
dfs = depthFirstSearch
astar = aStarSearch
ucs = uniformCostSearch


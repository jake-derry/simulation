# Design Exercise

Matt Harris (msh54)
Jake Derry (jad111)
Jonah Knapp (jk366)


## Rock Paper Scissors Exercise

Our team discussed how the Rock, Paper, Scissors game could be implemented using CRC cards. We determined there could be 5 main classes used to play the game. 


### Game
- initializes # of players
- keeps track of player scores
- determines game winner when a player's score reaches a set threshold
- starts round by passing in a 'game graph' file to Round with rules on which choice beats which

#### Collaborators: 
- Round
- Player

### Round

- knows round rules
- knows game graph
- asks player to choose weapon
- determines winner
- passes game winner of game

#### Collaborators: 
- Game
- Player
- Game Graph


### Player
- chooses weapon 

#### Collaborators: 
- Round

### Game Graph
- initializes matric of weapon relationships
- constructed with list of weapons 
- choice method to update weapon relationships

#### Collaborators: 
- Round 


## Game code review

We discussed Jake's use of inheritance in his project and what the benefits were of using an inheritance heirarchy to organize the behaviors that objects like bricks, paddles, and balls shared. We discussed that the use of heirarchy is (1) to prevent bugs as people add to the code base, (2) to organize the behaviors of objects, and (3) to prevent data clumps.

Heirarchy:
 - Object
     - Paddle
     - Ball
     - Brick
         - Indestructable Brick
         - ...

## Cell Society Design

### Questions

1. How does a Cell know about its neighbors? How can it update itself without effecting its neighbors update?

    The Cell contains a list of its neighbors. The cell contains both a current and future state variable. The future state is updated in a first class while the current state is accessed by its neighbors.

2. What relationship exists between a Cell and a simulation's rules?

    We will implement multiple types of cells, each of which will have behavior based on the type of simulation. These cells will be initialized by the simulation after reading from the configuration. 

3. What is the grid? Does it have any behaviors? Who needs to know about it?

    The grid is a 2D array of Cells. The grid itself does not do anything, and the Simulation is the only class that knows about it.

4. What information about a simulation needs to be the configuration file?

    We will have to know certain parameters of the simulation (such as in the fire example, the chance of trees catching on fire). There will also be the ability to congifure the number of cells in the configuration, etc. 

5. How is the graphical view of the simulation updated after all the cells have been updated?

    The graphical view will be generated after the cells have been updated and the future states set to the current state. 

### CRC Cards

# Simulation (2-d array of cells)

private void step()
    - calls cells to update
    
keeps track of grid

# Configuration(XML File)
public Cell[][] generateCells()
    - Reads parameters and initial states from file
    - creates 2d array of cells to pass to simulation
    
private void readFile(XML File)
    -set configuration paramters from XML file 


# Visualization (type)

public void updateVisualization(Cell[][])

# abstract Cell()

public void update()
knows neighbors
has state and future state
public void setNewState()

simulation
====

This project implements a cellular automata simulator.

Names:
Jake Derry jad111
Matt Harris msh54
Jonah Knapp jk366

### Timeline

Start Date: Sept 15

Finish Date: Sept 23

Hours Spent: 44 hours between members

### Primary Roles

Simulation: Jake Derry

Visualization and Display: Matt Harris

Configuration: Jonah Knapp

### Resources Used
Button images obtained from: http://clipart-library.com/clip-art/play-pause-button-transparent-3.htm
New Sim File Chooser inspiration from: https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm

### Running the Program

Main class: CAApp.java 

Data files needed: (needed for running simulations of each type) Fire.xml, GameOfLife.xml, Percolation.xml, PredatorPrey.xml, Segregation.xml

Interesting data files: GameOfLife.xml

Features implemented: Simulations for 5 different cellular automata configured with an xml file and visualized in a display controlled 
by a play/resume button, step button, increase and decrease simulation speed buttons, and a New Sim button capable of loading
a new config file for a new simulation on the fly.

Assumptions or Simplifications:
* no additional types of CA's can be run besides the five implemented
* only one CA can be simulated and visualized at once
* every simulation being run is visualized
* the color scheme is hard coded

Known Bugs:
* the current implementation of the segregation CA seems to be working incorrectly however this will be fixed for the final version

Extra credit:

### Notes
#### XML File Format:
* The required fields for all XML files are the following: type, author, rows, and columns. If the file does not contain
any one of these fields, then the file reader will not succeed and will throw an error. In order to have a meaningful output, 
most of the simulations will also require for cells to be initialized as an "activeCell". 

* An activeCell is initialized within the activeCell field as a "cell". Required information for cells are row, column, 
and state. Row and column may start at 1 and go up to the rows/columns field specified earlier. 
Acceptable values for the state of a cell depend on the simulation being ran. 

* The type field denotes the type of simulation to be ran. Currently, there are five supported types: 
"gameOfLife", "segregation", "predatorPrey", "fire", and "percolation". There are also different requirements based on 
the type of file selected. 

### Impressions
* the functionality of the menu buttons was much more deeply connected to the simulations themselves than we expected
* we took the planning phase much more seriously than our individual brickbreaker assignments and this made the integration of our separate parts much easier
* when implementing many subclasses of a design you realize how good (or bad) it is

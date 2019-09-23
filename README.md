simulation
====

This project implements a cellular automata simulator.

Names:

### Timeline

Start Date: 

Finish Date: 

Hours Spent:

### Primary Roles


### Resources Used
Button images gotten from: http://clipart-library.com/clip-art/play-pause-button-transparent-3.htm
New File Chooser inspiration from: https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm

### Running the Program

Main class:

Data files needed: 

Interesting data files:

Features implemented:

Assumptions or Simplifications:

Known Bugs:

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

**Parameter Requirements for Simulations**
* gameOfLife

There are no additional parameters needed for the segregation simulation. 
* segregation


* predatorPrey


* fire


* percolation


### Impressions


simulation
====

This project implements a cellular automata simulator.

Names:
Jake Derry jad111
Matt Harris msh54
Jonah Knapp jk366

### Timeline

Start Date: Sept 15

Finish Date: Sept 30

Hours Spent: 44 hours between members

### Primary Roles

Simulation: Jake Derry

Visualization and Display: Matt Harris

Configuration: Jonah Knapp

### Resources Used
New Sim File Chooser inspiration from: https://docs.oracle.com/javafx/2/ui_controls/file-chooser.htm

### Running the Program

####Main class: CAApp.java 

####Data files needed (needed for running simulations of each type):
 * Fire.xml
 * GameOfLife.xml 
 * Percolation.xml 
 * PredatorPrey.xml 
 * Segregation.xml
 * For Error Checking info, see section in Notes below

####Interesting data files: GameOfLife.xml

####Features implemented:
##### Configuration: 
* Reads in an XML file that contains initial settings for a simulation. The type of simulation, 
neighbors, size of grid, shape of the cell, simulation parameters, preferred style file, and 
type of distribution (specific, random, or concentration) are all able to be set through this XML. 
* Reads in an XML file that contains the style settings for the simulation. This includes whether or not 
grid lines should be displayed, colors for cells, delay of the simulation(initial speed), and the 
dimensions of the window. 
* Reads in an XML file that contains the default settings for color. Ideally, this file is not 
touched by the user and serves as a flexible backup for when the user does not specify a color 
within their Style file. 
* Error checking for incorrect file data (See error checking Note below) 
* Ability for user to select the initial distribution of the cells. This distribution can be set 
by specific locations and states, randomly by number of locations to occupy, and finally by 
concentration.
##### Simulation:
##### Visualization: 
* visualization of the cell grid that can run indefinitely until stopped by user (with specific cell shapes specified in style xml)
* menu buttons that allow the user to: pause and play the simulation, step forward through simulation, control simulation speed, start a new simulation from xml file in a new window
* all display text read from a properties file specified by language choice
* visualization of the states of cells over time shown in a LineChart
* ability for user to change cell states by clicking on cell locations
* multiple simulations to be run at once using the LoadNewSimulation button

Assumptions or Simplifications:
* no additional types of CA's can be run besides the five implemented
* only one CA can be simulated and visualized at once
* every simulation being run is visualized
* the color scheme is hard coded

Known Bugs:
* the current implementation of the segregation CA seems to be working incorrectly however this will be fixed for the final version

Extra credit:

### Notes
#### Error Checking 
The following errors are specifically checked for when reading an XML file. If the input is an 
invalid type, then default parameters will be assigned for each of these. 
* Cell Array Parameters: Ensures that the cell array has valid dimensions. If either 
of the dimensions are not specified correctly (not an integer or is negative), the 
default dimension will be set for both # of columns and # of rows. For integer entries, spaces in or 
around the entry will not affect the ability for it to be read. 
* Active Cell Parameters: Ensures that the cell states are valid, based on the 
type of parameter that is input. Also ensures that each of the cell positions are 
valid, given the dimensions of the grid given above. 

The following errors are also checked. However, if there is error in the declaration of 
these parameters, a default simulation file will be ran.

* Type of XML: Ensures that the XML is of type "Simulation", specified by the 
name of the first node within the XML file. 
* Type of Simulation: Ensures that the simulation type is supported.
* Errors due to file type, such as the event that a non-XML file is chosen.

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

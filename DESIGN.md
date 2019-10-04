# DESIGN
Names:
Jake Derry jad111
Matt Harris msh54
Jonah Knapp jk366

## Design Goals

We designed with hopes to easily implement:

- New simulation types (cell rules)
- Various visual representations of the data (grid, graph, etc)
- New and multiple instances of simulations (opened in new windows, multiple at once)
- Adding or defining new neighborhoods for cells
- Allowing custom, user-defined stylings (independent from simulation configuration)

## High-level Design

*Application flow*:
- CAApp starts
- Configurer takes in an initial "default" (defined in CAApp) xml to start the first simulation
- Parsing of this xml creates a simulation object of the correct type with the correct initial states and parameters
- Call to configurer to create the styling map
- Styling map and simulation are used to create visualization
- The keyframe for the timeline is connected to the step method of the created visualization which controls the progress of the simulation
- Users can then control the simulation through buttons in the UI maintained in visualization, and can start new simulations with the LoadNewSimButton


### Configuration Module
#### Configuration
- called from the start method of CAApp and the setOnAction of the LoadNewSim button
- main two methods: getSimulation and getStyling
- getSimulation: reads a file based on a given filename and creates a simulation of the proper type and with the correct initial states. In addition, creates a parameterMap passed to the simulation that contains simulation parameters as well as the styling filename
- getStyling: reads a file based on a given filename and creates a styling map (used to set up a visualization) with information like a colormap for setting state colors, simulation delay, outlines of cells, and the size of the window

### Simulation Module
#### Simulation
- created by Configurer's getSimulation method
- creates and maintains CellGrid object with state information for all cells and definition of neighborhood to be used in the simulation
- simulation only calls its cells to update, it does not implement simulation rules
#### CellGrid
- CellGrid makes and contains individual cells, created at the contruction of a simulation 
- The cell grid offers the Iterable interface which allows cells to update themselves when called by simulation
- main methods: createCellGrid, connectGrid, connectNeighbors
- createCellGrid: called at the construction of a CellGrid object, generates an array of cells with the correct parameters and cell type
- connectGrid: called at the construction of a CellGrid object, calls connectNeighbors for all cells in the grid 
- connectNeighbors: called at the construction of a CellGrid object, connects cells to their neighbors based on the definition of the neighborhood for the simulation, parsed from xml

#### Cell
- abstract class that updates itself and maintains a current state, next state, and its own neighborhood
- implementations of cell define the behavior of updateNext based on the simulation rules with which a cell is associated (fire cell, rps cell, etc)

#### Neighborhood
- class to define the neighborhood of a cell, designed to make it easier to implement new types of simulations and new definitions of neighborhoods
- Implements the Iterable interface
- main methods: chooseNeighbor, getRight/Left/Down/Up
- chooseNeighbor: called from implementations of cell, randomly chooses a neighbor that has one of the acceptable states passed in to the method call
- getRight/Left/Down/Up: called from implementations of cell, returns the cell in the correct direction

#### State
- enumerated types to define the different states of cells used in the implementations of cells and connects the text in the xml pertaining to cell state to a specific, enumerated cell State
- main method: getState()
- getState(): maps the defined State text in the xml to the correct enumerated State type

#### CellShape
- enumerated types that define the mapping of the geometric shape of a cell to the displacement (how to get neighboring cells)
- main methods: getDisplacements, matchShape
- getDisplacements: returns all the displacement pairs for an associated CellShape
- matchShape: maps the defined shape text in the xml to the correct enumerated CellShape type

### Visualization Module
#### Visualization
- maintains and displays all visual aspects of the app as well as controls a simulation's progress through calling the simulation to step and user-controlled menu buttons
- on creation of a visualization object, window and animation parameters are set, a LineChart is created to track cell states, menu buttons and title text are added, the visual cell grid is created
- main methods: step, visualize
- step: calls simulation to step once forward, calls visualize
- visualize: calls the GridHandler to update the visual cell grid and the GraphHandler to update the state graph

#### MenuHandler
- adds the menu buttons that have been implemented and included to the menu section of the display as well as the title text, related to simulation type
- main methods: addTitleTextToDisplayGroup, addMenuButtonsToDisplayGroup
- addMenuButtonsToDisplayGroup: makes the menu buttons at the correct positions in the menu based off of window size
- addTitleTextToDisplayGroup: takes in a title string and creates a new text object in the display group in the proper position based off of window size

#### MenuButton
- abstract class to define the common behavior of all of the menu buttons we would like to implement
- commonality in buttons having positions, sizes, resource bundle to pull strings from, and an action
- main methods: setButtonAction()
- setButtonAction(): defined for specific implementations of MenuButton, defines the behavior of the button on click

#### GridHandler
- creates the visual grid to be displayed and updated over the course of a simulation, represented as a list of polygons
- main methods: visualizeCells, setUpPolygons
- visualizeCells: iterates through the list of cells and list of polygons, updating the polygons' colors based on the state of the cells
- setUpPolygons: creates the proper number of properly sized and sided polygons and sets the polygons to cycle their states on click (chooses between setUpRectangles and setUpTriangles currently)

#### GraphHandler
- creates and updates the LineChart of states of cells over time to be displayed by the app 
- main methods: setupStateGraph, updateGraph
- setupStateGraph(): creates a LineChart object with the correct parameters given the simulation type and window size
- updateGraph(): takes in a list of data series and updates the graph based on this new information from updated cells

## Assumptions & Decisions

- Control for simulations is completely separate when multiple simulations are running simultaneously
    - the pairing of a simulation and visualization is completely independent in its operation and progression making it impossible in our current design for a higher level controller to control all simulations at once
- Parameters are immutable throughout the simulation
    - It would difficult to change parameters dynamically because it would require mutation of the parameterMap which is possible, but not yet supported in our implementation
    - This simplified our design because parameters remained final throughout the running of a simulation
- Configuration is a class that gathers data from an XML. Does not take in any parameters other than file to read from
    - to implement saving the current state of a simulation to an xml file this would require that we should probably not have configurer as static and changes the way we think of its functionality

## Adding New Features

### Configuration
- Adding new Simulation type
    - If the simulation requires additional parameters, an additional case statement should be added to the Switch in Configurer to add new parameters to the ParameterMap
    - As long as the simulation type is recognized by the Simulation, Configurer will relay the information regarding the new Simulation to the Simulation class
- Saving XML 
    - This would require a change in the way that Configuration in viewed within the project. Rather than simply taking in a file and creating a simulation or map of styling parameters, configurer would have to be able to take in parameters. 
    - Would likely require Configurer to be converted to static. 
    - If it is assumed that the simulation parameters are not changed(such as probCatch for Fire Simulation), then the existing XML could simply be edited in the < activeCells > field. 
### Simulation
- Adding simulation types
    - Create a new subclass of Cell that implements updateNext()
    - Add the states that are associated with your simulation to the State enum
    - Add your simulation type to the CellGrid Cell factory (createCellGrid())
- Creating different grid edges
    - Make CellGrid an abstract class where the body of the if statement in connectNeighbors called an abstract method implemented in subclasses
    - Put the current implementation in one CellGrid subclass and put a new implementation in another subclass
    - Create a CellGrid factory that chooses between the implementations based on a parameter in parameterMap
- Adding a new CellShape
    - Add a new enumerated type in CellShape with parameters

### Visualization
- Adding new menu items
    - text: the addTitleTextToDisplayGroup in MenuHandler could be generalized very easily to simply add text with any input string to any location in order to allow a user to display any wanted text in the menu or otherwise
    - buttons: new menu buttons would be created as new subclasses of MenuButton with their own specific actions defined with the call to setOnAction for the specific type of button. In addition the menu button would be added to the group in MenuHandler's method addMenuButtonsToDisplayGroup
    - shapes of cells: if new shapes for cells were to be implemented in visualization, a new method like setUpRectangles and setUpTriangles would have to be added to the GridHandler to deal with the specific placement and geometry of the shapes
    - new visual representations of the data (like the graph): a new handler would have to be created to handle this new asset (similar to graph handler) that would create and update the new visual object to be added to the display group in Visualization. This implies that it may have been smart to have a more generic handler class that was not static and could be implemented specifically for different visualization objects the user wanted to display.

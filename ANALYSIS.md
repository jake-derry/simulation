# CompSci 308: Simulation Project Design Review

### Jake Derry

> This is the link to the [assignment](http://www.cs.duke.edu/courses/compsci308/current/assign/02_simulation/):


## Overall Design

Reflect on the coding details of the entire project by reading over all the code.
- Describe the overall design of the complete program:
    - What is the high level design of each part (simulation, configuration, 
    and visualization) and how do they work together (i.e., what behavior, data,
     or resources each part depends on from the others).
     
     #### Simulation
     
     - The Simulation contains a CellGrid which it updates.
     - The CellGrid contains Cells which have a Neighborhood that is set by the Configuration.
     - Each Cell has an implementation of updateNext which applies the rules of a specific 
     simulation
     - The Simulation allows the Visualization to interact with the CellGrid, so it can iterate
     through the Cells
     
     #### Visualization
     
     - A Visualization is associated with a single Simulation
     - A Visualization contains both a graph and a grid
     - MenuButtons do many actions that affect the Visualization, its Timeline, or its Simulation
     
     #### Configuration
     
     - Creates Simulations and Visualizations based on the information stored in the XML files
     - Create a parameterMap that is handed to the Simulation and stores all the information about
     simulation settings
     
    - What is needed to add a new kind of simulation to the project (describe all
    parts of the code or resources that need to be changed to recognize new XML tags 
    and new Java automata rules)?
    
To add a new simulation type:
- Create a new subclass of Cell that implements updateNext()
- Add the states that are associated with your simulation to the State enum
- Add your simulation type to the CellGrid Cell factory (createCellGrid())
    
    - Are the dependencies between the parts clear and easy to find (e.g., public 
    methods and parameters) or do they exist through "back channels" (e.g., static 
    calls, order of method call, requirements for specific subclass types)? Note, 
    you can use IntelliJ to help you find the dependencies within your project.
        - The dependencies between packages are clear. 
            - The Configuration package only depends on the Simulation
        and Visualization classes.
            - The Simulation package is not depended on any class from the
            Configuration or Visualization class
            - The Visualization package is depended on both other packages, but
            only the Simulation class from the Simulation package.
        - The order of method calls matters for the Simulation calls of updateNext() and
        stepState()
            - This is because updating the next state must be done before stepping to the 
            next state
            - This dependency is problematic because it is not enforced within the Simulation class
- Read over and describe one of the components that you did not implement: I'm going to read over 
Visualization
    - What makes it readable or not (i.e., do classes and methods do what you 
    expect and have logic that is clear and easy to follow)? Give specific examples.
        - The methods and classes have names that are expected, like MenuHandler, GraphHandler, and
        GridHandler each handle the JavaFX objects associated with the Menu, Graph, and Grid respectively.
        - Often, variables need to be more specific because while using the most general object as possible is
        important, the object should be defined by what it is expected to do.
            - In the Visualization class, the polygonList is of List type instead of List<Polygon>. This is both
            difficult to understand and more prone to a bug.
        - The logic, though, with some methods is not straightforward. e.g. 
```java
    public static List setUpPolygons(int windowSize, int numRows, int numCols, Group group, 
                  Map stylingMap, int numSides, Simulation mySim) {
        boolean colOdd = true;
        boolean rowOdd = true;
        ArrayList polygonList = new ArrayList<Polygon>();
        double MENU_HEIGHT = windowSize/4;
        double CELL_HEIGHT = (windowSize - MENU_HEIGHT) / numRows;
        double CELL_WIDTH = windowSize*4/5 / numCols;
        double xOffset = (windowSize - CELL_WIDTH*numCols)/2;
        if (stylingMap.containsKey("cellSize") && (int)stylingMap.get("cellSize")*numCols < windowSize/2){
            CELL_HEIGHT = (int)stylingMap.get("cellSize");
            CELL_WIDTH = CELL_HEIGHT;
        }
        Iterator<Cell> cellIterator = mySim.getGrid().iterator();
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                Cell cell = cellIterator.next();
                Polygon polygon = new Polygon();
                switch(numSides){
                    case 3:
                    {
                        polygon = createTriangle(i, j, CELL_WIDTH, CELL_HEIGHT, MENU_HEIGHT, xOffset, colOdd, cell);
                        break;
                    }
                    case 4:{
                        polygon = createRectangle(i, j, CELL_WIDTH, CELL_HEIGHT, MENU_HEIGHT, xOffset, cell);
                        break;
                    }
                    default:{
                        polygon = createRectangle(i,j,CELL_WIDTH,CELL_HEIGHT, MENU_HEIGHT, xOffset, cell);
                    }
                }
                if (stylingMap.containsKey("outline") && (int)stylingMap.get("outline") == 1){
                    polygon.setStroke(Color.BLACK);
                }
                polygonList.add(polygon);
                group.getChildren().add(polygon);
                colOdd = !colOdd;
            }
            rowOdd = !rowOdd;
        }
        return polygonList;
    }
```
Within this code, I see a couple of issues:
        - I am unsure of what certain variable names mean because they have been abbreviated
        - The switch statement should be using the CellShape enumerated type to maintain consistency within
        the code base
        - This switch statement could be avoided by implementing an enumerated type specific to visualization that
        handled different polygons.
        - The createTriangle and createRectangle methods should be generalized to any polygon shape, so it should
        be createPolygon which would prevent using a switch statement as well
        - All of these issues, in addition to making it difficult to add to the code base, make the code less readable
   - What makes it encapsulated or not (i.e., can the implementation be easily changed
    without affecting the rest of the program)? Give specific examples.
        - I think the code is generally well encapsulated but there are instances where it is less encapsulated
            - For example, the setUpPolygons method in GridHandler returns a List rather than an ArrayList or a
            LinkedList. Yet, I think the returned value could even be simply an Iterable<Polygon>. The fact that 
            the List return value doesn't specify its type is concerning because while it makes the List more general,
            the method is only used to retrieve Polygons. 
   - What have you learned about design (either good or bad) by reading your team mates'
     code?
        - I have learned that design should be done as a group and that reviewing other's code is important to
        ensuring that the code base is clean, consistent, and well-designed. I have learned that while making variables
        as general as possible, there is a point where generality comes at the expense of clarity.
- Is the total code generally consistent in its layout, naming conventions and 
descriptiveness, and style? Give specific examples for or against by comparing code 
from different team members.
    - Naming conventions follow the camel case guideline, but some people abbreviate while others do not.
        - Example:
            - When I use the word `Column`, I spell it out completely, but Matt uses `Col` instead.
    - The JavaDoc comments vary in their styling

My JavaDoc comments:
```java
/**
 * This abstract class runs a simulation of any
 * type. This type changes how the simulation is
 * updated. The simulation contains an array of cells
 * that are the basis of the cellular automata simulation.
 *
 * This simulation constantly updates if it is running.
 *
 * DEPENDENCIES:
 *      Visualization
 *      Cell
 *
 * USAGE:
 *      After calling the constructor, call the step method.
 *      Call the pause and play methods to determine whether
 *      the step method does action or not.
 *
 * @author Jake Derry
 */
public class Simulation {}
```

Matt's JavaDoc comments:
```java
/**
 * @author Matt Harris
 * This class is responsible for maintaining and controlling the different assets to be displayed in the app including the menu, grid of cells, and graph of cell states
 * In addition, visualization calls the simulation to step forward and update the states of cells based on the rules of the simulation
 * Assumptions: the simulation will not need to be independently called to update independent of updating the visualization
 * Dependencies: Simulation, MenuHandler, GraphHandler, GridHandler, CellShape
 */
public class Visualization{}
```

I think just making the format of these would make the code look much more unified. In addition, our
descriptiveness in these comments should match, but our team didn't review the rest of the teams comment which
contributed to this issue.



## Your Design

Reflect on the coding details of your part of the project.
- Describe how your code is designed at a high level (focus on how the classes relate to each other through 
behavior (methods) rather than their state (instance variables)).
    
#### Simulation
- created by Configurer's getSimulation method
- creates and maintains CellGrid object with state information for all cells and definition of 
neighborhood to be used in the simulation
- simulation only calls its cells to update, it does not implement simulation rules
#### CellGrid
- CellGrid makes and contains individual cells, created at the contruction of a simulation 
- The cell grid offers the Iterable interface which allows cells to update themselves when called by simulation
- main methods: createCellGrid, connectGrid, connectNeighbors
- createCellGrid: called at the construction of a CellGrid object, generates an array of cells 
with the correct parameters and cell type
- connectGrid: called at the construction of a CellGrid object, calls connectNeighbors for all cells in the grid 
- connectNeighbors: called at the construction of a CellGrid object, connects cells to their neighbors 
based on the definition of the neighborhood for the simulation, parsed from xml

#### Cell
- abstract class that updates itself and maintains a current state, next state, and its own neighborhood
- implementations of cell define the behavior of updateNext based on the simulation rules with which a 
cell is associated (fire cell, rps cell, etc)

#### Neighborhood
- class to define the neighborhood of a cell, designed to make it easier to implement new types of simulations 
and new definitions of neighborhoods
- Implements the Iterable interface
- main methods: chooseNeighbor, getRight/Left/Down/Up
- chooseNeighbor: called from implementations of cell, randomly chooses a neighbor that has one of the acceptable 
states passed in to the method call
- getRight/Left/Down/Up: called from implementations of cell, returns the cell in the correct direction

#### State
- enumerated types to define the different states of cells used in the implementations of cells and connects 
the text in the xml pertaining to cell state to a specific, enumerated cell State
- main method: getState()
- getState(): maps the defined State text in the xml to the correct enumerated State type

#### CellShape
- enumerated types that define the mapping of the geometric shape of a cell to the displacement (how to get 
neighboring cells)
- main methods: getDisplacements, matchShape
- getDisplacements: returns all the displacement pairs for an associated CellShape
- matchShape: maps the defined shape text in the xml to the correct enumerated CellShape type
    
    
- Discuss any remaining Design Checklist issues within your code (justify why they do not need to be fixed 
or describe how they could be fixed if you had more time).

One issue that remains that could be factored out is the creation of CellGrids. CellGrids, rather than having a factory
class of Cell that creates a Cell of a particular type when given a string, uses a switch statement to fill the CellGrid.
This could be fixed by creating a factory class. In this case, the switch statement can be replaced with 
`myCellGrid[i][j] = CellFactory.getCell(parameterMap)`. The CellFactory could create cells based on the parameters.

- Describe two features that you implemented in detail ? one that you feel is good and one that you feel 
could be improved:
    - Generalizing the CellShape to define Neighborhoods for each cell
        - I designed it this way ensure easy definition of neighborhoods (4-neighbor, 8-neighbor, corners, etc.) and easy 
        definitions of new cell shapes (triangle, hexagon, rectangle, etc.)
        - The neighborhoods are dependent on strings that are not stored in properties files and 'displacement' coordinates
        that make it easy to find neighbors which could be put into XML files (I debated this with Jonah and ended up
        going with him and hard coding the displacements as not to cause conflict, but I know it would be better to do this
        as an XML)
    - Creating the CellIterator class
        - I think the CellIterator class, while it gives space for flexibility within the code if needed later, fails to
        use existing Java modules. Using a Iterator that already exists for a Queue would have worked for this class
        instead of creating my own, but I did decide to do this because it made the iterator() methods in Neighborhood and
        CellGrid more readable.
        - I did make the assumption that the only two uses of this iterator would be for the CellGrid and Neighborhood. 
        Although I can't think of another use, making it strictly defined for these two classes was a strict restriction 
        on the code
        
## Flexibility

- Describe what you think makes this project's overall design flexible or not (i.e., what new features do you feel it is 
able to support adding easily).

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


- Describe two features from the assignment specification that you feel were made easier to implement by the design and 
why.

- Running multiple simulations at once was extremely trivial
    - Each simulation and visualization work like Simulation is the model and Visualization is the view and controller.
    It was easy to do because we displayed each simulation in a separate window. The visualization has its own buttons that
    are already built in.
    
    - It was easy to add the triangle cell shape, because all it took was finding the 'displacements'
        - The enumerated type CellShape gives great flexibility for creating new CellShapes that allows the user flexibility
        with defining the neighborhood of each cell

- Describe two features from the assignment specification that you did not implement in detail (these can overlap the 
previous ones but must be discussed from this different perspective):
    - Implementing multiple simulations
        - I choose this because being able to run multiple simulations shows off the flexibility of our design to the
        point that each simulation and visualization is disjoint from the rest of the application
        - To implement this feature, only the LoadNewSimButton was needed.
        - The button is closed off to anything besides the Visualization which should call to create it and place it
        into the JavaFX root.
        - It would be difficult to extend this to place multiple visualizations in a single window which is by nature of
        the fact that the grid, graph, and menu are not objects in the code base.
        
    - Adding cell shapes (visually)
        - Adding a cell shape requires the Simulation and Visualization packages to react, but I would like to focus on 
        how the Visualization must react.
        - As I discussed earlier, the code that produces rectangles and triangles is based on a switch statement rather
        than generalized to all polygons. While the math for this is complicated, it is organized enough that it could be
        coded.
        - Edits to the GridHandler class are required
        - The current code makes it difficult to add another cell shape visually because it requires changing
        a switch statement and implemented the code that creates the whole grid which should be generalized to all
        polygons in the first place

## Alternate Designs

- Describe which classes would be affected by changing the Grid's data structure (say from a 2D array to a Map).

The only class that would be affected would be CellGrid.

- Describe how well (or poorly) the original design handled the project's extensions to the original specifications and 
how these changes were discussed and decisions ultimately made.

The original design was difficult to deal with. In it, each Cell held the visual element that represented it which made
the relationship between Simulation and Visualization less representative of what it should be. A lot of big structural
design changes were made to the project to accommodate:
    - Simulation rules would be implemented by the Cells
    - Cells would not store their own visual information
    - The grid of cells which existed as a 2-D array in Simulation would be wrapped by a new class and only accessed
    as an Iterable
    - Each cell would have a neighborhood that would be Iterable
    - The CellShape would make it easier to define the neighborhood of each cell
    
The configuration was rigid. Instead, we introduced the concept of a Map of parameters that would hold
information about simulation parameters, Cell types, etc. for the simulations.

The visualization was also highly intertwined with the Main class (CAApp). We separated them by storing a simulation
inside a visualization rather than the other way around which it was in the original design.

- Describe two design decisions discussed by the team about any part of the program in detail:
    - Relationship between Visualization and Simulation
        - Choices: Simulation is an object stored in Visualization; Visualization is an object stored in Simulation;
        Simulation is stored in Visualization and Visualization is stored in Simulation
        - Our discussions revolved around a couple of arguments: 
            1. Making semantic sense, 
                - The idea that a visualization visualizes a simulation seems to imply that simulation should be an 
                object on a visualization
            2. Providing access to the
        information that each class needs, and 
                - A visualization needs to be able to change the simulation in certain ways, but not the other way around
                because the visualization is constantly reading the results of the model's changes because it is
                controlling them
            3. Creating a flexible design that allows multiple visualizations to exist
        for a single simulation
                - This may imply that a simulation should have a list of all its visualization instead?
        - Rather, we decided that the visualization should have the simulation object. Yet, in implementation, Matt
        made several parts of the Visualization module static which was something that we discussed, and I encouraged 
        thoughts about, but now see that it does not very well model how a visualization can have many parts, like
        several menus and grids and graphs
            - I would prefer this more object oriented way of thinking
    - The Playable interface
        - When I initially came up with my new design of the Simulation module, one of the key components was that the
        CAApp (or whatever class is controlling its turning on and off) would only have access to it as a Playable
        interface
            - This interface included the play(), pause(), and step() methods
        - The alternative design was to give all controllers (or the CAApp in our original design) complete control over
        all methods that it had to offer
        - The trade-off with having this would be that the Playable interface would only be used by one class (Simulation)
        and that the encapsulation accomplished only complicates the fact that sometimes the object needs to be accessed
        completely like when the user changes a single cell's state
        - The benefit would be encapsulation that would block the Simulation class from misuse
        - I would have preferred to use the Interface although it was introduce some complications, it would be largely
        beneficial to helping the user understand the functions of the Simulation and limit the power over it given to
        certain parts of the design

## Conclusions

- Describe the best feature of the project's current design and what did you learn from reading or implementing it?

I think the flexibility of the neighborhood is worth noting. The neighborhood and CellShape allow users to easily 
redefine the CellShape and allow more neighbors (even adding 2 levels of moore neighbors would be possible with the
given design) or create entirely new CellShapes and have them encorporated in the XML.

- Describe the worst feature that remains in the project's current design and what did you learn from reading or implementing it?

The worst feature is that the Visualization class does not have a heirarchy of objects that includes all Visual elements.
I would enjoy a hierarchy where a Visualization is an abstract class that extends menus, grids, and graphs. I learned
from reading this that I don't have complete control of the project around me, and that is simply how it is going to be.
While I should give my team feedback on what I think about their design, as long as an API is in place, I should just 
allow them to design rather than micromanage and tell them how to do their part of the project.

- To be a better designer in the next project, what should you 
    - start doing differently: reviewing the team's code to make the code base consistent, fluid, and more understandable
    - keep doing the same: thinking critically about design and introducing abstractions and encapsulations
    - stop doing: demanding that my ideas are the only ideas that matter (which I caught myself doing several times in
    the project). Hearing from others improves the design.
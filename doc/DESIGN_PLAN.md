# Design Plan

Jake Derry (jad111)
Jonah Knapp (jk366)
Matt Harris (msh54)


## Introduction

The goal of this project is to make an organized application to simulate and visualize several different cellular automatas (CAs). This application must be able to incorporate a multitude of "rules" for different CAs and thus the design of the application is focused on allowing the implemented rule set to be very flexible.

In general, the application will be comprised of four main actions: configure the simulation, carry out the update logic of the simulation, visualize the current state of the simulation, and respond to user input.


## Overview

The main application to be launched will be a JavaFx program called CAApp. This serves as the highest level class where all other objects needed for the application will be created and called. In the start method, a configurer will be created to read an xmlfile and generate the simulation. Then, a visualization will be created based on the simulation. In the step method the simulation object can be told to update the cells, pause the simulation, or create a new simulation to be displayed based on user input. 

The configuration object will simply be able to read a file and return a proper simulation object based on the contents of the file.

We will be using an inheritance structure for our different types of simulation objects. The simulation class itself will be abstract with several different types of implementations based on the desired CA type. All will need to maintain a 2-d array of cell, have an update method with logic specific to the subtype of simulation, be able to pause the progress of its updating indefinitely, and return its array of cells.

Cells themselves will be an object created with an integer state, integer nextState, and an ImageView. Cells will have their nextState updated by the Simulation and will be called to stepState by visualization; given a list of color values, setting state=nextState and setting the color of their ImageView based on the passed list.

The visualization object will be used to setUp the scene being displayed by CAAp by adding all of the cell ImageViews to the display group. It also will maintain a list(colorValues) allowing int state to be mapped to a display color. The Visualization object will call cells to stepState and update their colors based on this list.


![](https://i.imgur.com/A8eSWde.png)



## User Interface
The basic concept of our user interface is shown in the picture below. In addition to what is shown there, there will be error handling that will create a popup dialogue box in the event that the user's new configuration file cannot be found.

![](https://i.imgur.com/Xhhpnlg.jpg)

## Design Details

### CAApp

This section of the code will house the main functionality of JavaFx. In the start() method, it will use the configuration, simulation, and visualization classes to create the scene and root. Then, with the step() method, the parameters within the grid of cells will change based on the simulation parameters. The simulation and visualization classes will work in conjunction to achieve this. Another component of the CAApp class will be the controller, which will handle user input. This input will have the ability to start a new simulation and change paratemeters within the current running simulation.

### Configurer 

The main functionality of the configurer class will be to read and analyze the supplied XML file. Based on the parameters within the file, it will return a simulation. The simulation is what will be ultimately displayed on the screen and updated. Note, it will not handle changes in configuration based on user input. This functionality will be housed in the simulation itself. 

### Simulation 

The general purpose of the simulation class will be to update the cells within the grid. In coordination with CA, this updating will be done iteratively through steps, so that a change in one cell will not incur a change in another cell in the same time step. The updating of the cell will be done in the update() method. This method will update the states within each of the cells through calls to cell objects. Specifically, it will use the setNextState() within the cell class to set the next state of the individual cell. The way in which each of the cells will be updated is determined by the parameters that the configuration class created the simulation with, as well as parameters that are changed by the user. There will also be a stop() method, which will have the functionality of stoping the simulation based on user input. Finally, getCells() will simply return the cell array. 

### Cell 

The cell class holds the state, nextState, and image view (myImageView) associated with each cell in a CA simulation. The nextState is set with the setNextState() while the state variable cannot be changed directly. stepState() sets the state to the nextState and updates the image view's color to match the state based on a list of colors taken as a parameter. Additionally, there is a getter method for the imageView that allows the cell image views to be added to the root. This setup ensure that the state variable cannot be changed directly. Instead, the nextState must be changed and its value, when stepState() is called, changes the color of the image view, so no state goes unseen.

### Visualization

The visualization class will have the responsibility of creating and updating what is displayed on the screen. Variables that it will keep track of are a list containing color mappings of states/colors and a visualGrid that corresponds to the grid of cells. In the setup() method, it will take a Group as the input and add the appropriate imageViews to the Group. This includes the buttons that the user will interface with, as well as any relevant information that will be shown on the screen. This portion of the setup will be handled within setupMenu(). Additionally, setup() will call getCells() within the simulation class to determine the dimensions of the cellular array, as well as the initial colors of each of them. Another method within the visualization class will be visualize(), which will step through the cells and update their colors appropriately. This updating of colors will be done using the stepState method in simulation.

## Design Considerations

### The way state gets updated
We ultimately decided that the state will be updated by the simulation. The cell will be a very simple class that will update its state at the request of the simulation. There were several alternatives to this method. Initially, we planned on having the cells update their state. However, after further analysis, we realized that this would not be possible due to simulations such as segregation, where a return value from the cell would be required. This return value would be difficult to control, since not all game types would need to return a value. However, a benefit to this method would be that the current state/future state would be controlled by cells themselves, rather than the simulation. Another alternative to this method would be to keep another separate array for the future state. A benefit to this method would be that there would be a clear distinction between a cell's current and future state. However, this would be somewhat unnecessary because a lot of additional information would be saved. 

### Visualization as object in Simulation

Our team discussed whether or not to store Visualization objects in the Simulation class or in the CAApp class. The advantages to putting the visualization object in the simulation class is that the grid would be more closely accessible to the visualization object. Additionally, putting the visualization of a simulation seems semantically sound. 

On the other hand, there were more roles that were not simulation specific that we hoped the visualization class would handle like setting up the root of the scene. To take away these roles from visualization would require placing them in the CAApp class which should be as minimal as possible because it is the JavaFX Application of our program. Our decision was to store the visualization object in the CAApp class to ensure that it could do more non-simulation specific actions.

### Controller class

Additionally, our team discussed possibly handling all user input in a controller class to keep the CAApp class as minimal as possible. Creating an object to handle user control would help organize all of the possibilities for user control.

Additionally, the controller class introduced additional difficulties. The controller requires access to the active simulation which it can also change. It seemed that the controller was far away from where the simulation was set up, so we decided to handle user input in the CAApp class instead. While it will fill more space in the CAApp class, the controller class would likely produce more difficulties than simplications.

## Team Responsibilities
**All responsibilities were decided randomly based on predetermined tasks. 

### Jake Derry

#### Simulation

- Writing cell class
- Writing simulation abstract class
- Implementing different simulation rules


### Jonah Knapp

#### Configuration
* How to read/format XML file 
* Checking in with others and helping out to see what can be done 
* Ensuring that the code is formatted consistently


### Matt Harris

#### Visualization
* setting up visual playing field: cells' ImageViews (sizing, position, color etc), menu and buttons
* handling user input (buttons etc)

### Team Plan
The general plan for how our team will complete this project is to divide the work into the main three pieces we have been considering throughout this planning phase: Configuration, Simulation, and Visualization. The general tasks for each of these pieces are shown above.

The first goal will be to have a way to read from a file and start a single type of simulation with a basic UI. This will make sure that early in the project our team is considering how our individual pieces will have to fit together. In addition, becuase the Configuration piece feels slightly smaller than the other two pieces, we are planning on having Jonah try to finish relatively early and then act as a flexible helper. This will allow us to redistribute our resources as needed once a substantial part of the project has been completed. 

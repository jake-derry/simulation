## Refactoring Discussion 

#### Making Cell State an Enumerated Type
* In order to make the addition of Cell States simpler, we converted the existing cell states from integers to 
enumerated types. This will allow users who want to add new simulations to easily add new states 
* Removes the need to declare states at the beginning of each cell, eliminating possible 
redundancy

#### Visualization Integrated with existing code
* Note: This was done before creating the refactoring-lab branch
* We integrated a new version of visualization into the existing code. This required
refactoring of other area of the code.  

#### Timelines added to Simulation (rather than CAAPP)
* Allowed for a more efficient and seamless creation of a new simulation 
* This design change will help us down the road, when we try to display a completely new simulation 
while simultaneously displaying the already running simulation. 

#### Discussed possible future refactoring 
* Making visualization static. This would eliminate unnecessary components that are involved with 
instantiating a Visualization object. It would also simplify the integration of visualization with the 
rest of the code. 
* How to handle initial setup of the cellular array. Discussed potentially taking this responsibility from 
configuration and transferring it to simulation, which would ensure that simulation is 
the only package that touches the cell array.
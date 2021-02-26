# DESIGN Document for Cell Society Team 01
## Names
Shaw Phillips sp422  
Jiyun Hyo jh160  
Harrison Huang hlh38  


## Roles
- Shaw: I created the Decoder class along with the XMLParser and XMLException classes, which dealt with prompting the user to select a file, parsing it, and passing the appropriate parameters to the Simulation Engine in order to setup the simulation. I added the XML template files and setup the ability to save the simulation's configuration. I also implemented error checking and exception handling for the XML files through the use of JavaFX alerts.  

- Jiyun: I designed the overall structure of the project using StarUML. Facilitated better work communication by integrating Gitlab to Slack (all members get notified with Gitlab notifications in real-time through Slack). Was in charge of 1) Main.java, simulationengine package, grid package (3 parts of Controller component) and 2) entirety of Models. I also wrote the templates and its descriptions, formatted the files according to Google format, and helped refactor parts of the classes for better design. I also followed up with team members to facilitate communication and to make sure we have implemented everything specified by the spec. Spent time refactoring. Implemented classes: Main, model package 

- Harrison: I created the view component of the project, of which the main three components are the SimulationScreen, GridGraphics, and SidePanel. I also had to provide functionality of the controls put in SidePanel by coordinating with the SimulationEngine. I implemented the resource property file and CSS file for the front end in order to have more flexibility in the display. I had to manage the view and state of the grid, as well as allow for resizing. This was extended in the complete version with the addition of an independent Graph view, multiple simultaneous simulations, additional language support, and color palette functionality, among other things.

## Design Goals
To make a Cellular Automata simulator capable of handling any model while also maintaining good design principles and division of roles. This simulator would also ideally allow for easy implementation (minimal number of new classes and code) of new simulations beyond the ones we have programmed.


## High-Level Design
We adopted the MVC framework. The `XMLParser` would initialize the simulation parameters and identify the chosen simulation, after which the simulation engine would act as the controller connecting the model and view, which are kept separate. The simulation is launched in `Main`, which subsequently calls the SimulationEngine class. `SimulationEngine` relays all necessary parameters to the models. `GridManager` builds and updates the grid based on the rules. Every time the states are updated, `SimulationEngine` passes the necessary information to be displayed to the view component


## Assumptions or Simplifications
Assumption was that other coders who are completely unfamiliar with the code could easily add new features. Another assumption linked the controller and view to each other as dependencies in order to allow the view to more easily control how the simulation engine functions.


## Changes from the Plan
- We initially planned on having a package for each model that would contain multiple classes, but after a few design considerations, we realized it would be more efficient to instead have a single class for each model to govern its behavior. The only exception to this rule was with Foraging Ants, which required multiple classes due to its complexity. 
- We also initially planned on using a list of lists to store the cells of the grid, but we later changed that to a 2D array of State objects.

## How to Add New Features
### Model 
 Create a new Rules class for the simulation. Understand the parameters necessary to simulate the model by doing research on the new model. Pass the necessary parameters to the constructor. Add the possible types of the model to possibleTypes List. Add the possible colors of the model to `possibleColors` List. Write your logic for updating each cell in `decideState`. Make sure you understand the parameters that are passed into the `decideState` method. Make sure you understand how State class works.

### Controller
First, you need to create new XML files for the new model. Then create an additional `initializer` method to parse each unique parameter for the new simulation. Afterwards, make new methods to pass the parameters information to the `SimulationEngine` class, which will call upon a new `Rules` class to initialize the simulation with the given parameters in the `initializeModelConstructors` method.

### View
For different extensions of the view, visual components can either be added or modified to allow for different implementations. For example, adding a new button or new sliders would simply require the use of the `makeButton` method and adding it to the correct location, `SidePanel` or `TopPanel`. Adding differently shaped cells in the grid would require the use of a new method to make a different shape, like how `makeTriangle` and `makeHexagon` were added as possible implementations. Adding different color palettes or different languages would only require adding a new CSS or resource property file, respectively, which implement all the specifications, and adding these new files as possible selections under the correct combo box/dropdown box.

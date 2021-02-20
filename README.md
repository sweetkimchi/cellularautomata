Cell Society
====

This project implements a cellular automata simulator.

Names:
Ji Yun Hyo - jh160<br>
Shaw Phillips - sp422 <br>
Harrison Huang - hlh38

### Timeline

Start Date: February 6th

Finish Date: February 14th

Hours Spent:
Jiyun - 40 hours (February 6th - 14th) <br>
Harrison - 20 hours

### Primary Roles

Shaw: Shaw was in charge of the Configuration package, which entailed parsing all the XML files and
sending model-specific parameters to the Model package to initialize the simulation.

Jiyun: I designed the overall structure of the project using StarUML. Facilitated better work
communication by integrating Gitlab to Slack (all members get notified with Gitlab notifications in
real-time through Slack). Was in charge of 1) Main.java, simulationengine package, grid package (3
parts of Controller component) and 2) entirety of Models. I also wrote the templates and its
descriptions, formatted the files according to Google format, and helped refactor parts of the
classes for better design. I also followed up with team members to facilitate communication and to
make sure we have implemented everything specified by the spec.

Harrison: I created the view component of the project, of which the main three components are the
SimulationScreen, GridGraphics, and SidePanel. I also had to provide functionality of the controls
put in SidePanel by coordinating with the SimulationEngine. I implemented the resource property 
file and CSS file for the front end in order to have more flexibility in the display. I had
to manage the view and state of the grid, as well as allow for resizing.

### Resources Used

1. Shaw
    - Professor Duvall's example XML Parser
    - Java Tutorial on Reading XML Data
2. Harrison Huang
    - [JavaFX Tutorial](https://zetcode.com/gui/javafx/intro/)
    - [Layout Panes](https://docs.oracle.com/javafx/2/layout/builtin_layouts.htm)
    - [GridPane Tutorial](https://www.tutorialspoint.com/javafx/layout_gridpane.htm)
    - [Slider documentation](https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/Slider.html)
    - [Changing speed of AnimationTimer](https://stackoverflow.com/questions/30146560/how-to-change-animationtimer-speed)
    - [Label text wrapping](https://stackoverflow.com/questions/30146560/how-to-change-animationtimer-speed)
    - [CSS Guide](https://openjfx.io/javadoc/11/javafx.graphics/javafx/scene/doc-files/cssref.html)
    - lab_bounce code for Resource Property and CSS
3. Ji Yun Hyo
    - [Branching and Merging](http://gitready.com/beginner/2009/01/25/branching-and-merging.html)
    - [Enumerated types](https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html)
    - [MVC model](https://www.tutorialspoint.com/mvc_framework/mvc_framework_introduction.htm)
    - [Game of Life rules](https://playgameoflife.com/)
    - [Schelling's simulation](http://jasss.soc.surrey.ac.uk/15/1/6.html#:~:text=The%20Schelling%20model%20of%20segregation%20(Schelling%201971%2C%201978)%20is,148$)$.)
    - [WaTor Model Wikipedia](https://en.wikipedia.org/wiki/Wa-Tor)
    - [WaTor Model simulation example](https://beltoforion.de/en/wator/)
    - [Modeling and simulation](https://maxstrauch.github.io/projects/mod-sim/index.html)
    - [Four Pillars of Object Oriented Programming](https://medium.com/@benjaminpjacobs/the-four-principle-of-object-oriented-programming-f78600f62608)
    - [MVC model 2nd resource](https://www.educative.io/blog/mvc-tutorial)
    - [StarUML Documentation](https://docs.staruml.io/)

### Running the Program

Main class: Main.java

Data files needed: All necessary XML files are under "data" folder

Features implemented: WaTor Simulation, Percolation, Spreading of Fire, Game of Life, Segregation; 6
templates for each; simulation screen with functional start/stop/reset buttons, speed slider,
ability to load new XML files, dynamically updating and sized grid

### Notes/Assumptions

Assumptions or Simplifications: The assumption in the beginning was that we would need a bunch of
classes for each model. However, along the way, I realized that all the models did not really need
to have a lot of the same classes. For example, I ended up getting rid of the Cell classes for all
the rules because it was evident that State and Cell performed the same thing. Also, because I came
up with the Rule abstract class, we did not need to have a separate builder for each one.
GridManager was able to take in all the parameters for all models and produce the starting states
and update the cell states successfully. Therefore, the design became much "simpler" and more
efficient than what we had initially imagined. As of the basic implementation, all three
components (MVC) are working together by informing each other of its states but do not specifically
have to know the inner-workings of each class in order to cooperate. One simplification for the 
resource property file is that the language (English) is currently stored as a private static final 
String rather than by being determined by an input to the main class. Another simplification is 
that the current method of updating the Grid is by clearing all the Rectangles and re-adding
them all one-by-one, while it would be better in the future to instead simply change the color of
each of these Rectangles.

Interesting data files: (Jiyun) I manually tested out the templates and have selected the ones that
produce interesting results. I think all the templates are worth your time to checkout. I tried to "
model" real life situations with the models.

Known Bugs: The grid resizing only works when the simulation is running, so when the simulation is
stopped, the grid will not resize if the window size is changed. When Load New is pressed but no
file is selected, the program will throw a NullPointerException, though this does not affect the 
overall program functionality. The descriptions can also disappear too soon if a new file is not
selected.

Extra credit: None

### Impressions

Jiyun: Personally, I really liked working on this project. I liked how Cellular Automata could be
used to simulate real-life situations. I also liked witnessing how a good design could help out
immensely throughout the project.

Harrison: I think it was really cool that we were all able to put together a working project all
while trusting each other to do our own part. While largely independent of the Model, I could focus
more on implementing features for the view and improving the UI.
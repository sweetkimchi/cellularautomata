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
Jiyun - 40 hours

### Primary Roles

Shaw: Shaw was in charge of the Configuration package, which entailed parsing all the XML files and
sending model-specific parameters to the Model package to initialize the simulation.

### Resources Used

1. Shaw
    - Professor Duvall's example XML Parser
    - Java Tutorial on Reading XML Data
2. Harrison Huang
    - dummy
    - dummy
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
templates for each;

### Notes/Assumptions

Assumptions or Simplifications: The assumption in the beginning was that we would need a bunch of
classes for each model. However, along the way, I realized that all the models did not really need
to have a lot of the same classes. For example, I ended up getting rid of the Cell classes for all
the rules because it was evident that State and Cell performed the same thing. Also, because I came
up with the Rule abstract class, we did not need to have a separate builder for each one.
GridManager was able to take in all the parameters for all models and produce the starting states
and update the cell states successfully.

Interesting data files: (Jiyun) I manually tested out the templates and have selected the ones that
produce interesting results. I think all the templates are worth your time to checkout. I tried to "
model" real life situations with the models.

Known Bugs:

Extra credit:

### Impressions


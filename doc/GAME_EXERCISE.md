# Simulation Lab Discussion

## Breakout with Inheritance

## Names and NetIDs
Ji Yun Hyo - jh160
Shaw Phillips - sp422
Harrison Huang - hlh38

### Block

This superclass's purpose as an abstraction:
```java
 public class Block {
     public int health ()
     // no implementation, just a comment about its purpose in the abstraction 
     //keeps information about the block
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class ExplodingBlock extends Block {
     public int something ()
     // block that explodes when health = 0, destroys surrounding blocks
 }
```

#### Affect on Game/Level class (the Closed part)
No effect


### Power-up

This superclass's purpose as an abstraction:
```java
 public class PowerUp {
     public int something ()
     //Controls logic for dropping powerups from blocks, collisions with the paddle
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class Laser extends PowerUp {
     public int something ()
     //Follows the superclass powerup behavior, i.e. falling from blocks, but gives a unique effect
     // creates new object upon obtaining the powerup
 }
```

#### Effect on Game/Level class (the Closed part)



### Level

This superclass's purpose as an abstraction:
```java
 public class Level {
     public int something ()
     // creates and loads level, determines rules for how the game functions 
 }
```

#### Subclasses (the Open part)

This subclass's high-level behavorial differences from the superclass:
```java
 public class BossLevel extends Level {
     public int something ()
     // creates different type of level, has alternate rules for how the game functions
 }
```

#### Affect on Game class (the Closed part)



### Others?




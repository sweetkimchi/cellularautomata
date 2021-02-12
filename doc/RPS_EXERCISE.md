# Simulation Lab Discussion

## Rock Paper Scissors

## Shaw Phillips (sp422), Harrison Huang (hlh38), Jiyun Hyo (jh160)

### High Level Design Ideas

Game engine class that determines functions of the game, deals with presenting outputs. Match class
will manage each match between players, deciding which one wins. Player class will deal with storing
each player's score and their weapon choice for each round played. Weapon class handles the weapon
file and information about each weapon.

### CRC Card Classes

This class's purpose or value is to manage something:

```java
 public class Game {
     public startMatch();
     public void addPlayer();
     public void setMatchup(Player p1, Player p2);
     public boolean wonGame(Player p);
     private void resetGame();
 }
```

This class's purpose is to manage each match:

```java
 public class Match {
     public Player winner;
     public Match(Player p1, Player p2);
     private playMatch();
     public Player getWinner();
 }
```

This class's purpose is to manage players

```java
public class Player{    
    public Player(){
        private int score;
        private String weapon;
    }
    public void incrementScore(){
        score++;
    }
    private String getWeapon(){
        Weapon = Console.getInput("Enter your weapon: ");
        
    }
    public void resetScore(){
        score = 0;
    }
    public int getScore(){
        return score;
    }
}
```

This class's purpose is to manage information about the weapons.

```java    
public class Weapon{
    private int weaponNum;
    private Image weaponImg;
    
    public Weapon(){
        weaponNum = 0;
        weaponImg = DEFAULT_IMAGE;
    }
    public Weapon(int weaponNum, Image weaponImg){
        this.weaponNum = weaponNum;
        this.weaponImg = weaponImg;
    }
}
```

### Use Cases

* A new game is started with five players, their scores are reset to 0.

 ```java
 Player player = new Player();
 player.resetScore();
 ```

* A player chooses his RPS "weapon" with which he wants to play for this round.

 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```

* Given three players' choices, one player wins the round, and their scores are updated.

 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```

* A new choice is added to an existing game and its relationship to all the other choices is
  updated.

 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```

* A new game is added to the system, with its own relationships for its all its "weapons".

 ```java
 Something thing = new Something();
 Value v = thing.getValue();
 v.update(13);
 ```


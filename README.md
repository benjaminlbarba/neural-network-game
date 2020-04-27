# MCIT-591 Final Project

### Overview

In light of the Blackjack Solitaire game that we designed in project 3, our group is proposing to
develop a 2-dimensional Pac-Man game, in which the player controls Pac-Man, who must eat all
the "dots" and avoid the "ghosts" within a enclosed maze. If Pac-Man makes contact with the
ghosts, he will lose a life and the game ends if Pac-Man loses all his lives.

### Goals

* **Primary Goals:** The board UI can be displayed properly and users are able to control the
Pacman to move. Scores can also be recorded. Game will terminate if the pac man hits a
ghost.
* **Stretch Goals:** Different levels of the game are implemented, with different numbers of
ghosts and speed. There can be a 'treasure' entity that provides the pec man the ability
to overcome the ghost in the next 20 seconds.

### Specifications

Ghost (Yufei): Generally, the game starts with 4 ghosts. As the levels increase, the number of
ghosts gradually increases each level. In addition, the speed of movement for each ghost also
increases with each level, increasing the difficulty for pacman to avoid them. Implementation
wise, the Ghost class should consist of variables for starting position, current position, and
speed of movement; its methods should include path-finding (based on the map and where
pacman is), moving, and killing (when in contact with pacman).

Board (Liyu): The board will be implemented with Java JWT (Abstract Window Toolkit), which is
an API to develop GUI for windows-based applications. Size of the board is configurable,
pacman and ghosts can be moved in the board. A method the check current status will be
invoked after each move. Current score will also be updated.

Pacman (Xizi): Pacman positional variables using arrays, methods for changing positional
variables when keyboard strokes given, animations of Pacman on the Board after positional
arrays change and move Pacman to its corresponding location, methods for drawing Pacman
within the maze as well as interactions with other classes.


### Instruction for installing Slick Library
The library can be downloaded from https://slick.ninjacave.com/. 

For IntelliJ IDEA windows, open project structure -> modules -> dependencies -> add JARs or directories -> choose all the files in the unzipped directory.

Foe Eclipse windows, open project properties -> Java Build Path -> Libraries -> add external JARS -> choose all files in the unzipped directory -> expand JRE system library -> click Native library location -> add all .dll extension in the unzipped directory -> click apply.


### Instruction for running
The project used 2D-Slick library, which can be found in the Libraries folder. Upon adding the library in Project Structure, the game can be runned from the main method in GameStateManager class. The direction of Pacman is controled by Up, down, left, right Arrow keys. The High Score option, when pressed, gives you a historical high score for the game.

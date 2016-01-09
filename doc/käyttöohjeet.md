##Running the game

1) Download and install [Java](https://www.java.com/en/download/)

2) Download and extract NethackSokoban.zip

3) Double click the .jar file to start the game

##Rules

You win the game by getting the character to the green circle. You do this by filling holes in your way with boxes.

You can push the boxes in the four cardinal directions but not diagonally. 

You can only push one box at the time. 

Pulling boxes is not allowed.

This game follows the movement rules of Nethack which means diagonal movement is possible as long as you are not squeezing between walls or boxes. Picture illustrates this better than words:

![example moves] (https://raw.githubusercontent.com/karvonen/NethackSokoban/master/doc/util/DiagonalMoves.png)


##Movement keys:
###Numpad
    7 8 9
     \|/
    4- -6
     /|\
    1 2 3

###Vi keys with added roguelike diagonals
    y k u
     \|/
    h- -l
     /|\
    b j n

##Adding levels

It's easy to add maps to the game. However Board.java has hardcoded grid of JLabels for sprites and currently the size is 18 tiles in height and 32 tiles in width. If you want to add a bigger level than this you will need to increase these numbers and the windows size in GUI.java to match.

The program checks for text files in /levels/ starting with 1.txt incrementing by one after each file until no file is found. So to add a level you need to add exactly 1 to the currently biggest level. After that the levels will loaded automatically and they appear in the menu bar's level selector.

In the text files the first number is the width of the level and the second is the height.

Here are all the supported characters for creating levels:

    # wall
    . floor
    0 box
    < victory tile
    @ player starting position
    ^ trap


Note that the level selector will probably get a scroll bar if you add one level to the default 8. So while your new level might no be instantly be visible, it's there, just scroll down.
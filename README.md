# JavaLearning

This repository contains several projects I created during the study of Java.

## ChessGame.java
A chess game of two players

Once 5 pieces of same player construct a continuous line, that player wins.
```
   1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 
A  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  
B  .  .  .  .  x  .  .  .  .  .  .  .  .  .  .  
C  .  .  .  .  .  x  .  .  .  .  .  .  .  .  .  
D  .  .  .  .  .  .  x  o  .  .  .  .  .  .  .  
E  .  .  .  .  .  .  .  x  .  .  .  .  .  .  .  
F  .  .  .  .  .  .  o  x  x  .  .  .  .  .  .  
G  .  .  .  .  .  .  o  .  .  .  .  .  .  .  .  
H  .  .  .  .  .  .  o  .  .  .  .  .  .  .  .  
I  .  .  .  .  .  .  o  .  .  .  .  .  .  .  .  
J  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  
K  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  
L  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  
M  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  
N  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  
O  .  .  .  .  .  .  .  .  .  .  .  .  .  .  .  
Tom win!
```

## Game1024
Just like the original one, only the input of control changed to "W", "S", "A", "D".

When number of digits of the largest number changes, all spaces between the numbers will adjust accordingly for smooth viewing.
```$xslt
Score : 756
64  .   .   .   
64  2   .   2   
16  4   2   4   
4   2   1   2   
Please make your move:
W
Score : 884
128  2    2    2    
16   4    1    4    
4    2    .    2    
.    .    1    .  
```

## Reversi
A strategy board game for two players.

During a play, any disks of the opponent's color that are in a straight line and bounded by the piece just placed and another piece of the current player's color are turned over to the current player's color.

The object of the game is to have the majority of pieces turned to display your color when the last playable empty square is filled. 

```$xslt
Score: 2 : 2
   1  2  3  4  5  6  7  8  
A  .  .  .  .  .  .  .  .  
B  .  .  .  .  .  .  .  .  
C  .  .  .  .  .  .  .  .  
D  .  .  .  o  x  .  .  .  
E  .  .  .  x  o  .  .  .  
F  .  .  .  .  .  .  .  .  
G  .  .  .  .  .  .  .  .  
H  .  .  .  .  .  .  .  .  
It's Black's turn
C4
Score: 4 : 1
   1  2  3  4  5  6  7  8  
A  .  .  .  .  .  .  .  .  
B  .  .  .  .  .  .  .  .  
C  .  .  .  x  .  .  .  .  
D  .  .  .  x  x  .  .  .  
E  .  .  .  x  o  .  .  .  
F  .  .  .  .  .  .  .  .  
G  .  .  .  .  .  .  .  .  
H  .  .  .  .  .  .  .  . 
It's White's turn
```
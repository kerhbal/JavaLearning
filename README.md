# JavaLearning

This repository contains several projects I created during the study of Java.

## ChessGame & ChessGameObjectOriented
A chess game of two players. ChessGameObjectOriented is the OO version.

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
Tom wins!
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

## MineSweeper
A typical mine sweeper game with three method to interact with. 

The first one `O A1` means open block `A1`, and if there is a mine, game over; If there is no mine in the target block, a number will appear to show how many mines around; And if there is no mine in and around the target block, a field of empty space will spread until there is number shows mine counts around.

The second one `F A1` means flagged block `A1`. Flagged block cannot be opened until removal of the flag, which uses the same command `F`.

The third one is `R A1` means open the remaining blocks around `A1`. This happens when both conditions apply:

1. Target was opened before and shows the number of surrounding mines, also there are flags around the target.
2. The count of flags and the number shows by the target is equal.

Once `R A1` is received and above conditions are true, all blocks left around the target except flags will be opened instantly, and if any flag was marked at wrong place, game over. 

```$xslt
10 mines left
    1  2  3  4  5  6  7  8  
A   .  .  .  .  .  .  .  .  
B   .  .  .  .  .  .  .  .  
C   .  .  .  .  .  .  .  .  
D   .  .  .  .  .  .  .  .  
E   .  .  .  .  .  .  .  .  
F   .  .  .  .  .  .  .  .  
G   .  .  .  .  .  .  .  .  
H   .  .  .  .  .  .  .  .  
O A1
10 mines left
    1  2  3  4  5  6  7  8  
A                           
B      1  1  1     1  1  1  
C   1  2  .  1     1  .  .  
D   .  .  .  2     2  .  .  
E   .  .  .  2  1  2  .  .  
F   .  .  .  .  .  .  .  .  
G   .  .  .  .  .  .  .  .  
H   .  .  .  .  .  .  .  .  
O D2
10 mines left
    1  2  3  4  5  6  7  8  
A                           
B      1  1  1     1  1  1  
C   1  2  *  1     1  *  .  
D   .  *  .  2     2  .  .  
E   *  .  *  2  1  2  *  *  
F   .  .  .  .  *  .  .  .  
G   .  .  .  .  .  .  .  .  
H   *  .  .  .  .  .  .  *  
BOOM!
```
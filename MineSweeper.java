import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    public static void main(String[] args) {
        new MineSweeper().run();
    }

    int mineSize = 10;
    int size = 8;
    int[][] mineField = new int[size][size];
    int[][] frontBoard = new int[size][size];
    int flagCount = 0;
    boolean lose;
    public void run() {
        generateMineField();
        game();
    }

    public void generateMineField() {
        //represents the locations of mines
        int[] mine = new int[mineSize];
       // int mineCount = 0;//how many mine locations are created successfully

        for (int mineCount = 0; mineCount < mineSize; mineCount++) {
            //random[0, 64), if not found in mine[], then put it in mine[]
            //these random numbers represents the locations of mines
            int random;
            boolean unique = true;
            //each loop check if the random number already exists in the mine[]
            do {
                random = new Random().nextInt(size * size);
            } while(!unique(mine, mineCount, random));

            mine[mineCount] = random;
        }
        
        //put those mines in the mineField
        for (int i = 0; i < mineSize; i++) {
            int row = mine[i] / size;
            int column = mine[i] % size;
            mineField[row][column] = 1;
        }
        //debug print
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                System.out.print(String.format("%-3d", mineField[y][x]));
            }
            System.out.println();

        }
    }

    public boolean unique(int[] mine, int mineCount, int checkValue) {
        for (int i = 0; i <= mineCount; i++) {
            if (mine[i] == checkValue) {
                return false;
            }
        }
        return true;
    }

    public void game() {
        while(true) {
            printFrontBoard();
            int[] target = getTarget();
            int method = target[0];
            int targetY = target[1];
            int targetX = target[2];
            int[] checkMineResult = checkMine(targetY, targetX);
            int surroundingFlag = checkSurroundingFlag(targetY, targetX);
            //Console.debugPrintln(checkMineResult);
            if (method == 1) {//method is open
                open(checkMineResult, targetY, targetX);
            } else if (method == 2) {//method is flag
                if (frontBoard[targetY][targetX] == 0) {//if this block is never touched
                    frontBoard[targetY][targetX] = 10;//flag this block
                    flagCount++;
                } else if (frontBoard[targetY][targetX] == 10 || frontBoard[targetY][targetX] == 11) {//if this block is already flagged
                    frontBoard[targetY][targetX] = 0;//remove the flag
                    flagCount--;
                } else {
                    System.out.println("Cannot flag it, please try another one!");
                }

            } else {//method is open remaining
                //open remaining happens when target is a number(of surrounding mines)
                //and among 8 surrounding blocks there are flags that equal to the number shows on target block
                //and when chose to open remaining, all surrounding blocks left will be opened, if flags were wrong
                //placed, boom!
                openRemaining(surroundingFlag, targetY, targetX);
            }
            if (lose) {
                //make mines visible
                for (int y = 0; y < size; y++) {
                    for (int x = 0; x < size; x++) {
                        if (mineField[y][x] == 1) {
                            frontBoard[y][x] = 9;//'*'
                        }
                    }
                }
                printFrontBoard();
                System.out.println("BOOM!");
                break;
            }

            //break;
        }
    }

    public void printFrontBoard() {
        System.out.println((mineSize - flagCount) + " mines left");
        System.out.print("    ");
        for (int x = 0; x < size; x++) {
            System.out.print(String.format("%-3s", x + 1));
        }
        System.out.println();

        for (int y = 0; y < size; y++) {
            System.out.print(String.format("%-4s", (char)('A' + y)));
            for (int x = 0; x < size; x++) {
                //when frontBoard[][] is 0, show '.', when it's 9, show '*',
                // when it's 10 or 11(means checked by recursion), show flag 'F', when it's 12, show ' '
                char[] printSymbols = {'.', '1', '2', '3', '4', '5', '6', '7', '8', '*', 'F', 'F', ' '};
                System.out.print(String.format("%-3s", printSymbols[frontBoard[y][x]]));
            }
            System.out.println();
        }
    }

    public int[] getTarget() {
        // first digit is N/A (0) or open(1) or flag(2) or open remaining(3) , second and third digits are location
        int[] target = new int[3];
        while (true) {
            Scanner input = new Scanner(System.in);
            String targetInput = input.next();
            if (targetInput.length() >= 4) {
                if (targetInput.charAt(0) == 'O') {
                    target[0] = 1;
                } else if (targetInput.charAt(0) == 'F') {
                    target[0] = 2;
                } else if (targetInput.charAt(0) == 'R') {
                    target[0] = 3;
                } else {
                    System.out.println("Wrong format! The first digit should be O, F or R！");
                    continue;
                }

                if (targetInput.charAt(1) != ' ') {
                    System.out.println("Wrong format! The second digit should be space");
                    continue;
                }

                target[1] = targetInput.charAt(2) - 'A';
                target[2] = Integer.parseInt(targetInput.substring(3)) - 1;
                //Console.debugPrintln(target);
                break;
            } else {
                System.out.println("Wrong format, please try again！");
            }
        }
        return target;
    }

    public int[] checkMine(int targetY, int targetX) {
        //mineField:
        //first digit means if there is mine at target position, 1 means there is mine
        //second digit means how many mines around target position
        //(deleted, not used)frontBoard:
        //third digit means if target position is in original state
        //forth digit means if target position is flagged
        //fifth digit means how many flags around target position
        int[] checkMineResult = new int[2];
        //check the target position and around
        for (int i = -1; i < 2; i++) { // -1, 0, 1
            for (int j = -1; j < 2; j++) {
                //check if there is mine in field at target position and around
                if (targetY + i >= 0 && targetX + j >= 0 && targetY + i < size && targetX + j < size && mineField[targetY + i][targetX + j] == 1) {
                    if (i == 0 && j == 0) {//target position
                        checkMineResult[0] = 1;
                    } else {//around target position
                        checkMineResult[1]++;
                    }
                }

//                //check if target position is in original state in the frontBoard
//                if (frontBoard[targetY][targetX] == 0) {
//                    checkResult[2] = 1;
//                }
//                //check if there is flag at and around the target in the frontBoard
//                if (targetY + i >= 0 && targetX + j >= 0 && targetY + i < size && targetX + j < size && frontBoard[targetY + i][targetX + j] == 10) {
//                    if (i == 0 && j == 0) {//target position
//                        checkResult[3] = 1;
//                    } else {//around target position
//                        checkResult[4]++;
//                    }
//                }
            }
        }
        return checkMineResult;
    }

    public int checkSurroundingFlag(int targetY, int targetX) {
        int surroundingFlag = 0;
        for (int i = -1; i < 2; i++) { // -1, 0, 1
            for (int j = -1; j < 2; j++) {
                //check if there is flag around the target in the frontBoard
                if (targetY + i >= 0 && targetX + j >= 0 && targetY + i < size && targetX + j < size && frontBoard[targetY + i][targetX + j] == 10) {
                    if (i == 0 && j == 0) {//target position
                        //do nothing
                    } else {//around target position
                        surroundingFlag++;
                    }
                }
            }
        }
        return surroundingFlag;
    }

    public void open(int[] checkMineResult, int targetY, int targetX) {
        //only original blocks can be opened
        if (frontBoard[targetY][targetX] == 0) {
            //if found mine, game over
            if (checkMineResult[0] == 1) {
                frontBoard[targetY][targetX] = 9; // '*'
                lose =  true;
            } else {
                openSpace(targetY, targetX);
            }
        }
    }
    //11 means flag with recursion done, 12 means recursion done
    final int markFlagDone = 11;
    final int markDone = 12;
    public void openSpace(int targetY, int targetX) {
        int[] checkMineResultRecursion = checkMine(targetY, targetX);
        if (frontBoard[targetY][targetX] == markFlagDone || frontBoard[targetY][targetX] == markDone) {
            //if the recursion is happened and the target is already checked before, do nothing to avoid endless loop
            return;
        }

        if (frontBoard[targetY][targetX] != 0) {//if target has been touched
            return;
        }
        if (checkMineResultRecursion[1] > 0) {
            //if there is any mine surrounding the target
            frontBoard[targetY][targetX] = checkMineResultRecursion[1];//show number of surrounding mines
        } else {//if there is no mine surrounding the target, spread the space
            if (frontBoard[targetY][targetX] != 10) {// do not change flag.
                frontBoard[targetY][targetX] = markDone; //means show ' '
            } else {//if target is flag, need to mark this one is already checked to avoid endless loop, but not to change it
                //so we need a special mark
                frontBoard[targetY][targetX] = markFlagDone;
            }
            //use recursion to find out every space in the area until showing numbers
            for (int i = -1; i < 2; i++) { //-1, 0, 1
                for (int j = -1; j < 2; j++) {
                    if (targetY + i >= 0 && targetX + j >= 0 && targetY + i < size && targetX + j < size) {
                        openSpace(targetY + i, targetX + j);
                    }
                }
            }
        }
    }

    public void openRemaining(int surroundingFlag, int targetY, int targetX) {
        if (frontBoard[targetY][targetX] > 0 && frontBoard[targetY][targetX] < 9) {//need to be numbers
            if (frontBoard[targetY][targetX] == surroundingFlag) {//need to be same as surrounding flags
                //open the remaining flags
                for (int i = -1; i < 2; i++) { // -1, 0, 1
                    for (int j = -1; j < 2; j++) {
                        if (targetY + i >= 0 && targetX + j >= 0 && targetY + i < size && targetX + j < size && !(i == 0 & j == 0)) {
                            //if the surrounding is not flag(10 or 11), open it
                            if (frontBoard[targetY + i][targetX + j] != 10 && frontBoard[targetY + i][targetX + j] != 11) {
                                open(checkMine(targetY + i, targetX + j), targetY + i, targetX + j);
                            }
                        }
                    }
                }
            }
        }
    }
}

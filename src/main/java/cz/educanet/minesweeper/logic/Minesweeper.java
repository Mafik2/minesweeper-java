package cz.educanet.minesweeper.logic;
import java.util.Random;
public class Minesweeper {

    private int rowsCount;
    private int columnsCount;
    private boolean bomb[][];
    private int field[][];

    public Minesweeper(int rows, int columns) {
        this.rowsCount = rows;
        this.columnsCount = columns;
        field = new int[columns][rows];
        bomb = new boolean[columns][rows];
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                field[j][i] = 0;
                if (random.nextInt(100) % 5 == 0){
                    bomb[j][i] = true;
                }
                else {
                    bomb[j][i] = false;
                }
            }
        }
    }

    /**
     * 0 - Hidden
     * 1 - Visible
     * 2 - Flag
     * 3 - Question mark
     *
     * @param x X
     * @param y Y
     * @return field type
     */
    public int getField(int x, int y) {
        return field[x][y];
    }

    /**
     * Toggles the field state, ie.
     * 0 -> 1,
     * 1 -> 2,
     * 2 -> 3 and
     * 3 -> 0
     *
     * @param x X
     * @param y Y
     */
    public void toggleFieldState(int x, int y) {
        if(field[x][y] == 0)  field[x][y] = 2;
         else {field[x][y] = field[x][y] + 1;

            if (field[x][y] == 4) {
                field[x][y] = 0;
            }
        }
        System.out.println("Toggle Reveal");
    }

    /**
     * Reveals the field and all fields adjacent (with 0 adjacent bombs) and all fields adjacent to the adjacent fields... ect.
     *
     * @param x X
     * @param y Y
     */
    public void reveal(int x, int y) {
        if (getField(x, y) != 1){
            field[x][y] = 1;
            for (int i = - 1; i < 2; i++) {
                for (int j = - 1; j < 2; j++) {
                    if (x + i >= 0 && x + i < columnsCount && y + j >= 0 && y + j < rowsCount && !(i == 0 && j == 0)){
                        if (getAdjacentBombCount(x, y) == 0){
                            reveal(x + i, y + j);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the amount of adjacent bombs
     *
     * @param x X
     * @param y Y
     * @return number of adjacent bombs
     */
    public int getAdjacentBombCount(int x, int y) {
        int adjacentBombCount = 0;
        for (int i = - 1; i < 2; i++) {
            for (int j = - 1; j < 2; j++) {
                if (x + i > 0 && x + i < columnsCount && y + j > 0 && y + j < rowsCount && !(i == 0 && j == 0)){
                    if (isBombOnPosition(x + i, y + j)){
                        adjacentBombCount++;
                    }
                }
            }
        }
        return adjacentBombCount;
    }


    /**
     * Checks if there is a bomb on the current position
     *
     * @param x X
     * @param y Y
     * @return true if bomb on position
     */
    public boolean isBombOnPosition(int x, int y) {
        return bomb[x][y];
    }

    /**
     * Returns the amount of bombs on the field
     *
     * @return bomb count
     */
    public int getBombCount() {
        int flags = 0;
        for (int i = 0; i < rowsCount; i++) {
            for (int j = 0; j < columnsCount; j++) {
                if (getField(j, i) == 2){
                    flags++;
                }
            }
        }
        return flags;
    }
    /**
     * total bombs - number of flags
     *
     * @return remaining bomb count
     */
    public int getRemainingBombCount() {
        int flags = 0;
        for (int i = 0; i < columnsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                if (field[i][j] == 2) {
                    flags++;
                }
            }

        }
        return flags - getBombCount();
    }

    /**
     * returns true if every flag is on a bomb, else false
     *
     * @return if player won
     */
    public boolean didWin() {
        for (int i = 0; i < columnsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                if (field[i][j] != 2 && bomb[i][j]) {
                    return false;
                }
                if (field[i][j] == 2 && !bomb[i][j]) {
                    return false;
                }
            }

        }
        return true;
    }


    /**
     * returns true if player revealed a bomb, else false
     *
     * @return if player lost
     */
    public boolean didLoose() {
        for (int i = 0; i < columnsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                if (field[i][j] == 1 && bomb[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getRows() {
        return rowsCount;
    }

    public int getColumns() {
        return columnsCount;
    }

}

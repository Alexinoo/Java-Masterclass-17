package concurrency.part27_livelock_example;

import java.util.Arrays;

public class Maze {
    /*
     * Create Maze Class
     *
     *  Constant - maze size
     *     static final - MAZE_SIZE - 4
     *
     *  Grid Cells - 2 dimensional array 4 * 4
     *     cells : String[][]
     *
     *  Constructor - no args
     *      - Initialize all the values in the grid to an empty string
     *      - Loop through each row in the 2d array and fill each cell in that arr with an empty string
     *      - Use Arrays.fill
     *  Methods
     *   int[] getNextLocation(int[] lastSpot) :
     *      - get the next location for someone in the maze
     *      - get the next cell in the grid traversing over each cell and row, one at a time
     *      - takes cell location (row,col) coordinate pair as param
     *      - returns the next location as an int[] for the new coords
     *
     *      - Initialize the next spot to a new array of int
     *          - If it's the last cell, then the player will be moved to the first cell
     *          - row coordinate remains the same, unless the cell was reset back to zero
     *              - if the row coordinate is 0 and the last spot was the last cell, then the position goes back to 0
     *          - return the next spot
     *
     *  void moveLocation(int locX, int locY, String name) - moves person to another location
     *      - Takes the location, and the name
     *      - set the cell to the first initial of the person who is moving in the maze
     *          e.g. A for Adam and G for Grace
     *      - When the person moves in the maze, any work done by the partner searching for that person would need to be reset
     *          - call resetSearchedCells() an pass the name
     *
     *  void resetSearchedCells(name) -
     *      - Takes a name
     *      - loop through the rows
     *          - for all cells in the row, replace any rows that have been searched back to unsearched or an empty string
     *          - when a cell has been searched, it will have ! or exclamation mark and the initial of name
     *              - so any cells with these values will get reset
     *
     *  boolean : searchCell(partner , nextSpot , lastSpot  )
     *      - returns a boolean if the person being searched for is found in the next cell
     *      - takes the partner/person being searched for , next spot & the last spot
     *      - check the value in the cell of the next spot, the spot this person would move to
     *          - if it has the initial of the partner, then we know we have found the partner
     *              - return true
     *      - Otherwise, set the last spot to be the not symbol plus the partner's initial
     *              - return false
     *
     *  toString : Override toString()
     *      - return the cells in the maze
     *          - call Arrays.deepToString() and pass cells 2-d array
     *
     */

    private final static int MAZE_SIZE = 4;

    private final String[][] cells = new String[MAZE_SIZE][MAZE_SIZE];

    public Maze() {
        for (var row : cells)
            Arrays.fill(row,"");
    }

    public int[] getNextLocation(int[] lastSpot){
        int[] nextSpot = new int[2];
        nextSpot[1] = (lastSpot[1] == Maze.MAZE_SIZE - 1) ? 0 : lastSpot[1] + 1;
        nextSpot[0] = lastSpot[0];

        if( nextSpot[1] == 0 )
            nextSpot[0] = (lastSpot[0] == Maze.MAZE_SIZE - 1) ? 0 : lastSpot[0] + 1;

        return nextSpot;
    }

    public void moveLocation(int locX,int locY, String name){
        cells[locX][locY] = name.substring(0,1);
        resetSearchedCells(name);
    }

    public void resetSearchedCells(String name){
        for (var row : cells){
            Arrays.asList(row).replaceAll(cell -> cell.equals("!" +name.charAt(0)) ? "" : cell);
        }
    }

    public boolean searchCell(String partner , int[] nextSpot , int[] lastSpot){
        if (cells[nextSpot[0]][nextSpot[1]].equals(partner.substring(0,1)))
            return true;

        cells[lastSpot[0]][lastSpot[1]] = "!" + partner.charAt(0);
        return false;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(cells);
    }
}

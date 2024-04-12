package concurrency.part27_livelock_example;

public class Main {

    /*
     * Livelock
     * ..........
     *
     * A livelock is when 2 or more threads are continuously reacting, each responding to the other's action.
     *   And can never successfully complete
     *
     * Livelock Examples
     * .................
     * You can find different examples of this problem on the internet
     *
     * Let's say you and your problem have gone into an escape room, where you have to solve problems to get out
     * In this case though, it's going to contain a maze/puzzle you have to navigate through
     * You and your partner will start at different positions, and have to find each other and aren't allowed to communicate with each other
     * Should you both look for each other?
     * Definitely not, but the rules say each person must move within 3 sec
     *
     * Simple 'Maze'
     * .............
     * Use a simple maze to demo how me and my friend , in the escape room's maze might be caught in a live lock scenario
     *
     *  - Create a 4 * 4 maze and the paths are shown by arrows
     *  - Have 2 people searching for each other, Grace and Adam
     *      - Grace starts at position  (1,1)
     *      - Adam starts at position  (3,3)
     *  - Adam
     *      - if he moves to the next position, he will go to 0,0
     *
     * For the purposes of this code, the players must only move forward
     *
     *
     * Implementation
     * ..............
     *
     * Create Maze Class
     *
     *
     * Create MazeRunner Class
     *
     *
     *
     *
     *
     *
     */

    public static void main(String[] args) {
        //Maze maze = new Maze();

    }
}

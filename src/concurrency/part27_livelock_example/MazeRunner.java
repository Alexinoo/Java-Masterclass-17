package concurrency.part27_livelock_example;

import java.util.Arrays;
import java.util.concurrent.Executors;
    /*
     *  Create a Participant Record
     *      Fields
     *          - name : String
     *          - searchingFor : String
     *          - maze : Maze
     *          - startingPosition : int[]
     *      Compact Constructor
     *          - set the participation's location on the maze
     */

    record Participant(String name, String searchingFor , Maze maze , int[] startingPosition){

        Participant{
            maze.moveLocation(startingPosition[0] ,startingPosition[1],name );
        }
    }

    /*
     * We need data from this participant to execute the search
     * Create ParticipantThread Class that extends the Thread class
     *  Fields:
     *      - participant : Participant
     *
     *  Constructor
     *      - initialize the values
     *      - chain a call to super constructor since we want the thread to have the same name
     *
     *  Override run()
     *      - set a variable last spot to the participant starting position
     *      - assign maze to a variable as well, just to make the code easier to read
     *      - set up a while loop that runs until a successful search has been done
     *          - get the next loc based on last spot of the participant
     *
     *          - pause for half a second, before the next search
     *          - call searchCell() from Maze class and pass
     *              - name of the person the thread should be looking for
     *              - newSpot
     *              - lastSpot
     *          - if true
     *              - print out info about a successful search
     *                  - person doing the search
     *                  - person they are looking for
     *                  - position they were found
     *              - stop the thread from running by calling break to exit the while loop
     *
     *          - Add a synchronized block, on the shared maze because moveLocation is going to change the maze values
     *              - If no match was made, then the player will move to the next location which we'll get by calling
     *                  moveLocation and passing newSpot and the participant name
     *              - Then save off / update the lastSpot
     *
     *          - Add a catch block but this time re-assert the interrupt
     *              - Without this, the thread may not be properly interrupted
     *              - then return or exit
     *
     *          - print the "participant" is searching the "maze"
     */

    class ParticipantThread extends Thread {

        private final Participant participant;

        public ParticipantThread(Participant participant) {
            super(participant.name());
            this.participant = participant;
        }

        @Override
        public void run(){
            int[] lastSpot = participant.startingPosition();
            Maze maze = participant.maze();
            while (true){
                int[] newSpot = maze.getNextLocation(lastSpot);

                try{
                    Thread.sleep(participant.name().equals("Grace") ? 2900 : 500);
                    if(maze.searchCell(participant.searchingFor(), newSpot , lastSpot)){
                        System.out.printf("%s found %s at %s!%n",
                                participant.name(),participant.searchingFor(), Arrays.toString(newSpot));
                        break;
                    }
                    synchronized (maze){
                        maze.moveLocation(newSpot[0],newSpot[1], participant.name() );
                    }
                    lastSpot = new int[]{newSpot[0],newSpot[1]};

                }catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                    return;
                }
                
                System.out.println(participant.name() + " searching " +participant.maze());

            }
        }
    }

    /*
     * Instantiate a new maze
     * Create a new Participant and pass the following to the constructor
     *  - new participant named Adam
     *  - his partner, or the person he'll be searching for is Grace
     *  - then maze instance
     *  - starting position for Adam
     *
     * Create another Participant and pass the following to the constructor
     *  - new participant named Grace
     *  - his partner, or the person he'll be searching for is Adam
     *  - then maze instance
     *  - starting position for Grace
     *
     * Print out maze first we see how it looks at the start
     *
     * Running this:
     *  - Prints the entire grid as a 2-d array on a single line of output
     *      - Grace is in the 2nd row and in the 2nd cell which is 1,1
     *      - Adam is in the last cell of the grid which is 3,3
     *
     * To understand what this code is doing, run each thread on it's own first
     * Use ExecutorService to manage these threads
     *  - use CachedThreadPool from Executors Class
     *  - check the status of the running threads and use submit() on executor instance
     *      - This () returns a future, which we assign to variable "adamsResults"
     *      - Pass to this () a new participant thread created with the adam participant
     *      - Then call shutdown on the executor
     *
     * Running this:-
     *  - Thread works by printing Adam is Searching
     *      - Adam started at the last cell in the grid and obviously Grace wasn't there
     *          - this was marked as !G
     *      - Adam then moved to the first cell in the grid
     *      - Each time Adam moved, the cell he was in is marked !G - which as Grace was not there
     *      - This continues until he gets to the cell where Grace is
     *      - At that point, the thread prints that "Adam found Grace at position 1,1 a
     *      - And the thread stops running
     *
     * Comment out on adamResults executor
     * Execute the next thread , with Grace as the participant
     *
     * Running this:
     *  - The thread has Grace searching and we can see Grace moving through the grid
     *      - Each cell she was previously marked as ! - meaning Adam wasn't there
     *      - She eventually manages to find Adam at position 3,3
     *
     * This code works great, when the person being searched for never moves
     * But what happens if they do ?
     *  - If Grace is looking for Adam, and he moves to another cell, it's a valid statement to say that all her
     *      previous work is useless
     *  - She cannot say with any certainty after he moves, that the cells she looked in previously, are Adam free
     *  - That's why the maze reset will reset all cells that have !A in them , if Adam moves
     *  - It will do the same if Grace moves, invalidating or removing, the !G setting in this maze
     *
     * Let's run our 2 participants asynchronously
     *  - uncomment the thread that kicks off Adam searching
     *
     * Before running this, set up a situation that if 1 thread completes, it will shut down the other
     * Set a while loop that will check both adamsResults and gracesResults
     *  - if both return false from isDone() that means they're still running
     *      - sleep for 1 sec
     *  - If adam is done, cancel the graces thread
     *      - call cancel() on gracesResult and pass true to this ()
     *  - If grace is done, cancel the adam thread
     *      - call cancel() on adamsResult and pass true to this ()
     *
     * Running this:
     *  - Both Adam and Grace are now both searching for each other and are both on the move
     *  - The threads are active and each thread is changing the state of the maze,
     *      - This means each thread keeps searching and will keep searching indefinitely,
     *          - This is because each is moving linearly, always away from each other and at the same pace
     *  - We've created a live lock
     *      - Threads are not blocked but continually working , responding to the maze's state that was changed
     *         by another thread , in a loop that may never end
     *
     * Livelocks can be difficult to debug and fix
     * ............................................
     * There are few general things to avoid them
     *  - Avoid having threads that are constantly checking for each other's state
     *      - Design your code so that threat can make progress independently of each other
     *  - Use timeouts to prevent threads from waiting indefinitely for each other
     *  - Use randomization to break the symmetry between threads
     *      - e.g. If 2 threads are trying to acquire the same resource, you can have them randomly decide which
     *        thread should acquire the resource first
     *
     * As demonstrated earlier, the problem with this code isn't due to the synchronized block or locks
     *
     * Solution:
     *  - Execute the search but randomize the search time a little bit
     *  - Our partners are sort of in lock step, both moving forward at the exact same rate
     *      - Let's have each thread wait a diff amount of time
     *
     *  - run()
     *      - sleep for 2900ms if the participant is Grace , otherwise 500ms
     *
     * Running this :
     *  - Since Grace is waiting almost all the way up to the 3 sec max, and Adam is moving every half a second,
     *     he's able to catch/find her
     *
     * From this example, we deduce that 1 thread's actions, can affect another thread in such a way that completing
     *  the tasks is nearly impossible
     *
     * That's a livelock in a nutshell
     */

public class MazeRunner {
    public static void main(String[] args) {

        Maze maze = new Maze();
        Participant adam = new Participant("Adam","Grace",maze, new int[]{3,3});
        Participant grace = new Participant("Grace","Adam",maze, new int[]{1,1});

        System.out.println(maze);

        var executor = Executors.newCachedThreadPool();
        var adamsResults = executor.submit(new ParticipantThread(adam));
        var graceResults = executor.submit(new ParticipantThread(grace));

        while (!adamsResults.isDone() && !graceResults.isDone()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (adamsResults.isDone())
            graceResults.cancel(true);
        else if (graceResults.isDone())
            adamsResults.cancel(true);

        executor.shutdown();

    }
}

package concurrency.part27_livelock_example;

public class MazeRunner {
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
                System.out.println(participant.name() + " searching " +participant.maze());

            }
        }
    }
    public static void main(String[] args) {

    }
}

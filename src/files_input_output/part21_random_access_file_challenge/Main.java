package files_input_output.part21_random_access_file_challenge;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;

public class Main {

    /*
     * Use an indexed file, that contains a series of employee records
     *
     * Open a RAC class with appropriate permissions
     * Load the employee index into memory
     * List your employee Id's in order
     * Retrieve an Employee Record from file, using an employee id, to locate the position of that record in the file
     * Print the employee record info to the console
     * Update the selected employee salary in the file
     * Retrieve the record from the file again, and print the info to the console, confirming that the salary persisted
     * Each employee record in the file consists of the following info and in this order
     *  - Employee ID - int
     *  - Salary - double
     *  - First name - String - variable width
     *  - Last name - String - variable width
     *
     *
     * Create a static variable for the map index
     *  - keyed by an integer - employee id
     *  - value mapped to file position
     * Use static initializer on main class to initially load up the index when the class is loaded
     * Initialize recordsInFile variable
     * Open the RAC for reading "employees.dat" with read-only mode
     * Catch checked IOException and print stack trace
     * Read 4 bytes with readInt() usually 4 bytes and store it in recordsInFile
     *  - readInt() - integer that has the record count in it
     * Print how many records are in the file
     * Use a for loop to determine how many times you need to read the values
     *  - read the employee id as an int
     *  - read the file position as a long
     *  - And put this key value pair into the map
     *
     * Running this:-
     *  Prints "25 records in file"
     */
    private static final Map<Integer,Long> indexedIds = new HashMap<>();

    static {
        int recordsInFile = 0;
        try {
            RandomAccessFile rac = new RandomAccessFile("employees.dat","r");
            recordsInFile = rac.readInt();
            System.out.println(recordsInFile +" records in file");
            for (int i = 0; i < recordsInFile; i++) {
                indexedIds.put(rac.readInt(),rac.readLong());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        /*
         * Open RAC with read-write mode because we plan for read and write operations this time, and update
         *  salary field
         * Use a scanner to solicit the employee id from the console
         * Create a list of the employee ID's from the indexedIds map
         * Sort the list of ids
         *
         * Use this list to help prompt the user for a valid employee id
         * Handle IoException
         *
         * Start a while loop and loop until something's breaks out of this loop
         *  - display the sorted list of employee ids
         *  - Prompt for an Employee id
         *  - if there is no input, will break out from the loop
         *  - read the input, employee id and parsing it to an int
         *      - if id < 1 will exit the loop
         *  - For good measure, will make sure the id the user enters,is really in the file
         *      - use contains() to check and if it's not, we'll continue, i.e. going back to the start of the while loop
         *
         * Call the readRecord() and pass the employee id entered by the user
         * Then prompt user for a salary
         *  - use try catch to handle bad data entered for the salary
         *  - catch the exception and ignore it - so that the program doesn't crash
         *  - Then position the file pointer to the salary field which is after the id
         *      - We need to add 4 to the position that we got from the employee id map
         *          - write the new salary to the file
         *          - read & display the updated Employee Record
         *          - catch and ignore any NumberFormatException
         *
         *
         *
         * Running this :
         *      - Prints "25 records in file"
         *      - prompted to enter an employee id or 0 to quit (622)
         *          - The program creates and returns an employee details with 0.0 as salary
         *      - prompted to enter a salary (55555)
         *          - The employee record is retrieved and printed with the salary that we entered
         *
         * The employee's new salary is now in persistence storage in ".dat" file
         *
         */
        try(RandomAccessFile rac = new RandomAccessFile("employees.dat","rw")){

            Scanner scanner = new Scanner(System.in);
            List<Integer> ids = new ArrayList<>(indexedIds.keySet());
            Collections.sort(ids);

            while (true){
                System.out.println(ids);
                System.out.println("Enter an Employee Id or 0 to quit");
                if (!scanner.hasNext()) break;
                int employeeId = Integer.parseInt(scanner.nextLine());
                if (employeeId < 1) break;
                if (!ids.contains(employeeId))
                    continue;
                Employee employee = readRecord(rac , employeeId);
                System.out.println("Enter new salary, nothing if no change:");
                try{
                    double salary = Double.parseDouble(scanner.nextLine());
                    rac.seek(indexedIds.get(employeeId) + 4);
                    rac.writeDouble(salary);
                    readRecord(rac, employeeId);
                }catch (NumberFormatException ignore){
                    // If the entered input is not a valid no, I'll ignore it
                }
            }

        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    /* Create a static () readRecord that returns an Employee
     * Takes RAC instance as the 1st arg and employee id as the 2nd arg
     * Will use this () to get a record from the file, instantiating the Employee record, using the appropriate read
     *  ()s to populate the fields
     * Declare the throws clause on this () and let the calling code deals with it
     *
     * Will use employeeId from the Map, to get the file position for the employee passed in and use seek() to jump
     *  to the position in the file for this specific employee
     * Will read the employee info
     *  - employee id with readInt() - 4 bytes
     *  - employee salary with readDouble() - 4 bytes
     *  - employee first name which is a variable width string with readUTF()
     *  - employee last name which is also a variable width string with readUTF()
     *
     * Create an employee obj and pass the gathered data in the constructor
     *
     * Go to the main () and open RAC for read-write
     */
    private static Employee readRecord(RandomAccessFile rac , int employeeId) throws IOException{
        rac.seek(indexedIds.get(employeeId));
        int id = rac.readInt();
        double salary = rac.readDouble();
        String first = rac.readUTF();
        String last = rac.readUTF();

        Employee employee = new Employee(id,first,last,salary);
        System.out.println(employee);
        return employee;
    }
}

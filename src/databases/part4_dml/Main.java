package databases.part4_dml;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    /*
     * Statement.execute vs Statement.executeQuery
     *
     *
     *
     * main()
     *
     * Setting a connection , if you're using JDBC driver, that's earlier than JDBC 4.0
     * Prior to that version, a driver had to be loaded explicitly
     * To load a class explicitly or manually, you have to call Class.forName() and pass the fully qualified class name as a string
     *  - Class.forName() return a class instance, and can be used for other purposes , like reflection
     *  - we can call getClass on any obj to get a Class instance
     *  - in this case, we're getting a class instance based on a fully qualified class name in String literal for a class we haven't included in
     *    any of our import statements
     *  - wrap this in a try-catch
     *
     * The Class.forName() was necessary in older JDBC, and allowed the database driver to be registered dynamically
     * This gave programmers the flexibility to swap in different drivers at runtime, or in diff environments
     * This mechanism has since be replaced , with the Service Provider Interface
     *
     * Even though its no longer necessary to use Class.forName(), let's see what happens if we do. Legacy code or older applications may still use this
     *  approach and we should at least recognize it
     *
     * Once the driver is registered , the process is the same to get a connection
     *  - Set up a try-with-resources and set up a new connection using DriverManager
     *      - call getConnection() on DriverManager and pass
     *          - the connection String for MySql db
     *          - pass the username stored in an environment variable
     *          - pass the password stored in an environment variable
     *          (set the above in the run configuration)
     *      - set up Statement obj
     *  - Catch SQLException
     *
     * Within the try clause -
     *  - Query from artists table with the local string
     *
     * Then call the execute() on statement instance
     *  - execute(query) takes the query and returns a boolean
     *  - print the boolean result
     *
     * The execute() can be used for different kinds of statements, like insert , update, and delete in addition to the select statement
     *
     */

    private static final String CONN_URL = "jdbc:mysql://localhost:3306/music";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try(Connection connection = DriverManager.getConnection( CONN_URL,System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASS"));
            Statement statement = connection.createStatement();){

            String artist = "Elf";
            String query = "SELECT * FROM artists WHERE artist_name = '%s'".formatted(artist);
            boolean result = statement.execute(query);

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}

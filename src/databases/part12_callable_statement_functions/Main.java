package databases.part12_callable_statement_functions;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    /*
     * Stored Procedures
     * .................
     * Stored procedures are designed to execute a sequence of SQL statements , and can perform multiple operations
     *  within a single call
     * They can modify the state of the database, i.e. they can create, update or delete data
     * Procedures can also control transaction management, ensuring data consistency and integrity
     *
     * Stored Functions
     * ...............
     * In RDBMS like MySQL, precompiled collections of SQL statements, can either be a stored procedure or a stored
     *  function
     * Both are stored in the db and both can be executed as a single unit
     * While each may encapsulate common database operations, they are different
     * Specifically, stored functions are designed to perform a specific calculation, or data manipulation, and return
     *  a single value
     *
     * Characteristics of Stored Functions
     * ...................................
     * The key characteristics of a stored functions are
     *  - Returns a value
     *      - Stored functions are expected to return a single value,such as an integer, string, date or a result set
     *  - Is Immutable
     *      - SF are generally designed to be deterministic, should not have side effects
     *      - means calling a function with the same inputs, should always produce the same output and should not modify data in the db
     *  - Can be used in SQL Expressions
     *      - You can use it directly in SELECT statements, WHERE clauses , or JOIN conditions, to compute values used in
     *        queries
     *
     * Using a CallableStatement to execute stored functions
     * .....................................................
     * You might be less likely to call a stored function from your CallableStatement , than you would a SP
     * There are a couple of differences
     *  - SQL string will always start with a placeholder, a question mark for the returned result
     *  - MySQL stored functions only support IN parameters
     *      - This is not true for other vendors
     *      - This means you don't specify the parameter types in the parameters declarations of the stored function
     *
     *
     * /////////////
     * Copy the code from Callable_statement_in_out file
     *
     * Call the function after printRecords()
     *  - Whether you're calling a SP or SF, you still call prepareCall() on connection instance
     *      - Pass the string that calls calcAlbumLength()
     *          - start with a ? =
     *          - followed by a keyword CALL
     *          - then the stored function music.calcAlbumLength(?)
     *              - pass a ? as it takes 1 parameter
     *      - The first question mark is for the result of the function
     *      - Only need to register the result only once and this is done just like an output parameter
     *          - The returned result is in index 1
     *      - Use Java.sql.Types class to specify the result as double
     *
     *      - Loop through the data in the Map
     *          - through the key , the artist and the nested map which has all the new album data
     *          - loop through the album map but just the album name keys
     *          - include a try clause
     *              - set the function's parameter index 2 , to the album name
     *              - this means we'll be calling the calcAlbumLength for each of the Bob Dylan albums in the map
     *              - next call execute on csf statement
     *              - get the result by calling getDouble() and passing index 1
     *                  - print this data out
     *          - usual catch clause
     *
     *      - Running this:
     *          - We get an error Parameter 1 is an OUT parameter
     *              - this error is confusing if you don't understand the problem
     *
     *      - Solution
     *          - go up to the prepareCall() and make a minor change
     *          - start and end the text that's in quotes with an opening and closing curly brace
     *
     *      - Running the code again:
     *          - The code ended without any errors and we get the album lengths back from executing the function this way
     *              - The length of Bob Dylan is 32.5
     *              - The length of Blonde on Blonde is 35.0
     *
     * What are the curly braces here and why did this work?
     *  - Without the curly braces, the JDBC driver interprets the string literally, so it wants to execute a CALL
     *    command, on the db
     *  - But in most databases, you don't execute SF using the call keyword, like you would for a SP
     *  - Because it thinks you're calling a SP, the starting question mark is simply ignored
     *
     * That's why we got the error in the first try, the Parameter 1 is an OUT parameter because the driver didn't factor
     *  in the first ?
     * Using the {} informs the JDBC driver that you want to perform extra processing or translation
     * These {} are called an escape sequence, and they're supported in special cases
     *
     * JDBC Escape Sequences
     * .....................
     * JDBC Escape Sequences provide a way to execute db specific operations, in a more consistent and portable manner
     *  across different db systems
     * They're enclosed in {} and are used in SQL statements
     * There are certain things that are not db agnostic
     *  - Date, time and timestamp literals
     *  - Scalar functions such as numeric, string and data type conversion
     *      - e.g. the function to transform a string to upper case may have a diff name in various db
     *  - Escape char(s) for wildcards used in LIKE clauses may be diff btwn db
     *  - Execution of SP and SF may be performed with either a call, or execute () or some other keyword
     *      - may be performed diff across diff db
     *
     * For these features and others , you can use an escape sequence usually with special syntax specified
     * In the case of SF, the key is ? = CALL, part of the string
     * This informs the JDBC driver, that you want to prepare the code, to execute a function
     *
     * You might be wondering why we didn't need the escape sequences for the stored procedures
     *  - In general, the keyword CALL is supported by most db for SP
     * This means we could go to MySQL Workbench and execute CALL command, with the procedure name and specified params
     *  , in a SQL Query editor
     * We can do the same in most RDBMS's, so the use of the escape sequences in a SP call is often optional
     * The execute of a function call isn't done with the command CALL, in MySQL as we saw, we needed to escape the string
     *  which allowed it to run successfully
     *
     * Summary
     * .......
     * SP are designed for executing multiple operations, modifying data and enforcing business logic
     * In contrast, SF are primarily used for calculations and data transformations
     * Both enhance code re-usability, improve performance, and promote encapsulation of complex db operations
     * And even better, they simplify the JDBC code you have to write
     *
     *
     *
     */

    private static final int ARTIST_COLUMN = 0;
    private static final int ALBUM_COLUMN = 1;
    private static final int SONG_COLUMN = 3;
    public static void main(String[] args) {

        Map<String , Map<String,String>> albums = null;

        try(var lines  = Files.lines(Path.of("NewAlbums.csv"))){
            albums = lines.map(s -> s.split(","))
                    .collect(Collectors.groupingBy(s->s[ARTIST_COLUMN],
                            Collectors.groupingBy(s->s[ALBUM_COLUMN],
                                    Collectors.mapping(s -> s[SONG_COLUMN] ,
                                            Collectors.joining("\",\""
                                                    ,"[\"",
                                                    "\"]"
                                            )))));

        }catch (IOException e){
            throw new RuntimeException(e);
        }

        albums.forEach((artist , artistAlbums)->{
            artistAlbums.forEach((key,value)->{
                System.out.println(key + " : "+ value);
            });
        });

        var dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("music");

        try(Connection connection = dataSource.getConnection(
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASS"))){


            String sql = "SELECT * FROM music.albumview WHERE artist_name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "Bob Dylan");
            ResultSet rs = ps.executeQuery();

            printRecords(rs);

            CallableStatement csf = connection.prepareCall("{? = CALL music.calcAlbumLength(?)}");
            csf.registerOutParameter(1,Types.DOUBLE);

            albums.forEach((artist , albumMap)->{
                albumMap.keySet().forEach(albumName -> {
                    try{
                         csf.setString(2,albumName);
                         csf.execute();
                         double result = csf.getDouble(1);
                        System.out.printf("Length of %s is %.1f%n",albumName,result);
                    }catch (SQLException e){
                        throw new RuntimeException(e);
                    }
                });
            });

        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    private static boolean printRecords(ResultSet resultSet) throws SQLException{
        boolean foundData = false;
        var meta = resultSet.getMetaData();
        System.out.println("=".repeat(50));
        for (int i = 1; i <= meta.getColumnCount(); i++) {
            System.out.printf("%-15s",meta.getColumnName(i).toUpperCase());
        }
        System.out.println();
        while (resultSet.next()){
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                System.out.printf("%-15s",resultSet.getString(i));
            }
            System.out.println();
            foundData = true;
        }
        return foundData;
    }
}

package databases.part11_callable_statements_in_out_params;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    /*
     * CallableStatement, OUT And IN/OUT parameters
     * MySQL has 3 different types of parameter types for it's procedures
     *
     * IN
     *  - Is a READ-ONLY parameter and the default type and doesn't get modified
     *  - Used to pass values to the stored procedure
     *  - If a type is not specified, then it is implicitly an IN parameter
     *
     * OUT
     *  - Is a WRITE-ONLY parameter
     *  - You must specify the OUT type
     *  - Used to return values from the stored procedure to the calling program
     *
     * INOUT
     *  - Is a hybrid parameter, so you can pass data in, which the procedure can modify and return
     *  - Used to pass values to the stored procedure, which can modify the values and return the modified values back
     *
     * These types are specific to MySQL, but many vendors have similar types, with a syntax that might be slightly different
     *
     * GoTo addAlbum procedure
     * .......................
     * Add a new line after CREATE DEFINER
     *  - CREATE DEFINER is important as it gives control of who can execute this procedure
     *  - In this case, this means the procedure will get executed with dev user's privileges
     * Change the Procedure name to albumReturnCounts
     *  - This creates a new procedure
     * Add a 4th parameter
     *  - OUT count INT
     * Update the first 3 parameters
     *  - Add keyword IN
     *
     * Right after COMMIT;
     *  - Set count variable to  i
     *      - will tell us how many songs got inserted
     *
     * Usually best practice to explicitly specify the IN parameter type, though not required
     * Delete Bob dylan before proceeding
     *
     * Copy code from part10_callable_statement Main class
     *  - Update "CALL music.addAlbum(?,?,?)" to "CALL music.albumReturnCount(?,?,?)"
     *
     *  - Running this:
     *      - we get an error for each attempt, that the parameter named count is not registered as an output
     *        parameter
     * There a re a couple of problems here actually
     *  - We need another question mark ,as a parameter placeholder in the call to the stored procedure
     *      - add that and run again
     *  - Running this works
     *      - and data gets added
     *
     * This means registering an OUT parameter isn't actually required
     * You register the OUT parameter if you want the data back
     * Since we do want the result that gets passed back
     * Two steps are involved:-
     *  - Before the execute() , call registerOutParameter() on CallableStatement instance and specify
     *      - the index of the OUT parameter
     *      - the data type - call Types.Integer
     *          - Types is a class in the java.sql package, which defines constants used to identify generic SQL types, called JDBC types, which will be
     *            translated to java types
     *  - retrieve this data from the statement after it's executed
     *      - call getInt() on the CallableStatement and pass the index of the OUT parameter
     *
     * Running this:
     *  - delete 'Bob Dylan' data and re-run
     *  - before all the records are printed, notice the 2 statements printed
     *      - 13 songs were added for Bob Dylan
     *      - 14 songs were added for Blonde on Blonde
     *
     * Next,
     *  - Create a 3rd procedure in MySQL Workbench
     *      - update name to albumINOUTReturnCount from albumReturnCount
     *      - update count to INOUT
     *      - After COMMIT; statement
     *          - check if the records that got inserted equals the count, passed as a parameter
     *              - Log,Audit,Exception Handling stuff
     *      - Apply the changes and finish
     *
     *  - Delete 'Bob Dylan' data and re-run
     *
     * Back to main()
     *  - update name to albumINOUTReturnCount from albumReturnCount
     *  - Pass an initial value to parameter 4,
     *      - Normally, this would be the record count you expect to be inserted
     *          - set it to an arbitrary number like 10
     *      - call setInt() on CallableStatement instance and set
     *          - the index of the parameter
     *          - the value
     *  - Running this:
     *      - Even though we passed 10 as the count, to parameter 4, we actually get the actual count back ; 13 and 14 songs inserted
     *
     * - If we rerun it again before deleting 'Bob Dylan' data:
     *      - We get 0 songs were added , which is what we would expect
     *
     * Switch to MySQL Workbench delete Bob Dylan data
     *  - comment out on the code that is initializing the value for parameter 4
     *
     * Running this:
     *  - prints 0 songs were added but we can see that data was added
     *
     * If we don't set the INOUT parameter to a value, it will get initialized to a default value, depending on the data type and DBMS vendor
     *
     * Here, count wasn't initialized to zero, it was initialized to null
     * null has unexpected and sometimes confusing results in SQL code, if you're new to it
     *
     * ALWAYS INITIALIZE YOUR IN an INOUT parameters
     * This way, you can avoid any unexpected behavior that could arise as we're seeing here
     *  - uncomment the line that ;  cs.setInt(4,10);
     *
     * There's another way to get data back from stored code in the database server and that's by using Callable statement, but executing
     *  a function, rather than a procedure
     *
     * A function's purpose is to return a value, usually the result of some calculation or formula
     * In contrast, a stored procedure is often used for performing a sequence of operations, data manipulation, or enforcing business rules within
     *  the database
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

//            CallableStatement cs = connection.prepareCall("CALL music.addAlbum(?,?,?)");
//            CallableStatement cs = connection.prepareCall("CALL music.albumReturnCount(?,?,?,?)");
            CallableStatement cs = connection.prepareCall("CALL music.albumINOUTReturnCount(?,?,?,?)");

            albums.forEach((artist,albumMap)->{
                albumMap.forEach((album,songs)->{
                    try {
                        cs.setString(1,artist);
                        cs.setString(2,album);
                        cs.setString(3,songs);
                        cs.setInt(4,10);
                        cs.registerOutParameter(4,Types.INTEGER);
                        cs.execute();
                        System.out.printf("%d songs were added for %s%n",cs.getInt(4),album);
                    }catch (SQLException e){
                        System.err.println(e.getErrorCode() + " "+ e.getMessage());
                    }
                });
            });

            String sql = "SELECT * FROM music.albumview WHERE artist_name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "Bob Dylan");
            ResultSet rs = ps.executeQuery();

            printRecords(rs);

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

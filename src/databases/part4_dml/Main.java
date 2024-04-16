package databases.part4_dml;

import java.sql.*;

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
     * The execute() can be used for different kinds of statements, like insert , update, and delete in addition to
     *  the select statement
     *
     * Running this:
     *  - prints a message that loading the class "com.mysql.jdbc.Driver" is deprecated
     *      - also prints what the new driver class is "com.mysql.cj.jdbc.Driver" and that this is loaded automatically
     *        and therefore no need to do this manually
     *  - prints true
     *
     * Comment out on the code that uses Class.forName() and try catch a well
     *
     * Change the artist name from "Elf" to "Neil Young"
     *  - Neil Young happens to be not int the table
     *
     * Running again:
     *  - still getting a result that's true even though Neil Young is not in the artists database
     *
     * statement.execute()
     * ...................
     *  - always return true when it's used with a SELECT statement
     *  - the boolean result can't be used to test for the existence of a record , in a table
     *
     * statement.getResultSet()
     * ........................
     *  - use getResultSet() to test the data in the result set
     *  - returns type ResultSet
     *
     * Then,
     *  - Set up a boolean variable "found" which will be true if the result set is not null and the next() returns
     *    true
     *      - Use this variable to test whether the artist was found or not found
     *
     * Running this:
     *  - still get the result is true
     *  - but now we get the artist was not found
     *
     * In general, you'll use executeQuery for SELECT statements , but there may be some isolated use cases, where
     *  using execute makes more sense
     *
     * Next,
     * Set up some ()s on this class
     *  - The first will print records given a result set
     *      - printRecords(ResultSet resultSet) : boolean
     *          - Takes a ResultSet as a param and throws SQLException
     *          - returns true if the records were found
     *          - initialize foundData:boolean to false
     *          - copy the code that prints the result set in tabular form and paste it here
     *              - start from where you declare the meta variable
     *              - until the end of the while loop
     *          - Finally set foundData to true in the while loop
     *              - preferably at the end of the while loop
     *          - return foundData from this ()
     *      - printRecords(ResultSet resultSet) will print records found and also returning true, otherwise false.
     *         if no records were found
     *
     *  - The next is a generic one so that it can be used to select data from any record using a single column name
     *          and value
     *      - executeSelect : boolean
     *          - Takes 4 parameters
     *              - Statement obj, String table-name, String columnName, String columnValue
     *          - Throws an SQLException
     *          - Create a SELECT statement using the data passed
     *          - Call statement.executeQuery() and get a ResultSet back
     *              - if we get ResultSet, execute the printRecords(rs) and return its value
     *              - otherwise return false
     *
     * Back to the main()
     *  - Let's remove / comment out on the code we have in the try block and leverage the 2 ()s that we have created
     *      - executeSelect()
     *  - Then set up a couple of variables, table name, column name and column value
     *  - Call executeSelect()
     *      - pass state,emt obj, table name and column name and column value
     *      - test if false and
     *          print maybe we should add this record
     *
     * Running this:
     *  - We get info printed in tabular form since we are using "Elf"
     *
     * Update
     *  - columnValue "Elf" to "Neil Young"
     *  - in my case comment and add another columnName
     *
     * Rerunning :
     *  - The code still prints the column names which means the result set was returned with meta data, but not results
     *  - We get the text "Maybe we should add this record" since executeQuery returned false
     *
     * So let's do that:
     *
     * Create a () that inserts data into a table
     *  - insertRecord() : boolean
     *      - Takes 4 parameters :
     *          - Statement obj
     *          - String table
     *          - String[] columnNames
     *          - String[] columnValues
     *      - Throws an SQLException
     *      - Split/join the column names with a comma delimited string
     *      - Split/join the column values with a comma delimited string, but enclosed in a single quote
     *      - Then construct the insert DML query with these values using the formatted()
     *      - Print the generated INSERT Query
     *      - Call execute() on statement and pass the query statement and storing that in a boolean
     *      - Print the result and return it
     *
     * Call insertRecord() from the main()
     *  - Pass the statement and tablename and the String arrays using a single value in each array and
     *    the same values as before
     *
     * Running this:
     *  - Prints the insert DML statement that was created
     *  - Prints insertResult = false
     *
     * Does this means this statement failed?
     * .....................................
     *  - No actually it doesn't
     *  - If an SQL statement really fails, we'd get an error .i.e an exception and the app would have ended there
     *  So why did we get false back ?
     *  - As it turns out, we only get true if we're running a SELECT query, so we can't use this boolean value to
     *    confirm whether the record was added
     *
     * Let's comment out on the
     *      - System.out.println("insertResult = "+insertResult);
            - return insertResult;
     * And instead
     *  - Check the getUpdateCount()  on the statement obj and store it to recordsInserted:int
     *      - If recordsInserted is greater than zero , then the insert was successful
     *  - For good measure
     *      - we'll call executeSelect passing the first column name and value to select data
     *  - Then return true if the no of recordsInserted > 0
     *
     * Running this :
     *  - Prints out that "Neil Young" info with the generated artist id
     *  - This did not execute the insert, because we inserted this in the previous run
     *
     * Change the artist name again
     *  - This time to "Bob Dylan"
     *
     * Rerunning this code:
     *  - Now we can see the insert statement that was executed and we get the confrimation Bob Dylan was added
     */

    private static final String CONN_URL = "jdbc:mysql://localhost:3306/music";

    public static void main(String[] args) {

//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        try(Connection connection = DriverManager.getConnection( CONN_URL,System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASS"));
            Statement statement = connection.createStatement();){
//            String artist = "Elf";
//            String artist = "Neil Young";
//            String query = "SELECT * FROM artists WHERE artist_name = '%s'".formatted(artist);
//            boolean result = statement.execute(query);
//            System.out.println("result = "+result);
//            ResultSet rs = statement.getResultSet();
//            boolean found = (rs != null && rs.next());
//            System.out.println("Artist was "+ (found ? "found" : "not found"));
            String tableName = "music.artists";
            String columnName = "artist_name";
//            String columnValue = "Elf";
//            String columnValue = "Neil Young";
            String columnValue = "Bob Dylan";

            if(!executeSelect(statement , tableName,columnName,columnValue)){
                System.out.println("Maybe we should add this record");
                insertRecord(statement,tableName,new String[]{columnName},new String[]{columnValue});
            }
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

    private static boolean executeSelect(Statement statement , String table,String columnName,String columnValue)
     throws SQLException{
          String query = "SELECT * FROM %s WHERE %s = '%s'".formatted(table,columnName,columnValue);
          ResultSet rs = statement.executeQuery(query);
          if (rs != null)
              return printRecords(rs);
          return false;
    }

    private static boolean insertRecord(Statement statement, String table ,
                                        String[] columnNames,String[] columnValues) throws SQLException {
        String colNames = String.join(",",columnNames);
        String colValues = String.join("','",columnValues);
        String query = "INSERT INTO %s (%s) VALUES('%s')".formatted(table,colNames,colValues);
        System.out.println(query);
        boolean insertResult = statement.execute(query);
//        System.out.println("insertResult = "+insertResult);
//        return insertResult;
        int recordsInserted = statement.getUpdateCount();
        if (recordsInserted > 0)
            executeSelect(statement , table,columnNames[0],columnValues[0]);
        return recordsInserted > 0;
    }
}

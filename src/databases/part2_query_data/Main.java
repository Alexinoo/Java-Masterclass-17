package databases.part2_query_data;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Main {

    /*
     * Querying Data
     * .............
     *
     * We can use a statement which is an interface in java
     * Its implemented by the database vendor in the JDBC driver and rep an SQL statement
     * This can be either DML or DDL
     * A statement once executed will return the results of the query , in a ResultSet obj
     *
     * Set up a simple query statement to get all the artists from music.artists table
     *  - SQL commands are all specified in upper case which is a common practice
     *  - In contrast , the schema and table name are in lower case
     *
     * Although, MySQL is a case-insensitive language, some databases are case sensitive , so it's best practice to
     *  use lowercase for data objects both in naming them and querying them
     *
     * First create a Statement Object and include it in the try-with-resources block
     *  - call createStatement() on connection instance
     *  - then call executeQuery on statement instance and pass the query which in turn returns a ResultSet
     *
     * Comment out on sout
     *
     * All 3 obj(s) , the connection, the statement and the result set are all resources that need to be closed
     * We could've included the result set in the try-clause, along with the connection statement but it's not
     *  necessary there
     * A ResultSet Object is automatically closed, when the Statement Object that generated it is closed
     * Its also closed if the statement is re-executed or used to retrieve the next result from a sequence of multiple
     *  results
     * The ResultSet contains all the records in the artist table
     *  - Loop through its content with a while loop
     *      - like an iterator, we can check if there's a next value in the result set
     *      - print artist id and artist name
     *          - there are diff ways to retrieve column/field data
     *              - we can do it by columnIndex
     *                  - call getInt() and pass 1 e.g. resultSet.getInt(1)
     *              - we can do it by columnLabel
     *                  - call getString() and pass label e.g. resultSet.getString("artist_name")
     *
     * Running this:
     *  - List all the artists in the table in the order of the artist id
     *
     * There are very popular database management tools, such as Toad, DBeaver and Oracle's SQL Developer. that have
     *  been written in java
     * These are similar to MySQL Workbench, which is actually written in C++
     * You can use any of these tools o connect to a variety of databases
     *
     * Now let's change this SQL statement, to get a few records from the view, named "albumview"
     *  - Limit the data coming back to just a single album named "Tapestry" with a WHERE clause
     *  - Think of a WHERE clause as a filter, to get a subset of data, based on a particular condition as defined
     *     in the clause
     *      - add a String : albumName - set it to "Tapestry"
     *  - Change the table from artists to albumview
     *      - You can query a view just like you would a table
     *  - Filter where album name is Tapestry
     *
     * In ANSI SQL, String literal should be enclosed in single quotes, when they're used in WHERE clauses
     * Double quotes are used to delimit identifiers such as table and column names, which means they could contain spaces
     *  or other char(s) that are not allowed in text literals
     *
     * Some SQL implementations, including MySQL, allow you to use double quotes around text literals , but let's stick
     *  with single quotes here
     *
     * Because the data coming back will be different for this view, from the artists able, we need to change the code
     *  in the while loop, where we are printing data out
     *  - add track_number - pass it to getInt()
     *  - add song_title
     *
     * Running this:
     *  - Prints Carole King was the artist for the entire album and each of the song is listed by the track no order
     *  - THe view itself is ordered by track no, and therefore no need for the ORDER clause in SQL statement
     *
     * If you don't know what data might be, that's returned from your query, we can use the ResultSetMetaData obj to get
     *  more info about the ResultSet
     *
     * Let's add this code before we execute the ResultSet
     *  - call getMetaData() from the resultSet and assign that to "meta"
     *  - get the number of columns that were returned from meta.getColumnCount()
     *  - Loop from 1 to the column count
     *      - for each column, print the col index which starts at 1
     *      - print the col name and its type, then pass the appropriate data to printf statement
     *      - there are multiple ()s on the result set meta data that takes a column index
     *          - getColumnName(int index)
     *          - getColumnTypeName( int index)
     *  - Include a separator line
     *
     * Running this:
     *  - Prints info about the columns
     *
     * We can use this info to help facilitate handling data in a generic fashion
     *
     * Remove the code we have in the while loop
     *  - comment out instead
     *
     * Before the loop,
     *  - add a column of headers using the result set metadata
     *  - loop 1 to the column count incrementing by one
     *      - print each column name, making it left justified , and allowing 15 char(s) and convert to Uppercase
     *  - print a new line
     *
     * In the while loop, do something similar
     *  - loop through the meta index
     *      - print the resultSet's value using getString with the meta index
     *  - print a new line
     *
     * Running this:
     *  - We get all the data in a grid, much like i'd see if I ran the query in a MySQL WorkBench
     *
     * We can use ResultSet.next() for any type of select query, from any table or view
     * The result set meta data object, has other ()s , such as column type and width which you can use to make this
     *  more flexible
     *
     * That's a quick intro to Statement Type, using the executeQuery()
     *  statement.executeQuery()
     *      - this () returns the results of the query in a ResultSet object
     * And you can use the result set metadata obj to query info about the result set
     *
     */

    public static void main(String[] args) {
        Properties props = new Properties();
        try {
            props.load(Files.newInputStream(Path.of("music.properties"),
                    StandardOpenOption.READ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String albumName = "Tapestry";
        //String query = "SELECT * FROM music.artists";
        String query = "SELECT * FROM music.albumview WHERE album_name = '%s'".formatted(albumName);

        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));

        try (var connection = dataSource.getConnection(props.getProperty("user"),
                props.getProperty("password"));
             Statement statement  = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

            var meta = resultSet.getMetaData();
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                System.out.printf("%d %s %s%n",
                        i,
                        meta.getColumnName(i),
                        meta.getColumnTypeName(i)
                        );
            }
            System.out.println("=".repeat(50));
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                System.out.printf("%-15s",meta.getColumnName(i).toUpperCase());
            }
            System.out.println();
            while (resultSet.next()){
            // System.out.printf("%d %s %n",resultSet.getInt(1),resultSet.getString("artist_name"));
            // System.out.printf("%d %s %s %n",resultSet.getInt("track_number"),
            //   resultSet.getString("artist_name"),resultSet.getString("song_title"));
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    System.out.printf("%-15s",resultSet.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

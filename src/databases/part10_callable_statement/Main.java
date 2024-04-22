package databases.part10_callable_statement;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    /* The Stored Procedure
     * ....................
     * Stored procedure are a way to encapsulate complex SQL logic, and data manipulation, into re-usable modules that can
     *  be called within the database
     * They can help improve application performance, modularity and security
     * They're precompiled queries or segments of code, that are stored as just another database obj
     * Your JDBC code can be simplified quite a bit, if you're able to take advantage of stored procedures
     * A stored procedure can also be written externally in Java, Python, or C and stored in a wrapping procedure in the
     *  db, which means the code will run on the server
     *
     * The CallableStatement
     * .....................
     * Java supports the execution amd retrieval of results, from these code segments, with a special kind of statement
     *  called the CallableStatement
     * Like a PreparedStatement, this first takes a parameterized string, when you get an instance
     * The obj reference will already be compiled in the database server
     * Like a () in java, you pass in data with parameters and you may or may not get data back
     * Depending on your db, the data coming back might be a result from a function, or it could be returned with declared
     *  output parameters
     * Some databases also supports a hybrid parameter, an input output parameter which lets you pass which lets you pass data
     *  which may be modified by the procedure, and passed back
     *
     *
     *
     * Set up a couple of constants that rep the column index of the data in the csv file
     * Won't set track no, will be set by stored procedure based on the no of the song in the album
     *
     * main()
     * Will use a Map keyed by String with a nested Map as the value
     *  - Initialize to null
     *
     * Show an alternative of reading data from CSV using the lines() which returns a Stream<String>
     * A reminder that when you use Files.lines, use try-with-resources, so the stream is automatically closed
     * Inside the try block
     *  - Set up a stream pipeline
     *  - Split each line with a comma using map operation and a lambda that splits the string
     *      - this means the stream goes from a Stream<String> to a Stream of array of strings
     *  - Collect this data into a Map with Collectors.groupingBy
     *      - first level is group by artist
     *      - next grouping is by album
     *      - next, get the song column to collect the song titles - do this with mapping returning only the song title
     *  - Next call Collectors.joining which ultimately uses StringJoiner
     *      - the goal here is to make this look like an array of JSON - which really looks like a printed array
     *      - the delimiter is a comma, but we also need to wrap each song title in double quotes, so we can escape that
     *        with a backslash on either side of the comma
     *      - this will be wrapped in a square bracket which will be prefix and suffix elements - start with quotes
     *      - use closing square bracket as suffix
     *  - print the album out
     *      - key is the artist
     *      - value is the albums
     *      - loop the nested map's data
     *          - key is the album name
     *          - value is the string value , the array of songs
     *      - Running this:
     *          - prints 2 albums and we've got all the songs in a single comma delimited string, each song title
     *            enclosed , in double quotes
     *
     * Copy the code to connect to the db from a previous code
     *  - remove addToFile()
     *  - keep select from albumview
     *      - keep PreparedStatement
     *      - and its execution
     *  - copy printRecords()
     *
     * Inside the try block
     *  - Will execute the stored procedure using a Callable Statement
     *      - call prepareCall() on the connection instance
     *          - pass a Sql string
     *  - To execute the procedure in a database, you usually use the keyword call followed by the procedure name
     *      - and like PS, use a ? for parameters
     *      - in this case the stored procedure has 3 params , so will add 3 ? mark(s)
     *  - A Callable statement also gets parsed and compiled , which means we can re-use this statement efficiently
     *  - Next, loop through the album's map, looping through the keys and values,
     *      - key is the artist and the value is the nested map
     *      - again use forEach and the key is the album and the value is the songs array, a string, a JSON array
     *      - include try-catch in this lambda expression
     *          - Like a PS, we set the values for the placeholders,
     *          - so first the artist,then the album and next is the json (which is really just a String ultimately)
     *      - call execute() on cs to run
     *  - The stored procedure doesn't return any data back
     *      - In case , we get an exception, we'll print a more specific vendor error code and message
     *
     *  - Running this
     *      - prints data in the album view
     *          - confirms that we've successfully added the artist, the 2 albums and all the related songs in
     *            the csv file with just 2 method calls, to code stored in the database
     *  - Some databse vendors do support arrays, but MySQL is not one of them, so that's why we used JSON workaround
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

            CallableStatement cs = connection.prepareCall("CALL music.addAlbum(?,?,?)");

            albums.forEach((artist,albumMap)->{
                albumMap.forEach((album,songs)->{
                    try {
                        cs.setString(1,artist);
                        cs.setString(2,album);
                        cs.setString(3,songs);
                        cs.execute();
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

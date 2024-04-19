package databases.part8_prepared_statement;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    /* What happens on the server when it receives a request from JDBC Driver?
     * ......................................................................
     * When the query is received by the Database server ?
     *  - Query is parsed
     *      - SQL syntax is checked, and tables and column names are verified
     *  - An Execution plan is created
     *      - The database server analyzes the query and works out an optimal way to execute the statement
     *      - The result is a compiled statement
     *      - Once compiled, it's stored in a cache on the database server
     *      - This means it can be reused without having to be recompiled each time it is executed
     *      - Importantly, this compiled statement can also be stored as a special kind of JDBC statement
     *
     * PreparedStatement
     * .................
     *
     * A PreparedStatement in JDBC, is a precompiled statement
     * When we were reviewing regex, we could get a compiled regex by using the Pattern.compile()
     * In some ways, this is similar to precompiling an SQL statement
     * In both cases, a String is passed, which needs to get interpreted,a s some operations that will occur
     * The string first needs to get parsed, its syntax tested, and some optimizations may optionally be applied
     * There's some overhead with this process, so if you're using the statement multiple times, it makes sense to precompile it
     *
     * The PreparedStatement Syntax
     * ............................
     * A PreparedStatement is used to execute the same statement multiple times, with parameter value placeholders
     * This improves performance as the database server does not need to parse and compile the statement, each time it is executed
     * A parameter in an SQL string passed to a PreparedStatement, is defined with a question mark (?) as a placeholder
     *  e.g. SELECT * FROM music.artists WHERE artist_name = ?;
     * Notice here, we don't include single quotes around the question mark
     * This is because, when you use a prepared statement, the work of enclosing literals, is determined by the type and done for us
     * When you use the prepared statement, you'll pass the values at that time, specifying their data types as well.
     * This means the values passed as data, will never be interpreted as SQL code
     * Because of this and very importantly, PreparedStatements help prevent SQL injection attacks
     * You can have multiple parameters in the SQL string, and they can be different types
     *  e.g. SELECT * FROM music.songs WHERE album_id = ? AND song_title like ?;
     * Specifying placeholders is the same, regardless of the type parameter
     *
     * Create a Connection
     * ...................
     * Create a connection to the database using data source
     * In a server environment, you wouldn't be instantiating a new instance of a known driver class, but all the other operations would be the same
     *  - set server name , port,and the database, username and password (edit configurations)
     *  - set ContinueBatchOnError to false, so that all the batch statements get executed instead of stopping at the first error
     *      - wrap with try-catch
     *
     *
     * Select a Simple SELECT statement to get fetch data from the music.albumview
     *  - Instead of using a formatted string or a concatenated string, for the artist name value
     *  - Pass a question mark instead
     *  - This is how you parameterize an SQL statement when you plan to use it in a prepared statement
     *          e.g. SELECT * FROM music.albumview WHERE artist_name = ?
     * Call prepareStatement() on connection instance and pass the SQL String variable
     *  - Set the value of the parameter by calling setString() on the preparedStatement
     *      - setString() takes
     *          - parameter index : int
     *          - String
     *  - Invoke executeQuery on the prepared statement as well, which will return a result set
     *  - Note we're not calling the enquote literal () when replacing a placeholder
     *  - in a prepared statement, you specify the type, by calling the relevant set method
     *      - setString() in this case
     *  - The server will appropriately enquote literals as needed, based on that type
     *
     * Add a Print Record () - re-use the one win query music project
     *  - Call it from the main() after executing the query
     *  - Running this
     *      - we get the data for the artist Elf's 2 albums
     *
     * Next,
     * We'll use preparedStatements, to insert data into the music db
     * Insert Data into the music database from a CSV file
     *  - start with the parameterized strings as static strings
     *      - insert to artists
     *      - insert to albums
     *      - insert to songs
     *
     *  - Create addArtist()
     *      - Takes PreparedStatement ob, Connection obj , String:artist_name
     *          - Initialize artist id to -1
     *          - set the first parameter to artist name
     *          - call executeUpdate() to get the insert count
     *              - if count is greater than 0 get the generated key by calling getGeneratedKeys() on ps instance
     *              - This returns a results set and check if there are any records by calling next()
     *              - get the key by calling getInt on the ResultSet and print it
     *          - return artist ID since we'll need it to insert other records
     *
     *  - Create addAlbum
     *      - Copy addArtist() and update effectively
     *          - Insert artistId as the 3rd arg
     *          - include the artistId as the first parameter
     *          - update artistId to albumId (in all the four instances)
     *          - update setString() respectively
     *
     *  - Create addSong
     *      - Copy addArtist() and update effectively
     *
     *
     */

    private static String ARTIST_INSERT = "INSERT INTO music.artists(artist_name) VALUES(?)";
    private static String ALBUM_INSERT = "INSERT INTO music.albums(artist_id,album_name) VALUES(?,?)";
    private static String SONGS_INSERT = "INSERT INTO music.songs(album_id,track_number,song_title) VALUES(?,?,?)";

    private static int addArtist(PreparedStatement ps, Connection conn, String artistName) throws SQLException {
        int artistId = -1;
        ps.setString(1,artistName);
        int insertedCount = ps.executeUpdate();
        if (insertedCount > 0){
            ResultSet generatedKey = ps.getGeneratedKeys();
            if (generatedKey.next()){
                artistId = generatedKey.getInt(1);
                System.out.println("Auto-incremented ID: "+artistId);
            }
        }
        return artistId;
    }

    private static int addAlbum(PreparedStatement ps, Connection conn,int artistId, String albumName) throws SQLException {
        int albumId = -1;
        ps.setInt(1,artistId);
        ps.setString(2,albumName);
        int insertedCount = ps.executeUpdate();
        if (insertedCount > 0){
            ResultSet generatedKey = ps.getGeneratedKeys();
            if (generatedKey.next()){
                albumId = generatedKey.getInt(1);
                System.out.println("Auto-incremented ID: "+albumId);
            }
        }
        return albumId;
    }

    private static int addSong(PreparedStatement ps, Connection conn,int albumId,
                               int trackNo , String songType) throws SQLException {
        int songId = -1;
        ps.setInt(1,albumId);
        ps.setInt(2,trackNo);
        ps.setString(3,songType);
        int insertedCount = ps.executeUpdate();
        if (insertedCount > 0){
            ResultSet generatedKey = ps.getGeneratedKeys();
            if (generatedKey.next()){
                songId = generatedKey.getInt(1);
                System.out.println("Auto-incremented ID: "+songId);
            }
        }
        return songId;
    }

    public static void main(String[] args) {

        var dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("music");

        try {
            dataSource.setContinueBatchOnError(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try(Connection connection = dataSource.getConnection(System.getenv("MYSQL_USER"),System.getenv("MYSQL_PASS"))){

            String sql = "SELECT * FROM music.albumview WHERE artist_name = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, "Elf");
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

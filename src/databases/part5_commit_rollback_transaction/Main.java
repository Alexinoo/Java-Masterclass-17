package databases.part5_commit_rollback_transaction;

import java.sql.*;
import java.util.Arrays;

public class Main {

    /*
     * Database Commits
     * ................
     * The AUTOCOMMIT option tells the database to automatically commit changes after every statement
     * By default a Connection obj is in auto-commit mode
     * This can be useful for small changes, but can also be dangerous if you make a mistake
     * Changes are first stored in a temporary location, called a redo log or journal file
     * When you execute a commit these changes are then persisted permanently to the database
     * Its a good idea to turn AUTO-COMMIT OFF , if you want a series of related statements to be treated as a single atomic operation
     * This means all the statement in that group have to be run successfully, before they actually get persisted permanently
     *
     * Transactions
     * .............
     * In a database world, a transaction is a series of one or more database operations (such as inserts, updates, or deletes ) that are
     *  treated as a single unit of work
     * Transactions ensure that database operations are atomic, meaning they EITHER ALL succeed or ALL fail
     * If any part of the transaction fails, the transaction gets rolled back and no changes are applied to the db
     * In JDBC , a transaction is initialized, when we turn AUTO-COMMIT OFF, on the Connection obj
     */

    /*
     * deleteArtistAlbum()
     *  - Pass the Connection obj , Statement obj, String artist name, album name
     *  - Throws SQLException
     *  - print default autoCommit from getAutoCommit() on Connection instance
     *
     *  - call deleteArtistAlbum() in the main () and pass the required params
     *
     * Running this :
     *  - returns Bob Dylan records because he's already in the db
     *  - prints AUTOCOMMIT is set to true (default for any JDBC connection)
     *
     * Back to deleteArtistAlbum() and set up delete queries
     *  - set delete statement as a text block
     *      - use a subquery to specify where conditions
     *  - use executeUpdate() and pass the delete songs query
     *      - returns a number of records that were affected (no of records deleted)
     *      - print the no of rows deleted
     *
     *  - Once songs have been deleted, delete the associated album
     *      - construct the deleteAlbum query and simulate an error on missing closing quote on the format specifier
     *      - use executeUpdate() and pass the delete album query
     *          - returns the album deleted
     *              - print the no of album(s) deleted
     *
     * Running this:
     *  - Songs appear to have been deleted successfully
     *  - we get an SQLSyntaxErrorException
     *  - however, it appears that there is no data in the view
     *
     * No we have an album that has no songs
     *  - There's no foreign key that prevents this scenario, though not ideal in this situation to have an album with no songs in our data
     *
     * So we don't want any songs deleted if the album record deletion fails
     *  - We want these 2 statements execute as a unit , so its EITHER ALL succeed or ALL Fail
     *      - in other words, we want to establish a transaction
     *  - We need to turn AUTO-COMMIT off on the connection but before that fix the syntax error in the code
     *      - include the ending single quote in the delete Albums string
     *  - Then delete 'Bob Dylan' from the artist table
     *      - We want this code to fully delete all Bob Dylan data
     *      - construct delete query on artists
     *      - call executeUpdate() and get the count of deleted artists
     *      - print how many artists were deleted - which is hopefully only 1
     *
     *
     * Solution
     * ........
     *  - Enclose all delete statements inside a transaction
     *      - Turn auto-commit off
     *          - by calling setAutoCommit on the connection and passing it false
     *      - At the end of the (), manually commit by calling commit() on the connection
     *      - Then set AUTOCOMMIT to true
     *
     * Running this:
     *  - The output looks exactly as before
     *  - Looks like 7 rows were deleted
     *      - But until another commit is done, this deletion operation's result are sitting in a temporary location, waiting to be committed
     *
     * We really don't want them to be committed on this connection but rolled back from the temporary location as if they were never executed
     *     - Wrap a try block around the transaction code
     *          - commit inside the try block by calling commit() on Connection obj
     *     - Catch SQL Exception
     *          - rollback the transaction by calling rollback() on Connection obj
     *          - rollback()
     *              - set the state of the temporary storage back to the original state when the transaction started, before we executed any statements at all
     *
     * Running this again:
     *  - looks like 7 records were deleted and the output looks like the same,
     *  - But we can see them in the view even after the exception
     *
     * Fix the syntax error and re-run again
     *  - Now Bob Dylan and his album and songs have all been deleted as a single unit in a database transactions that consisted of 3 individual SQL
     *    deletion statements
     *
     * Each executeUpdate statement makes a round trip to the db, which for a remote database which can be expensive
     * You can usually increase performance, if you batch up your statements
     *  - Comment out on the statements that uses executeUpdate() an print statements that follow each of them
     *      - in my case will just copy existing and duplicate then comment out on the code that uses executeUpdate()
     *
     *  - Follow the comments
     *      - Add 3 addBatch() statements , one for each query
     *
     * - addBatch()
     *      - doesn't execute anything and simply adds the statements to a list of statements that will get passed to
     *        the server
     *      - To execute the statement, we have to call executeBatch() on the Statement obj
     *
     *  - executeBatch()
     *      - returns an int[] containing the results of each statement as if we ran executeUpdate() on each
     *      - we should get the no of records deleted for each statement
     *      - print the data out
     *
     * Running this:
     *  - Prints the individual results of the batch execution printed in the integer array : [7,1,1]
     *      - the first statement affected 7 records
     *      - the next 2 affected 1 record which is consistent with the results we saw
     *
     * Whether it's batch or not, we'll get an error and this code will roll back the changes if there's any sort of syntax error
     *
     * If we make the same syntax error (removing the ending single quote on the deleteAlbumQuery)
     *
     * Running this :
     *  - We get a different error than the one we were expecting which was aan SQL Syntax Error
     *  - Instead, we're getting a foreign key constraint - thrown if the code was trying to delete an artist, and unrelated album still exists
     *
     * So is statement.executeBatch(); really executing all the statements ?
     * ....................................................................
     * Well , Yes it is
     * Each JDBC provider can choose how they want to handle batch statements
     * The MySQL Connector J Driver by default will execute every statement, even if previous statements throws errors
     *  - This can be controlled by a property on the driver
     *
     * We have properties that are associated with the JDBC Statement Class in the MySQL driver
     *  - The 2nd property is called continueBatchOnError and the default is set to true
     *  - So, the behavior we seem to be seeing corresponds to this documentation
     *
     * To change this, we can set up a property with a properties file but since we're not using properties file in this case,we'll simply set
     *  the value in the url string
     *
     * GoTo main()
     *  - add parameters to the connection string by first adding a question mark (?) after the schema name and then provide a list of values
     *      - add "?continueBatchOnError=false" after the schema name
     *
     * Re-running this:
     *  - Again, we get an error, though quite significant that it's the SQL syntax error
     *  - Setting this property may or may not make sense depending on what you're trying to do
     *
     * But if you're expecting these statements to be run as a single transaction, it makes sense to set this property to false and have the batch
     *  processing stop after the first failure
     *
     * Fix the syntax error and re-run again
     *  - successfully deletes any data for Bob Dylan
     *
     * Key Points About Using executeBatch
     * ....................................
     * Multiple SQL statements can be bundled together and sent to the database in 1 trip reducing the overhead of multiple round-trips to the db
     * executeBatch does not necessarily execute all statements sent, if a failure occurs in 1 one of the statements
     *  - this depends on the type of the driver and how it is configured as this example shown
     * It's still important to manage transactions with the use fo commit and rollback , when you use this executeBatch()
     * Batch Processing is used to improve the performance of inserting,updating or deleting multiple rows in a database at once
     *
     *
     *
     *
     *
     *
     */

    public static void main(String[] args) {
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/music?continueBatchOnError=false",
                System.getenv("MYSQL_USER"),
                System.getenv("MYSQL_PASS"));
            Statement statement = conn.createStatement();
        ){
            String tableName = "artists";
            String columnName = "artist_name";
            String columnValue = "Bob Dylan";

          if (!executeSelect(statement,tableName,columnName,columnValue)){
              insertArtistAlbum(statement , columnValue,columnValue);
          }else{
              try{
                 deleteArtistAlbum(conn,statement,columnValue,columnValue);
              }catch (SQLException e){
                  e.printStackTrace();
              }
              executeSelect(statement,"music.albumview","album_name",columnValue);
          }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


    private static void deleteArtistAlbum(Connection conn, Statement statement,
                                          String artistName, String albumName) throws SQLException {
        try{
            System.out.println("AUTOCOMMIT = "+ conn.getAutoCommit());
            conn.setAutoCommit(false);

            /* Using executeUpdate
               ...................
            String deleteSongsQuery = """
                DELETE FROM music.songs WHERE album_id = (SELECT album_id FROM music.albums WHERE album_name = '%s')
                """.formatted(albumName);
            int deletedSongs = statement.executeUpdate(deleteSongsQuery);
            System.out.printf("Deleted %d rows from music.songs%n",deletedSongs);

            String deleteAlbumQuery = "DELETE FROM music.albums WHERE album_name= '%s'".formatted(albumName);
            int deletedAlbum = statement.executeUpdate(deleteAlbumQuery);
            System.out.printf("Deleted %d rows from music.albums%n",deletedAlbum);

            String deleteArtistQuery = "DELETE FROM music.artists WHERE artist_name= '%s'".formatted(artistName);
            int deletedArtist = statement.executeUpdate(deleteArtistQuery);
            System.out.printf("Deleted %d rows from music.artists%n",deletedArtist);
            */


            /* Using addBatch()
               ...................
             * Call addBatch() on Statement obj and pass the query string
             *
             */
            String deleteSongsQuery = """
                DELETE FROM music.songs WHERE album_id = (SELECT album_id FROM music.albums WHERE album_name = '%s')
                """.formatted(albumName);
            String deleteAlbumQuery = "DELETE FROM music.albums WHERE album_name= '%s'".formatted(albumName);
            String deleteArtistQuery = "DELETE FROM music.artists WHERE artist_name= '%s'".formatted(artistName);

            statement.addBatch(deleteSongsQuery);
            statement.addBatch(deleteAlbumQuery);
            statement.addBatch(deleteArtistQuery);

            int[] results = statement.executeBatch();
            System.out.println(Arrays.toString(results));

            conn.commit();

        }catch (SQLException e){
            e.printStackTrace();
            conn.rollback();
        }
        conn.setAutoCommit(true);


    }

    private static boolean executeSelect(Statement statement , String table,String columnName,String columnValue)
            throws SQLException{
        String query = "SELECT * FROM %s WHERE %s = '%s'".formatted(table,columnName,columnValue);
        ResultSet rs = statement.executeQuery(query);
        if (rs != null)
            return printRecords(rs);
        return false;
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

    private static void insertArtistAlbum(Statement statement , String artistName,String albumName) throws SQLException{

        String artistInsertQuery = "INSERT INTO music.artists(artist_name) VALUES (%s)"
                .formatted(statement.enquoteLiteral(artistName));
        System.out.println(artistInsertQuery);
        statement.execute(artistInsertQuery,Statement.RETURN_GENERATED_KEYS);

        ResultSet rs = statement.getGeneratedKeys();
        int artistId = (rs != null && rs.next()) ? rs.getInt(1): -1;

        String albumInsertQuery = "INSERT INTO music.albums(album_name,artist_id) VALUES(%s , %d)"
                .formatted(statement.enquoteLiteral(albumName),artistId);
        System.out.println(albumInsertQuery);
        statement.execute(albumInsertQuery, Statement.RETURN_GENERATED_KEYS);
        rs = statement.getGeneratedKeys();
        int albumId = (rs != null && rs.next()) ? rs.getInt(1): -1;

        String[] songs = new String[]{
                "You're No Good",
                "Talkin' New York",
                "In My Time of Dyin'",
                "Man of COnstant Sorrow",
                "Fixin' to Die",
                "Pretty Peggy-O",
                "Highway 51 Blues"
        };
        String songsInsertQuery = "INSERT INTO music.songs(track_number,song_title,album_id) VALUES(%d ,%s ,%d)";

        for (int i = 0; i < songs.length; i++) {
            String songQuery = songsInsertQuery.formatted(i + 1 ,statement.enquoteLiteral(songs[i]), albumId);
            System.out.println(songQuery);
            statement.execute(songQuery);
        }
        executeSelect(statement,"music.albumview","album_name","Bob Dylan");
    }

}

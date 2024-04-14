package databases;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

public class part1_connection {

    /* Connecting with the Music Database with JDBC
     * ............................................
     * Add jar MySql connector jar files
     *  - Right click on root folder (Java-Masterclass-17)
     *      - Click on "Open Module Settings"
     *          - Select Libraries
     *              - click + and select Java
     *                  - add your jar files and click OK
     *
     * Usernames and Passwords
     * .......................
     *  - never put this info in your source code
     *
     * This leaves you with a few other options such as :-
     *  - Putting it in a properties file such as configuration file
     *  - soliciting data from the user
     * In a server environment, the connection & conn details are configured as part of the Datasource, but we won't
     *  be testing in that kind of environment
     * So rather than encourage bad habits for now, we'll prompt the user for the data
     *  - If we use a Scanner Class with System.in , to get the info from the console, we don't have a way to mask the pwd
     *  - That leaves us with Console, which we learned early on in the course, doesn't work from the IDE
     * Since we haven't covered a lot of User Interfaces options yet except for a Swing dialog , we'll just go with swing
     *  prompts
     *
     * main()
     * Create a local var (username) that will get populated by calling showInputDialog() on a JOptionPane
     *  - showInputDialog() takes
     *      - parentComponent which would normally be the parent page - pass null
     *      - message - prompt message
     *
     * Use a diff () on the JOptionPane for the password
     *  - Create a special type of variable - JPasswordField which is a swing class that will mask the input
     *
     * Use showConfirmDialog which is similar to showInputDialog but in this case, and takes
     *  - parentComponent
     *  - we can pass password field instance as the second arg
     *  - prompt title - String
     *  - choose OK or CANCEL option
     *
     * Set the password to be char[] and make it final
     *  - If the user enters OK, get the password from the pf variable, otherwise set it to null
     *      - getPassword() returns a char[] and not a String because a String might get interned, and this password,
     *        if it was a String, could inadvertently be stored on the String pool
     *          - a memory dump in any case could reveal the user's password
     *          - its best practice to encrypt or hash the pwd, but won't include that code here
     *          - in most cases, this info will hopefully be stored securely on a server
     *
     * Next,
     * Set up a static string for what's called a Connection Url
     * This is a String which uniquely describes how, and what you're connecting to & is determined by the database vendor
     *  private final static CONN_STRING : String
     *      - for mysql, this will always start with jdbc:mysql://hostname:port/db
     *
     * Depending on what database vendor you're using, you conn string may be a little different
     *
     * We use the connection string as input to get the connection
     * There are 2 ()s to do this:
     *  - Using DriverManager Class
     *      - Like opening a file, you want to open a connection with a try-with-resources block
     *          - Inside the try() set up a Connection variable, calling it a connection and calling a static () on the
     *            DriverManager Class named getConnection()
     *              - getConnection() takes
     *                  - a Connection url
     *                  - optionally username and password (turn char[] into a String)
     *              - print successful connection to the db
     *              - Another best practice is once you've used the pwd for whatever reason, reset the char(s), so the pwd
     *              is only in memory for the shortest time possible
     *          - catch SQLException thrown by getConnection() and throw it as a runtime exception
     *
     * We don't have to close the connection,because we've created it (and implicitly opened it) in this
     *  try-with-resources code
     * The connection will get automatically closed when this code completes
     *
     * Running this:
     *  - Pops an inputDialog that prompts for username
     *  - Pops an inputDialog that prompts for password
     *  - successfully connects to the db
     *
     * We successfully connected the MySQl database from a Java application, using the DriverManager class to do it
     *
     *  - Using Datasource Class
     *      - Before try-with-resources create a local var using type inference, called dataSource, and set it to
     *        a new mySQLDataSource() which is MySQL's implementation of this type
     *          - new mySQLDataSource() constructor does not take any args
     *      - Set the connection string by calling setURL() on dataSource instance
     *      - We have created a basic DataSource implementation which will produce a standard connection obj, much
     *          like we'd get with the DriverManager, so it won't be pooled or used in a distributed transaction
     *      - If, you're working in a multi-tiered production env, your client would get a datasource instance a diff way
     *      - Comment out on the first try-with-resources
     *          - Replace with a call from the dataSource instance
     *
     * Running this:
     *  - We get an error, because we didn't pass the username or password that was supplied by the user to the
     *     dataSource.getConnection() and so, this fails
     *  - If, we had gotten, the datasource instance from the naming context or JNDI, I could use getConnection()
     *      with no args
     *  - But since we're using a basic datasource, we do have to pass the username and password we get from the
     *      swing inputs
     *
     * Let's pass the username and the password as a String
     *
     * Re-running this code:
     *  - We now get the success msg
     *
     * We could have also used the setUser and setPassword ()s as we did with setURL
     * We can actually , use datasource without a connection string at all, by using the set ()s on this class
     *
     * Let's do this:
     *  - comment out on the setURL()
     *      - call setServerName() on dataSource and pass "localhost"
     *      - call setPort() on dataSource and pass "3306"
     *      - call setDatabaseName() on dataSource and pass "music"
     *
     *
     * Running this code :
     *  - And we get the same result
     *
     * Datasource is newer and generally preferred over DriverManager
     * Datasource should be used in multi-tiered environment, that requires connection pooling or distributed
     *  transactions
     */
    private final static String CONN_STRING = "jdbc:mysql://localhost:3306/music";

    public static void main(String[] args) {

        String username = JOptionPane.showInputDialog(null,"Enter DB username");

        JPasswordField pf = new JPasswordField();
        int okCxl = JOptionPane.showConfirmDialog(null , pf,
                "Enter DB Password", JOptionPane.OK_CANCEL_OPTION);

        final char[] password = (okCxl == JOptionPane.OK_OPTION) ? pf.getPassword() : null;

        var dataSource = new MysqlDataSource();
       //  dataSource.setUrl(CONN_STRING);
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setDatabaseName("music");

      //  try(Connection connection = DriverManager.getConnection(CONN_STRING, username,String.valueOf(password))){
        try(Connection connection = dataSource.getConnection( username,String.valueOf(password))){
            System.out.println("Success!! Connection made to the music database");
            Arrays.fill(password,' ');
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}






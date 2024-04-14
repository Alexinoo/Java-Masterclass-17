package databases.part1_connection;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.Properties;

public class part2_connection {
    /*
     * Connecting with the Music Database with JDBC - 2
     * ................................................
     * Rather than getting username & password with swing UI components which gets old very fast , let's use a
     *  combination of properties file and environmental variable.
     * Though not a recommended approach for a production application
     *  - Create music.properties on the cwd
     *      - contains plain text key-value pairs
     *          - enter the data that we need to connect to a data source without using a connection string
     *              i.e. serverName,port,databaseName,user and password
     *          - put asterisk for password as the value
     *              - you can put your password there but we'll use an environment variable to add another level of security
     *  - Building configuration
     *      - GoTo Menu > Run > Edit Configurations
     *          - appears empty
     *          - Exit and run the program which creates a configuration for part2_connection
     *  - GoTo Edit Configurations again
     *      - Add MYSQL_PASS=Unlockme13!@ and set it to your pwd
     *
     * main()
     * Set up a properties variable
     * Since we're reading data from the file
     *  - use a try-catch
     *      - call props.load() that takes
     *          - an input stream with the current path of music.properties file
     *          - open options - Read-Mode
     *  - catch IOException and throw it
     *
     *  - We won't retrieve the password till we need it at the time of connect
     *
     * Create a var for the dataSource which is of type MySqlDataSource
     *  - Call setServerName() on dataSource
     *      - call getProperty() on the props obj and pass the key name (serverName)
     * - Call setPort() on dataSource
     *      - call getProperty() on the props obj and pass the key name (Parse it as an Integer)
     * - Call setDatabaseName() on dataSource
     *      - call getProperty() on the props obj and pass the key name (databaseName) - this is optional
     *
     * We've got the dataSource now set up, and now we can establish a connection with our user and password
     *
     * Use try-with-resources since it is important to close database resources, just like file resources
     *  - Call getConnection() on the dataSource
     *      - pass the user property
     *      - pass password by calling System.getenv(MYSQL_PASS) an pass the name that we used
     *  - Print success message
     *
     *  - catch SQLException and throw it
     *
     * Running this:
     *  - prints success
     *
     * We're connecting to the music database, without having to enter the username and password which was getting tedious
     *  quickly
     *
     */

    public static void main(String[] args) {
        Properties props = new Properties();
        try{
            props.load(Files.newInputStream(Path.of("music.properties"),
                    StandardOpenOption.READ));
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));

        try(var connection = dataSource.getConnection(
                props.getProperty("user"),
                System.getenv("MYSQL_PASS")
        )){
            System.out.println("success");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}

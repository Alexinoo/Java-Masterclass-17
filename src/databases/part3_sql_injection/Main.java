package databases.part3_sql_injection;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    /*
     * SQL Injection and ANSI SQL
     * ..........................
     *
     * Prompt the user to enter the album name via console
     * Set up a scanner instance
     *  - prompt the user to enter album name
     *  - get the info from the scanner with the next line
     *
     * Running this:
     *  - Enter Tapestry at the prompt
     *  - Prints all the songs on that album
     *
     * Re-run again:
     *  - Enter Bad Company at the prompt
     *  - Prints all the songs on this album as well
     *
     * We are now providing a way for the user to interact with the database
     * This code isn't error proof or user-friendly
     *  - The album name entered by the user has to match the case exactly and there's no validation on user input
     *
     * Change to prompt the user to enter artist id
     *  - Comment out on prompts on album name and select statement
     *
     * Running this:
     *  - Enter 7
     *  - We get 1 artist back, Roly Gallagher
     *
     * Running again
     *  - Enter 7 or artist_id = 8
     *  - works & we get the data for artist 7 and 8
     *
     * This is somewhat staged and imperfect example, of SQL injection
     * SQL injection is when a user maliciously alters the input, attempting to change the intent of the SQL statement
     *  - This is a means to get more info back or perform unintended operations
     *
     * SQL Injection
     * .............
     *  - Is a serious security vulnerability
     *  - Occurs when an attacker attempts to manipulate the input data sent to an application's database query
     *
     * SQL Injection Points
     * ....................
     * SQL injection vulnerabilities usually exist at points where user input is used, to construct SQL queries
     * Common injection points include:
     *  - User login forms, where input is checked against stored credentials
     *  - Search forms where user input is used to filter database records
     *  - URL parameters, used in dynamic SQL queries
     *  - Any input field in a web application , that interacts with a database
     *
     * Preventing SQL injection
     * ........................
     * There are many ()s to minimize and prevent this attack
     *  - Validate and sanitize user input before using it - we could have parsed the input to an integer
     *  - On the db side, practice the Least Privilege Principle
     *      - Ensure user accounts have the least privilege necessary to perform their tasks
     *      - Our devuser has very broad privileges and the opportunity to do more damage because of it
     *  - Implement proper error handling and logging, to avoid exposing any db or table specifics, in msgs the user sees
     *  - Use prepared statements or parameterized queries, whenever possible
     *
     *
     * Back to the code
     *  - Parse the data we get from the user
     *      - use Integer.parseInt() and parse artistId
     *  - Update the query to use %d instead of %s
     *      - pass the integer variable to the formatted()
     *
     * Running this:
     *  - Again enter "7 or artist_id=8"
     *  - throws a NumberFormatException which is an improvement
     *
     * Running this again :
     *  - enter 77
     *  - returns Jefferson Starships back
     *
     * Moving on
     *  - Comment on the scanner
     *
     * Add a query to just return a subset of the artists, the first 10 for example
     *  - We can do this with the limit clause passing it 10
     *  - Running this prints now 10 records
     *
     * Unfortunately, using the limit clause in this statement, now means this jdbc code isn't portable to other db
     * This is a clause that only a couple of vendors support and its not ANSI SQL
     *
     * ANSI SQL
     * ........
     * ANSI SQL stands for the American National Standards Institute Structured Query Language
     * Each database vendor provides documentation, that covers its own SQL language statements
     *  - This documentation often include info about their compliance with the ANSI SQL standards
     *
     * ISO SQL
     * .......
     * ISO SQL is the International Organization for Standardization's version
     *
     * Both are based on the same core SQL syntax and semantics, and both are maintained by the same technical committee
     *
     * Why stick to ANSI SQL ?
     * ......................
     * There are some good reasons to strive to use ANSI SQL with your JDBC code
     *  - Portability and Database independence
     *  - Readability and Collaboration
     *  - Maintainability and Future-Proofing
     *  - Compliance Requirements
     *
     * Not all vendors implement all ANSI SQL Standards
     * ................................................
     * While using ANSI SQL is a good practice, diff db implement ANSI SQL standards to varying degrees
     * Some advanced features and optimizations will remain vendor specific, and some vendors may only implement the \
     *  standards minimally
     * The instructor recommends to start with ANSI SQL, which can help us maintain flexibility, & minimize vendor lock-in
     * Vendor lock-in occurs when you develop code that relies heavily on vendor specific features, syntax, or functionalities
     * This makes it a challenge to migrate to a different DBMS vendor
     *
     * This is good for the vendor, but may limit options for your organization
     * The limit clause is a good example of how each database might implement this functionality
     *  - The ANSI SQL standard specifies a 'FETCH FIRST' clause
     *  - MYSQL doesn't support that, and instead has the LIMIT clause
     *
     * So if MYSQL doesn't support 'FETCH FIRST', what options do we have to write database agnostic code in this example ?
     *
     * Let's look at a code that will work with any database
     *  - comment out on the SELECT query statement first
     *  - start a new one with a text block
     *  - does the same SQL and does the same thing as limit
     *  - its just a lot uglier and harder to understand
     *  - The WITH clause specifies a subquery or Common Table Expression, called a CTE
     *      - Inside there, a sequential no called row num gets assigned, to every record in the artists table
     *        which is first sorted by artist_id
     *      - Using this subquery or CTE, the code then gets the records that were assigned a row num, less than or
     *        equal to 10
     *
     *  Running this:
     *  - We get the same results as before
     *
     * Has the benefit fo database agnostic
     * But it has the cost of a complex statement, which may not be as efficient as using the LIMIT clause
     * This code is probably going to be difficult for some team members to understand and maintain
     *
     * So another option for this specific problem is to use a feature on the datasource, which limits the records
     *  returned
     *  - Comment on the text block and uncomment the original sql select (daa a new instead)
     *  - The call setMaxRows() on the dataSource instance, passing it 15 - means we only want 10 records back
     *      - setMaxRows() throws SQLException & we need to wrap this in a try catch
     *
     * Running this:
     *  - Gives us the same result as the LIMIT command
     *
     * How do you find the ANSI standards ?
     * ....................................
     * Trying to learn what standards are, and how well each vendor supports each standard is a challenging exercise
     * Oracle provides a good guide, not for the MYSQL database but for their Oracle database, that at least lists
     *  quite a few of the standards in 1 document
     * It the describes how well that database conforms to the standards
     *
     *
     *
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

        var dataSource = new MysqlDataSource();
        dataSource.setServerName(props.getProperty("serverName"));
        dataSource.setPort(Integer.parseInt(props.getProperty("port")));
        dataSource.setDatabaseName(props.getProperty("databaseName"));

        try {
            dataSource.setMaxRows(15);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Scanner scanner = new Scanner(System.in);

//        System.out.println("Enter an album name: ");
//        String albumName = scanner.nextLine();
//        String query = "SELECT * FROM music.albumview WHERE album_name = '%s'".formatted(albumName);

//        System.out.println("Enter an artist id: ");
//        String artistId = scanner.nextLine();
//        int artistid = Integer.parseInt(artistId);
//        String query = "SELECT * FROM music.artists ;


//        String query = "SELECT * FROM music.artists LIMIT 10";

//        String query = """
//                WITH RankedRows AS (
//                    SELECT *,
//                    ROW_NUMBER() OVER (ORDER BY artist_id) AS row_num
//                    FROM music.artists
//                )
//                SELECT *
//                    FROM RankedRows
//                WHERE row_num <= 10""";

        String query = "SELECT * FROM music.artists";
        try (var connection = dataSource.getConnection(props.getProperty("user"),
                props.getProperty("password"));
             Statement statement  = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);

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
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

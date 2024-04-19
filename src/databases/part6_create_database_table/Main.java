package databases.part6_create_database_table;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    /*
     * DDL
     * .....
     *
     * Set up a static query string "USE storefront"
     *  - This is a DDL statement and sets the default database for the session
     *  - Use it to test if we need to create the storefront db
     *
     *
     * main()
     *  - Set up a basic datasource
     *      - since we're running in a standalone env , create a new instance using the class name of the MySql data source
     *      - notice we've not specified the db while setting up the data source since we plan to create it
     *
     *  - Set up checkSchema() to test if the db exists
     *
     * checkSchema()
     *  - returns a Boolean
     *  - Takes a Connection obj
     *  - create a Statement obj with a try-with-resources obj
     *      - Execute "USE storefront"
     *  - return false if there is an exception ; Otherwise, true
     * Call checkSchema from the main()
     *
     * main()
     *  - Setup a connection with try-with-resource
     *      - check if checkSchema returns false
     *          - print storefront db does not exist
     *  - Set up configuration variables
     *
     * Running this:
     *  - We get the error Unknown database storefront
     *
     * SQLEXCEPTION
     * ................
     * SQLException comes with additional info that we can examine
     *  - e.g. SQLState String derived from a list of codes from 1 or 2 possible conventions used to standardize codes
     *  - To figure out which convention the driver uses,we can query a Class called DatabaseMetaData from the connection obj
     *  - There's an integer error code that's specific to each vendor
     *
     * Retrieve DatabaseMetaData as the first statement inside the main() try block
     *  - Call getMetaData() from the Connection obj
     *  - DatabaseMetaData Class contains info related to your Driver Class and your database specifics info
     *
     * Running this:
     *  - Prints 2  - means the driver is using the latest SQL conventions and not the X OPEN conventions
     *
     * checkSchema()
     *  - Print more info about the error we got back (Using System.err.println())
     *      - SQLState
     *      - Error Code
     *      - Error Message
     *  - System.err is standard output for errors and both output to the console as System.out.println()
     *
     * Running this:
     *  - Prints SQLState: 42000
     *  - Prints Error Code: 1049
     *  - Prints Message: Unknown database 'storefront'
     *
     * Visit WIKI SQL State
     *  - 42000 rep syntax error or access rule violation
     *
     * Visit MySQL Error Code
     *  - 1049 rep Symbol ER_BAD_DB_ERROR - SQLState:42000
     *      - followed by specific message "Unknown database '%s'
     *  - Searching 42000 prints results which means there are some other errors that fall into that state
     *
     * Change the code to test for more specific MYSQL error code
     *  - Set up a constant MYSQL_DB_NOT_FOUND and assign it to 1049
     *  - Test both the database product name and the error code
     *      - i.e. if database product is "MySQL" and the error code is 1049, we know for sure the schema doesn't exist
     *          - return false
     *      - Both getMetaData() and throw e - throws a checked exception - added to () signature
     *
     * Create storefront
     *  - setUpSchema(Connection conn) throws SQLException
     *      - returns void and takes a Connection obj
     *      - create db/schema namely storefront
     *          - create storefront.order table using a text block
     *          - create storefront.order_details table
     *      - sets up a parent-child relationship between these 2 tables
     *      - are treated as a single unit when we delete the parent
     *          - if we delete an order, the details will also be deleted
     *  - Execute the query strings
     *      - use try with resources and pass the Statement obj
     *          - pass createSchemaQuery to execute
     *              - check if the schema has been created by calling checkSchema()
     *                  - If so, create the order and order_details
     *                  - print both successful messages for creating the 2 tables
     *      - catch the exception and print it
     *
     *  - call setUpSchema() in the main()
     *      - pass the connection instance
     *
     *  - Running this
     *      - successfully creates storefront db and both order and order_details tables
     *
     * Significant Difference between DDL and DML statements
     * .......................................................
     * - You can't roll back many of the DDL statements, so it's less common to execute DDL in a transaction
     * - Though this might depend on the database vendor that you're using
     */


    private static String USE_SCHEMA = "USE storefront";
    private final static int MYSQL_DB_NOT_FOUND = 1049;

    public static void main(String[] args) {

        var dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setUser(System.getenv("MYSQL_USER"));
        dataSource.setPassword(System.getenv("MYSQL_PASS"));

        try(Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData.getSQLStateType());
            if (!checkSchema(connection)){
                System.out.println("storefront schema does not exist");
                setUpSchema(connection);
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    private static boolean checkSchema(Connection conn) throws SQLException{
        try(Statement statement = conn.createStatement()){
            statement.execute(USE_SCHEMA);
        }catch (SQLException e){
            e.printStackTrace();
            System.err.println("SQLState: "+e.getSQLState());
            System.err.println("Error Code: "+e.getErrorCode());
            System.err.println("Message: "+e.getMessage());
            if (conn.getMetaData().getDatabaseProductName().equals("MySQL") && e.getErrorCode() == MYSQL_DB_NOT_FOUND)
                return false;
            else
                throw e;
        }
        return true;
    }

    private static void setUpSchema(Connection conn) throws SQLException {
        String createSchema = "CREATE SCHEMA storefront";

        String createOrderQuery = """
                CREATE TABLE storefront.order(
                    order_id int NOT NULL AUTO_INCREMENT,
                    order_date DATETIME NOT NULL,
                    PRIMARY KEY(order_id)
                )""";

        String createOrderDetailsQuery = """
                CREATE TABLE storefront.order_details(
                    order_detail_id int NOT NULL AUTO_INCREMENT,
                    item_description text,
                    order_id int DEFAULT NULL,
                    PRIMARY KEY(order_detail_id),
                    KEY FK_ORDERID (order_id),
                    CONSTRAINT FK_ORDERID FOREIGN KEY (order_id) REFERENCES storefront.order(order_id) ON DELETE CASCADE)""";

        try(Statement statement = conn.createStatement()){

            System.out.println("Creating storefront database");
            statement.execute(createSchema);
            if (checkSchema(conn)){
                statement.execute(createOrderQuery);
                System.out.println("Successfully Created Order");
                statement.execute(createOrderDetailsQuery);
                System.out.println("Successfully Created Order Details");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

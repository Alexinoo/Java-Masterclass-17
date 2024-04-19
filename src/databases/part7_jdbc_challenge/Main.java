package databases.part7_jdbc_challenge;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {

    /*
     * JDBC Challenge , Transactions and Insertion using Statement
     *
     * Use MySQL Workbench to verify the changes we make in Java, gets persisted correctly
     * Need to do some settings in MySQL Workbench
     * MySQL Workbench has something called Connection caching
     *  - This means data is cached and not refreshed immediately from persisted data
     *  - Need to configure it to make the refresh interval much shorter
     *  - lets you see changes made in the app reflected immediately in MySQL Workbench
     *       Edit > Preferences > SQL Editor > MySQL Session
     *           (DBMS connection read timeout interval (in seconds) update from 30 to 1 sec)
     *
     * First JDBC Challenge
     * ....................
     * Insert an Order and at least 2 order details into storefront database - in a transaction
     * Use MySql Workbench to confirm the inserts worked
     * Delete an Order and it's Order details
     * Use MySql Workbench to confirm the order and it's line items were actually deleted
     *
     * Create addOrder()
     *  - Returns an int which is the auto-increment id we get back
     *  - Takes a Connection obj and String[] items - item descriptions of the order details
     *      - declare a local variable and initialize it to -1
     *      - Write Insert into Order query
     *      - Write Insert into Order Details query
     *          - for the String specifier, we'll use enquoteLiteral that formats it
     *      - Get DateTimeFormatter
     *          - format it into "yyyy-MM-dd HH:mm:ss"
     *          - Get current local date time and pass it the formatter
     *          - print the order date
     *      - Concatenate insert order query with order date time
     *          - print the query
     *
     *      - Alternatively,
     *          - We could have also used date time specifiers in the formatted string as an alternative to DateTimeFromatter
     *          - Let's see how that would look like
     *          - The inserts starts the same way but the specifiers are a lot more cryptic
     *              - 1$ in both cases means we can just pass a single date time variable for both of these values
     *              - tF will print the date in the format yyyy-MM-dd
     *              - tT is the key to the time, printed the way we want
     *          - Print it out with the LocalDateTime.now() as the arg
     *
     *      - Implement the inserts in a try-with-resources
     *          - Pass a Statement obj
     *          - Since we need a transaction - set AUTOCOMMIT to false
     *              - call executeUpdate on statement and pass
     *                  - formattedString
     *                  - Statement.RETURN_GENERATED_KEYS
     *              - store in inserts int variable
     *                  - if the insert is 1, get the generated id by calling getGeneratedKeys() which returns a ResultSet
     *                      - the first record in the result set, should have the key we want and will be at index 1
     *              - set another local variable, count, which we'll use to test how many detail items got inserted
     *                  - loop through the items passed
     *                      - format the string using the orderId as the first arg
     *                      - enquote the item string using statement.enquoteLiteral()
     *                      - call statement.executeUpdate returning the results back to the inserts variable
     *                          - add the value of inserts to the count variable
     *              - If we get an exception saving the records, the data will be rolled back and a runtime exception thrown
     *                  - But some databases problems are silent meaning we may not get an exception, but something may not
     *                    go as we expected, because of problems with the logic in our queries
     *              - Will add an additional test in this code, to make sure the results are what we expect them to be
     *                  - Check if the value in the count variable is equal to the no of items passed
     *                      - if its not set orderId back to -1
     *                      - print out the issue
     *                      - rollback the transaction if this happens
     *                  - otherwise wrap commit in the else
     *          - roll back the transaction in the catch clause
     *              - throw SQLException in the () signature
     *
     *          - running this:
     *              - we get new order = 1 which is a good sign
     *              - confirmed with MySQL Workbench that the order and order details were saved
     *
     * Create deleteOrder()
     *  - Takes a connection and the order id to be deleted
     *  - Add delete query string
     *  - Add a try-with-resources statement
     *      - pass Statement obj
     *      - call executeUpdate() and pass delete query
     *      - store the no of records deleted and print them out
     *  - catch SQLException and throw a runtime exception
     *
     *
     * Back to main()
     *  - Comment out on the call to addOrder()
     *  - call deleteOrder()
     *      - pass connection obj and delete order id 1
     *  - Running this
     *      - prints 1 record from order table deleted
     *
     * Because of the CASCADE delete defined in the order detail , no need to delete from order details in java
     *          - this was optional in this scenario
     * We can also do this by including this in a transaction which is also a valid solution
     * Do it that way
     *  - Comment out on the previous code in deleteOrder()
     *      - set AUTOCOMMIT to false
     *      - call commit() as the last statement in the try{}
     *      - call rollback() in the catch clause - add exception to the () signature
     *      - add a finally clause
     *          - set AUTOCOMMIT to true
     *  - Add the delete query more generic and put a placeholder in for the table name
     *      - concatenate formatted string with parentQuery and pass "order"
     *      - concatenate formatted string with childQuery and pass "order_details"
     *  - Start deleting childQuery
     *      - print how many child records were deleted
     *  - Call executeUpdate() and pass the parentQuery to delete parent record
     *      - check if the no of records deleted is 1
     *          - if so, commit and print out a successful deletion msg
     *          - otherwise, rollback
     *  - Running this:
     *      - prints 3 records were deleted
     *      - prints Order 2 was deleted
     *
     */

    private static String USE_SCHEMA = "USE storefront";
    private final static int MYSQL_DB_NOT_FOUND = 1049;

    private static int addOrder(Connection conn, String[] items) throws SQLException {
        int orderId = -1;
        String insertOrderQuery = "INSERT INTO storefront.order(order_date) VALUES('%s')";
        String insertOrderDetailsQuery = "INSERT INTO storefront.order_details(order_id,item_description)" +
                " VALUES(%d, %s)";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String orderDateTime = LocalDateTime.now().format(dtf);
        System.out.println(orderDateTime);
        String formattedString = insertOrderQuery.formatted(orderDateTime);
        System.out.println(formattedString);

        //Alternative
        String insertOrderAlternative = "INSERT INTO storefront.order(order_date) VALUES('%1$tF %1$tT')";
        System.out.println(insertOrderAlternative.formatted(LocalDateTime.now()));

        try(Statement statement = conn.createStatement()) {
            conn.setAutoCommit(false);
            int inserts = statement.executeUpdate(formattedString,Statement.RETURN_GENERATED_KEYS);
            if (inserts == 1){
                var rs = statement.getGeneratedKeys();
                if (rs.next())
                    orderId = rs.getInt(1);
            }
            int count = 0;
            for (var item : items) {
                formattedString = insertOrderDetailsQuery.formatted(orderId,statement.enquoteLiteral(item));
                inserts = statement.executeUpdate(formattedString);
                count += inserts;
            }

            if (count != items.length){
                orderId = -1;
                conn.rollback();
                System.out.println("Number of records inserted doesn't equal to items received");
            }else {
                conn.commit();
            }
            conn.setAutoCommit(true);
        }catch (SQLException e){
            conn.rollback();
            throw new RuntimeException(e);
        }

        return orderId;
    }

    private static void deleteOrder(Connection conn, int orderId) throws SQLException{
//        String deleteQuery = "DELETE FROM storefront.order WHERE order_id=%d".formatted(orderId);
//        try(Statement statement = conn.createStatement()){
//            int deletedRecords = statement.executeUpdate(deleteQuery);
//            System.out.printf("%d records deleted%n",deletedRecords);
//        }catch (SQLException e){
//            throw new RuntimeException(e);
//        }

        /////////////// Using Transactions //////////////////////
        String deleteQuery = "DELETE FROM %s WHERE order_id=%d";
        String parentQuery = deleteQuery.formatted("storefront.order",orderId);
        String childQuery = deleteQuery.formatted("storefront.order_details",orderId);
        try(Statement statement = conn.createStatement()){
            conn.setAutoCommit(false);
            int deletedRecords = statement.executeUpdate(childQuery);
            System.out.printf("%d child records deleted%n",deletedRecords);
            deletedRecords = statement.executeUpdate(parentQuery);
            if (deletedRecords == 1){
                conn.commit();
                System.out.printf("Order %d was successfully deleted%n",orderId);
            }else{
                conn.rollback();
            }
        }catch (SQLException e){
            conn.rollback();
            throw new RuntimeException(e);
        }finally {
            conn.setAutoCommit(true);
        }
    }

    public static void main(String[] args) {

        var dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setUser(System.getenv("MYSQL_USER"));
        dataSource.setPassword(System.getenv("MYSQL_PASS"));

        try(Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            if (!checkSchema(connection)){
                System.out.println("storefront schema does not exist");
                setUpSchema(connection);
            }
            deleteOrder(connection,2);
//            int newOrder = addOrder(connection , new String[]{"shoes","shirt","socks"});
//            System.out.println("New Order = "+newOrder);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }


    private static boolean checkSchema(Connection conn) throws SQLException {
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

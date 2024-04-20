package databases.part9_prepared_statement_challenge;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

record OrderDetail(int orderDetailId, String itemDescription, int qty){
    public OrderDetail(String itemDescription, int qty) {
        this(-1, itemDescription, qty);
    }
}

record Order(int orderId, String dateString, List<OrderDetail> details){
    public Order(String dateString) {
        this(-1, dateString, new ArrayList<>());
    }

    public void addDetail(String itemDescription, int qty){
        OrderDetail item = new OrderDetail(itemDescription,qty);
        details.add(item);
    }
}

public class Main {

    /*
     * PreparedStatement Challenge
     * ...........................
     *
     * Change the structure of the order_details table, add a new column called quantity, a number
     * Use data from Orders.csv to add orders into your storefront db
     * Use PreparedStatements to insert each order, and its related items
     * Batch up the line items for each order, but batch only line items for a single order
     * Use a transaction for each individual order, rolling back the order insert, if something fails, but allowing
     *  other orders to be inserted
     *
     * main()
     *  Set an ALTER TABLE statement
     *  Execute this query via Statement obj
     *      - In general, you don't use DDL statements with PreparedStatement in java
     *      - PreparedStatements are typically used with DML statements where the same SQL statement may be executed
     *        multiple times with different parameter values
     *      - There is no benefit of using a precompiled statement for a DDL since it's going to be executed once
     *      - Running this:
     *          - adds a quantity column in MySQL Workbench
     *      - Only needed once, comment out on that code
     *
     * Next,
     * Write the code to read the data from the file that has the order data (Orders.csv)
     * Use a different approach in this code to show an alternative
     *  - Set up 2 records
     *      - Order and Order details
     *  - OrderDetail Record
     *      - Fields
     *          orderDetailId:int
     *          itemDescription:String
     *          qty:int
     *      - Custom constructor - we won't have an order id as we're reading data in from the file
     *          - with only itemDescription and qty
     *          - call canonical constructor, passing -1 as the id
     *
     *  - Order Record
     *      - Fields
     *          orderId : int
     *          dateString : String
     *          details : List<OrderDetail>
     *      - Custom constructor
     *          - with only dateString param
     *          - call canonical constructor and pass-1 for orderId , dateString & initialize details to a new ArrayList
     *      - Methods
     *          - addDetail()
     *              - takes itemDescription and quantity
     *              - create an instance of the OrderDetail pass the description and the quantity
     *              - add that to details arrayList
     *
     */

    public static void main(String[] args) {
        var dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setUser(System.getenv("MYSQL_USER"));
        dataSource.setPassword(System.getenv("MYSQL_PASS"));

        List<Order> orders = readData();

        try (Connection connection = dataSource.getConnection()) {

//            String alterString = "ALTER TABLE storefront.order_details ADD COLUMN quantity INT";
//            Statement statement = connection.createStatement();
//            statement.execute(alterString);

            addOrders(connection,orders);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Write the read data method
     * readData()
     *  - returns a List<Order>
     *  - initialize an arrayList of orders to start with
     *  - Use a Scanner to read the data from the file
     *      - put it in a try-with-resources block
     *      - Unlike Files.readLines,the scanner won't get closed automatically, so if we put it here, we don't have to
     *         remember to close it
     *      - Set the delimiter to be either a comma or a new line
     *          - means instead of splitting the data by line, it's going to split the data by commas
     *      - Create a list of strings by calling scanner.tokens() which gives a stream of strings and we can use map
     *        to trim each value then terminate the stream , collecting it into a list
     *      - Print each value in the orders list
     *      - Loop through every column value, one at a time
     *          - get the column value from the list
     *          - if the value is an order then we know we need to set up an order
     *              - the date is the next place in the list, so we'll do a pre-increment, then get the next value
     *              - create an order and add it to the val list
     *          - similarly, check for keyword item which indicates the tokens that follow are item fields
     *              - parse the next field - qty
     *              - then item description
     *              - get the order details as the last value of the list array
     *              - add the detail to that order
     *      - return List<Order> vals
     *
     *
     * Call readData() from the main()
     *  - call it before opening a database connection
     *  - Create a List<Order> variable and assign that the value of readData()
     *
     *  - Running this now to make sure we get each order as a coherent unit
     *      - prints 5 orders and all the info is stored now in the array of orders
     *
     * Next,
     * Create the addOrder()
     *
     */
    private static List<Order> readData() {
        List<Order> vals = new ArrayList<>();

        try (Scanner scanner = new Scanner(Path.of("Orders.csv"))) {
            scanner.useDelimiter("[,\\n]");
            var list = scanner.tokens().map(String::trim).toList();
            for (int i = 0; i < list.size(); i++) {
                String value = list.get(i);
                if (value.equals("order")) {
                    var date = list.get(++i);
                    vals.add(new Order(date));
                } else if (value.equals("item")) {
                    var qty = Integer.parseInt(list.get(++i));
                    var description = list.get(++i);
                    Order order = vals.get(vals.size() - 1);
                    order.addDetail(description,qty);
                }
            }
            vals.forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return vals;
    }

    /*
     * addOrder() throws SQLException
     *  - Takes a Connection obj, 2 different Prepared statements obj and an order
     *  - Use regular try since we don't want to close any resources if the code fails
     *      - Instead, ignore the error and continuing to process other orders
     *  - Set up a transaction
     *      - set AUTOCOMMIT to false
     *      - Initialize orderId to -1
     *          - Order Table
     *              - set the only parameter, on the PS for the order
     *              - can use setString even when the field is a date time field
     *              - call executeUpdate() on psOrder and check to make sure that only 1 record was inserted
     *                  - if so , get the generated keys from the ps
     *                      - get that and print it out
     *      - Add the code to batch up the details for each order, before executing the whole batch
     *          - Will insert this code in a nested If
     *          - check if the order is > -1, then we know the order, parent order was created ok
     *              - use the psDetail and set the order id which is the first placeholder
     *              - loop through the order details
     *                  - set the 2nd and 3rd parameters, that's item description and qty
     *                  - call addBatch on psDetail
     *              - After batching all the statements, call executeBatch which returns an int[]
     *              - Sum those values up with a quick stream
     *              - Check if the rows inserted is different from the size , of the details
     *                  - If they are diff, throw an exception
     *      - commit the transaction
     *  - Catch SQlException
     *      - rollback any changes
     *      - throw the exception
     *  - Finaly clause
     *      - set AUTOCOMMIT to true
     *
     * Next,
     *  Will need a () that call addOrder
     * AddOrders()
     *  - Setup a String for the insert order and order detail
     *  - Setup a try-with-resource block and pass
     *      - create PreparedStatement for psOrder - pass insertOrder query string
     *      - create PreparedStatement for psOrderDetail - pass insertOrderDetail query string
     *      - return generated keys for both
     *  - Loop through the orders using forEach
     *      - add a multi-line lambda
     *      - do this in a try-clause
     *          - call addOrder and pass the conn, the 2 diff Ps statements (PsOrder,psOrderDetail) and the order
     *      - catch SQLException
     *          - print errorCode, SQLState and error message
     *          - print the state of the ps fro the order
     *          - print the order itself
     *
     * Finally,
     *  call the addOrders from the main()
     *
     * Running this:
     *  - There was a date time error in the data
     *  - this was on purpose to test the requirements of this challenge
     *  - the challenge was to make sure the order would be rolled back, but orders after the bad order, would still
     *    get processed
     *      - the problem was with the 4th order and it's because it's a bad date which is 31-Nov-2023 an invalid date
     *
     *  - Switch to MySQL Workbench
     *      - order table - 4 orders get added
     *      - order_details table - orders details for the orders
     */

    private static void addOrder(Connection conn, PreparedStatement psOrder,
                                 PreparedStatement psOrderDetail, Order order) throws SQLException{
        try{
            conn.setAutoCommit(false);
            int orderId = -1;
            psOrder.setString(1,order.dateString());
            if (psOrder.executeUpdate() == 1){
                ResultSet rs = psOrder.getGeneratedKeys();
                if (rs.next()){
                    orderId = rs.getInt(1);
                    System.out.println("orderId = "+orderId);

                    if (orderId > -1){
                        psOrderDetail.setInt(1,orderId);
                        for (OrderDetail od: order.details()) {
                            psOrderDetail.setString(2,od.itemDescription());
                            psOrderDetail.setInt(3, od.qty());
                            psOrderDetail.addBatch();
                        }
                        int[] data = psOrderDetail.executeBatch();
                        int rowsInserted = Arrays.stream(data).sum();
                        if (rowsInserted != order.details().size()){
                            throw new SQLException("Inserts don't natch");
                        }
                    }
                }
            }
            conn.commit();
        }catch (SQLException e){
            conn.rollback();
            throw e;
        }finally {
            conn.setAutoCommit(true);
        }

    }

    private static void addOrders(Connection conn, List<Order> orders){
        String insertOrder = "INSERT INTO storefront.order(order_date) VALUES(?)";
        String insertOrderDetail = "INSERT INTO storefront.order_details" +
                "(order_id,item_description,quantity) VALUES(?,?,?)";

        try(PreparedStatement psOrder = conn.prepareStatement(insertOrder ,
                        Statement.RETURN_GENERATED_KEYS);
                PreparedStatement psOrderDetail = conn.prepareStatement(insertOrderDetail ,
                        Statement.RETURN_GENERATED_KEYS);
                ){
            orders.forEach(o -> {
                try{
                    addOrder(conn , psOrder,psOrderDetail,o);
                }catch (SQLException e){

                    System.err.printf("%d (%s) %s%n",e.getErrorCode(),e.getSQLState(),e.getMessage());
                }
            });

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

}

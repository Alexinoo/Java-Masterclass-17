package databases.part13_callable_statement_challenge;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

record OrderDetail(int orderDetailId, String itemDescription, int qty){
    public OrderDetail(String itemDescription, int qty) {
        this(-1, itemDescription, qty);
    }

    public String toJSON() {
        return new StringJoiner(", ", "{", "}")
                .add("\"itemDescription\":\"" + itemDescription + "\"")
                .add("\"qty\":" + qty)
                .toString();
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
    public String getDetailsJson(){
        StringJoiner jsonString = new StringJoiner(",","[","]");
        details.forEach(d -> jsonString.add(d.toJSON()));
        return jsonString.toString();
    }
}

public class Main {
    /*
     * CallableStatement Challenge
     * ...........................
     * use storefront db
     *  - Download addOrder.sql procedure
     *      - The SP inserts order and it's details , if it can't find an existing order, for the date time specified
     * The stored procedure has 4 params
     *  - Two are input params
     *      - OrderDate : DateTime
     *      - OrderDetails : JSON
     *          - shd contain an array of Strings :- String[]{itemDescription,qty}
     *  - Two are output params
     *      - OrderId : INT
     *          - id of the inserted record
     *      - InsertedRecords : INT
     *          - records inserted for an order
     * Your Java code should use a CallableStatement to call addOrder procedure
     *  - You'll pass a java.sql.TimeStamp as the first param
     *      - A TimeStamp and DateTime field are often used interchangeably in many db
     *      - Need to transform a String , into a java.sql.TimeStamp
     *      - You can do this with a DateTimeFormatter and the use of LocalDateTime
     *  - You'll pass a String as a second parameter
     *      - This will be the JSON string rep an array of order details
     *      - The input for the array of details shd look like below
     *          [
     *           {"itemDescription" :"Apple","qty":5},
     *           {"itemDescription" :"Orange","qty":2},
     *           {"itemDescription" :"Banana","qty":3},
     *           {"itemDescription" :"Turkey","qty":1}
     *           {"itemDescription" :"Milk","qty":1}
     *          ]
     *  - Finally, register 2 OUTPUT parameters, both ints for the order id, and the number of order detail records
     *      inserted
     *
     * Challenge Helpful Tips
     * ......................
     * Delete the orders in a MySQL Workbench session, which were inserted in the prev challenge
     *  e.g.
     *      DELETE from storefront.order
     *      ALTER TABLE storefront.order AUTO_INCREMENT = 1;
     *      ALTER TABLE storefront.order_details AUTO_INCREMENT = 1;
     * The delete statement will also delete related data from order_details table because of the CASCADE rule
     * The alter command resets the auto increment id to 1 on both tables
     *
     * Challenge Helpful Tips - Use JSONBuilder Template
     * .................................................
     * Earlier in the course, we created a toString template , and called it JSONBuilder which used the StringJoiner
     * We can leverage this to create the JSON string parameter
     * Add this to the OrderDetail Record
     *
     * Challenge Helpful Tips
     * ......................
     * Use the DateTimeFormatter, with the pattern shown below
     *      DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
     *  - Notice we're using a "u" where we normally would use a "y", for the digits in the year
     * Suggested we use the "u" pattern because it causes the parsing to fail , with an exception, on the one bad date
     *  in our data, when using what's called strict parsing
     *
     * Challenge Helpful Tips
     * ......................
     * You can create a LocalDateTime, using the DateTimeFormatter, and then transform it to a SQL TimeStamp type
     *      LocalDateTime localDateTime = LocalDateTime.parse(o.dateString, dtf)
     *      TimeStamp timestamp = TimeStamp.valueOf(localDateTime);
     * A TimeStamp field can be used for a SQL parameter of type DateTime
     *
     *
     * Add 2 Records from previous challenge outside the Main Class
     *  - Order Record
     *      - add a public getDetailsJson() that returns a String
     *      - create a StringJoiner variable, joining with a comma, and starting with a [ and ending with a ]
     *      - loop through the order details and use the add() on the StringJoiner adding the json string we get back
     *         from calling toJson() on each order detail record
     *      - Finally return this string
     *
     *  - OrderDetail Record
     *      - add JSON () using the toString functionality
     *      - Alt+Ins > toString > Select StringJoiner > select fields
     *      - Remove Override annotation
     *
     * main()
     *  - Copy the code from prepared statement challenge
     *
     * Loop through the orders
     *  - print json string for the order details
     *
     * Running this:
     *  - readData() prints out the order details
     *  - prints JSON array strings that were created
     *      - it's an array , so in []
     *      - each element in this array has an itemDescription and a quantity
     *
     * Next,
     * Setup a CallableStatement
     *  - Insert before looping through the orders , because we want to reuse the CS for each record  in my data
     *  - call prepareCall() on connection instance
     *      - pass {} though optional wit calling a procedure
     *
     * Setup DateTimeFormatter obj
     *  - use the pattern shown on the slides
     *
     * Setup params on the callable statement in the forEach loop
     *  - comment/remove the print out statement
     *  - start with a try block
     *      - means if we get an exception on any 1 order, the code will continue to process the other orders
     *  - set up a local date time variable
     *      - create this by passing the the parse() passing the dateString and the formatter
     *  - get Timestamp by calling valueOf() from Timestamp class and passing the local date time
     *
     * Setup Input Parameters
     *  - The first param in the procedure is the datetime but the CS doesn't have a setDateTime()
     *      - instead we use setTimeStamp
     *  - The second param is the JSON string coming out from getDetailsJson()
     *  - Catch any Exceptions here, because of the bad dates and print the error out
     *
     * Register the output parameters
     *  - Both are integers and so
     *      - register param 3 as an Integer
     *      - register param 4 as an Integer
     *
     * Execute the CS
     *
     * Print the no of records inserted and the order id and the date string
     *  - use cs.getInt() and pass index 4 to get the no of records inserted
     *  - use cs.getInt() and pass index 3 to get the order id returned
     *  - pass the dateString on the order
     *
     * Running this:
     *  - Delete orders first and reset auto_increment id
     *  - Prints the last 5 lines of output are the result of the new code
     *      - ALl 5 orders were inserted successfully
     *      - (check 4 statement)
     *          - had an invalid date as printed in the output (31-Nov-2023)
     *
     *  - Going to db to check if this insert is successful
     *      - we get the record was inserted with 30th Nov
     *
     * Not only did Java's LocalDateTime.parse() not throw an error, it actually changed the date
     * This is due toa JDK 8 feature called a Resolver
     *  - is a bit complicated but there are 3 ways the parse() could resolve a date, strict, smart and lenient
     *  - by default, the setting is smart, which means Java will adjust the date accordingly, under certain circumstances
     *    nut not all
     *
     * Parsing a text string occurs in 2 phases,
     *  Phase 1 - is a basic text parse according to the fields added to the builder
     *  Phase 2 - resolves the parsed field-value pairs into date and/or time obj(s)
     *          - can be changed , from its default value of SMART, to either STRICT or LENIENT
     *
     * If you want to log the error, we can chain withResolverStyle() and pass ResolverStyle.STRICT
     *  - This way we can restrict and instead log the exception to the user
     *
     * Running this:
     *  - In this case, an exception was thrown and the order with 31-Nov date was not inserted
     *  - This may be something you want to control, so you might not want to use SMART resolving
     *
     * Querying MySQL workbench again
     *  - ony 4 orders were inserted this time which confirms what we saw in the intelliJ output
     *
     * Changing date formatter to yyyy
     *
     *  Rerunning this:
     *  - We get 4 exceptions that the date string could not be parsed
     *
     * As it turns out, in STRICT mode, if you use the yyyy pattern , you need to specify the era
     *  - We cna do this very easily by adding the pattern G at the start of the pattern
     *      e.g.
     *       DateTimeFormatter dtf  =
     *           DateTimeFormatter.ofPattern("G yyyy-MM-dd HH:mm:ss").withResolverStyle(ResolverStyle.STRICT);
     *  - Also have to include the era AD in the date string - prepend AD to the String parsed
     *       LocalDateTime localDateTime = LocalDateTime.parse("AD "+o.dateString(),dtf);
     *
     * SMART parsing is the default, but it may not actually have the desired effect
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

            CallableStatement cs = connection.prepareCall(
                    "{CALL storefront.addOrder(?,?,?,?)}"
            );

 //           DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
            DateTimeFormatter dtf  = DateTimeFormatter.ofPattern("G yyyy-MM-dd HH:mm:ss").withResolverStyle(ResolverStyle.STRICT);


         orders.forEach(o -> {
//             System.out.println(o.getDetailsJson())
             try{
 //                LocalDateTime localDateTime = LocalDateTime.parse(o.dateString(),dtf);
                 LocalDateTime localDateTime = LocalDateTime.parse("AD "+o.dateString(),dtf);
                 Timestamp timestamp = Timestamp.valueOf(localDateTime);
                 cs.setTimestamp(1,timestamp);
                 cs.setString(2,o.getDetailsJson());

                 cs.registerOutParameter(3, Types.INTEGER);
                 cs.registerOutParameter(4, Types.INTEGER);
                 cs.execute();
                 System.out.printf("%d records inserted for %d (%s)%n",
                         cs.getInt(4),
                         cs.getInt(3),
                         o.dateString());

             }catch (Exception e){
                 System.out.printf("Problem with %s : %s%n",o.dateString(),e.getMessage());
             }
         });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

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
}

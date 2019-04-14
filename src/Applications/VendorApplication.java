package Applications;

import java.sql.*;
import java.util.Scanner;
import java.lang.String;

public class VendorApplication {
    private Connection connection;
    private final String Vendor_DOESNT_EXIST="VendorId Doesn't exist\nPlease Sign-up!";
    private final String INCORRECT_PW="Incorrect password, Please enter again";

    public void createConnection(String location,
                                 String user,
                                 String password){
        try {

            //This needs to be on the front of your location
            String url = "jdbc:h2:" + location;

            //This tells it to use the h2 driver
            Class.forName("org.h2.Driver");

            //creates the connection
            connection = DriverManager.getConnection(url,
                    user,
                    password);
        } catch (SQLException | ClassNotFoundException e) {
            //You should handle this better
            e.printStackTrace();
        }
    }

    public String loginVendor(Connection connection, String vendor_id){
        String query="select vendor_name, password from vendor where vendor_id = "+"\'"+vendor_id+"\'\n";
        String vendor_name;
        String password;

        try {
            /**
             * Execute the query and return the result set
             */
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            result.next();

            vendor_name=result.getString("vendor_name");
            password=result.getString("password");


            Scanner pw= new Scanner(System.in);
            System.out.println("Please Enter your Password: ");
            String password_input =pw.nextLine();

            // if incorrect password
            while(!password_input.equals(password)){
                System.out.println(INCORRECT_PW);
                System.out.print("Please Enter your Password: ");
                password_input = pw.nextLine();
            }
            return "Welcome! " +vendor_name;


        } catch (SQLException e) {
            return Vendor_DOESNT_EXIST;
        }

    }

    public Connection getConnection(){
        return connection;
    }

    public void closeConnection(){
        try{
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void executeQuery(Statement stmt, String query) throws SQLException {
        //check if it is select
        Boolean ret = stmt.execute(query);
        if (ret) {
            ResultSet result = stmt.executeQuery(query);
            ResultSetMetaData rsmd = result.getMetaData();
            int columnCount = rsmd.getColumnCount();
            // The column count starts from 1

            //ArrayList<String> column_names = new ArrayList<String>();
            for (int i = 1; i <= columnCount; i++) {
                String name = rsmd.getColumnName(i);
                //column_names.add(name);
                System.out.format("|%-30s ",name);
                // Do stuff with name
            }
            System.out.println();

            while (result.next()) {
                for (int i = 0; i < columnCount; i++) {
                    System.out.format("|%-30s ",result.getString(i+1));
                }
                System.out.println();
            }
            // STEP 5: Clean-up environment
            result.close();
        }
    }

    public static void main(String[] args) {
        VendorApplication app = new VendorApplication();

        // Hard drive location of database
        String location = "./retailDb/retailDb";
        String user = "cskid";
        String password = "retaildomain";
        //Create database connection
        app.createConnection(location, user, password);

        //log in
        System.out.println("Please Enter VendorId: ");
        Scanner scan = new Scanner(System.in);
        String vendorID = scan.nextLine();
        String display = app.loginVendor(app.getConnection(), vendorID);
        System.out.println(display);


        Connection conn = app.getConnection();


        String query;
        try {
            Statement stmt = conn.createStatement();

            //check if they are delivered
            //TODO
            //String checkDate = "delete from reorder where Delivery_date < curdate();";
            //stmt.execute(checkDate);

            //create view
            query = "create view v as select * from Reorder where vendor_id="
                    +Integer.parseInt(vendorID)+";";
            stmt.execute(query);

            //test: print the view
            //query = "select * from v";
            //app.executeQuery(stmt,query);

            //query = "select count(*) as t from v;";
            //ResultSet c = stmt.executeQuery(query);
            //int count = c.getInt("t");
            //int count=0;
            //while(c.next()){
              //  count = c.getInt("count");
            //}

            //ResultSet result = stmt.executeQuery("select * from v;");
            //int count = result.getRow();

            ResultSet result = stmt.executeQuery("select count(*) as orderCount from reorder where vendor_id = '" +
                    vendorID + "' and delivery_date is null;");
            result.next();
            int count = result.getInt(1);
            System.out.println("You have "+count + " reorder requests.\n");
            while (true) {
                System.out.println("|------------------------------------------|");
                System.out.println("|     What would you like to do today?     |");
                System.out.println("|------------------------------------------|");
                System.out.println("|     1. Handle reorder requests!          |");
                System.out.println("|     2. View all the reorders.            |");
                System.out.println("|     3. Quit.                             |");
                System.out.println("|--Please enter the number of your choice--|");
                System.out.println("|------------------------------------------|\n");
                int choice = scan.nextInt();
                if (choice ==3) {
                    System.out.println("Good bye!");
                    break;
                }
                else if (choice == 2){
                    query = "select * from v;";
                    app.executeQuery(stmt, query);
                    continue;
                }
                else if (choice != 1){
                    System.out.println("Please enter the numbers that in the options.");
                    continue;
                }
                //handling the reorder
                query = "select * from v where delivery_date is null;";
                app.executeQuery(stmt, query);
                if (count == 0) {
                    System.out.println("No reorder requests that not handled.\n");
                } else {
                    //take input for the shipment
                    System.out.println("Which reorder would you like to handle? Enter reorder_id: ");
                    int reorder_id = scan.nextInt();
                    scan.nextLine(); //throw away the \n not consumed by nextInt()

                    System.out.println("Please Enter Shipment: ");
                    String shipment = scan.nextLine();

                    //take input for delivery date
                    System.out.println("Please Enter Delivery date: ");
                    String Delivery_date = scan.nextLine();

                    //update the table
                    query = "update reorder set shipment='" + shipment + "',Delivery_date='" + Delivery_date + "' where" +
                            " reorder_id='" +reorder_id+ "'";
                    app.executeQuery(stmt, query);

                    System.out.println("updated table:\n");

                    //Check if it is delivered
                    //TODO
                    //stmt.execute(checkDate);

                    //updated table
                    query = "select * from v";
                    app.executeQuery(stmt, query);

                    //update count
                    result = stmt.executeQuery("select count(*) as orderCount from reorder where vendor_id = '" +
                            vendorID + "' and delivery_date = '0';");
                    result.next();
                    count = result.getInt(1);
                }
            }
            //query = "drop table t;";
            //app.executeQuery(stmt,query);
            query = "drop view v;";
            app.executeQuery(stmt, query);
            app.closeConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{

        }

    }
}
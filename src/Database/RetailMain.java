package Database;

import Database.Populate.*;
import Database.Populate.Bakery.*;
import Database.Populate.Beverage.BeverageTable;
import Database.Populate.Grocery.*;
import Database.Populate.Pantry.Pantry;
import Database.Populate.Pantry.PantryTable;

import java.io.DataInput;
import java.sql.*;


public class RetailMain {

    private Connection conn;

    public void createConnection(String location,
                                 String user,
                                 String password){
        try {

            //This needs to be on the front of your location
            String url = "jdbc:h2:" + location;

            //This tells it to use the h2 driver
            Class.forName("org.h2.Driver");

            //creates the connection
            conn = DriverManager.getConnection(url,
                    user,
                    password);
        } catch (SQLException | ClassNotFoundException e) {
            //You should handle this better
            e.printStackTrace();
        }
    }

    /**
     * just returns the connection
     * @return: returns class level connection
     */
    public Connection getConnection(){
        return conn;
    }

    /**
     * When your database program exits
     * you should close the connection
     */
    public void closeConnection(){
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RetailMain rm = new RetailMain();

        //Hard drive location of the database
        String location = "./retailDb/retailDb";
        String user = "cskid";
        String password = "retaildomain";

        //Create the database connections, basically makes the database
        rm.createConnection(location, user, password);
        try {

            /**
             * Creates a sample Person table
             * and populates it from a csv file
             */
//            BakeryTable.createTable(rm.getConnection());
//            BakeryTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\Bakery\\bakery.csv");
//
//            BreadTable.createTable(rm.getConnection());
//            BreadTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\Bakery\\bread.csv");
//            PastryTable.createTable(rm.getConnection());
//            PastryTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\Bakery\\pastry.csv");
//            BeverageTable.createTable(rm.getConnection());
//            BeverageTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\Beverage\\beverage.csv");
//            DairyTable.createTable(rm.getConnection());
//            DairyTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\Grocery\\dairy.csv");
//            GroceryTable.createTable(rm.getConnection());
//            GroceryTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\Grocery\\grocery.csv");
//            MeatTable.createTable(rm.getConnection());
//            MeatTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\Grocery\\meat.csv");
//            ProduceTable.createTable(rm.getConnection());
//            ProduceTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\Grocery\\produce.csv");
//            PantryTable.createTable(rm.getConnection());
//            PantryTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\Pantry\\pantry.csv");
//            BrandTable.createTable(rm.getConnection());
//            BrandTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\brand.csv");
//            ContainsTable.createTable(rm.getConnection());
//            ContainsTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\contains.csv");
//            Cust_PhoneTable.createTable(rm.getConnection());
//            Cust_PhoneTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\cust_phone.csv");
//            CustomerTable.createTable(rm.getConnection());
//            CustomerTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\customer.csv");
//            IncludesTable.createTable(rm.getConnection());
//            IncludesTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\includes.csv");
//            OrderTable.createTable(rm.getConnection());
//            OrderTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\orders.csv");
            ProductsTable.createTable(rm.getConnection());
            ProductsTable.populateTable(
                    rm.getConnection(),
                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\products.csv");
//            StoreTable.createTable(rm.getConnection());
//            StoreTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\store.csv");
//            VendorTable.createTable(rm.getConnection());
//            VendorTable.populateTable(
//                    rm.getConnection(),
//                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\vendor.csv");
//





            /**
             * Just displays the table
             */

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }






}

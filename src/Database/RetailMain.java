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
            ProductsTable.createTable(rm.getConnection());
            ProductsTable.populateTable(
                    rm.getConnection(),
                    "C:\\Users\\abhay\\IdeaProjects\\Retail_Database\\src\\Datasets\\products.csv");

            /**
             * Just displays the table
             */

        } catch (SQLException e) {
            e.printStackTrace();
        }



    }






}

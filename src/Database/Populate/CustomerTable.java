package Database.Populate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerTable {

    public static void createCustomerTable(Connection conn){
        try {
            String query = "Create table customer(\n" +
                    "customer_id int primary key,\n" +
                    "first_name varchar(255),\n" +
                    "last_name varchar(255),\n" +
                    "num int,\n" +
                    "street varchar(255),\n" +
                    "city varchar(255),\n" +
                    "state varchar(255),\n" +
                    "zip varchar(255),\n" +
                    "email varchar(255),\n"+
                    "password varchar(255));" ;

            /**
             * Create a query and execute
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void populateCustomerTableFromCsv(Connection conn,
                                                  String fileName)
            throws SQLException {
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Customer> people = new ArrayList<Customer>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                people.add(new Customer(split));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String sql = createCustomerInsertSQL(people);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static String createCustomerInsertSQL(ArrayList<Customer> customers){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO customer (customer_id, first_name, last_name, num, street, city, state, zip, email, password) VALUES");

        /**
         * For each person append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last person add a comma to seperate
         *
         * If it is the last person add a semi-colon to end the statement
         */
        for(int i = 0; i < customers.size(); i++){
            Customer c = customers.get(i);
            sb.append(String.format("(%d,\'%s\',\'%s\',%d,\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\')",
                    c.getCustomer_id(), c.getFirst_name(), c.getLast_name(), c.getNum(), c.getStreet(), c.getCity(), c.getState(), c.getZip(), c.getEmail(),c.getPassword()));
            if( i != customers.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }
        return sb.toString();
    }

    public static void printCustomerTable(Connection connection) {
        String query = "SELECT * FROM customer;";
        try {
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while(result.next()){
                System.out.printf("Customer %d: %s %s %d %s %s %s %s %s\n",
                        result.getInt(1),
                        result.getString(2),
                        result.getString(3),
                        result.getInt(4),
                        result.getString(5),
                        result.getString(6),
                        result.getString(7),
                        result.getString(8),
                        result.getString(9));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
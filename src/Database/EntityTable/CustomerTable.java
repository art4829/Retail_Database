package Database.EntityTable;

import Database.Entity.Customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomerTable {
    public static void populatePersonTableFromCSV(Connection conn,
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
        String sql = createPersonInsertSQL(people);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static String createPersonInsertSQL(ArrayList<Customer> customers){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO customer (customer_id, first_name, last_name, num, street, city, state, zip, email) VALUES");

        /**
         * For each person append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last person add a comma to seperate
         *
         * If it is the last person add a semi-colon to end the statement
         */
        for(int i = 0; i < customers.size(); i++){
            Customer c = customers.get(i);
            sb.append(String.format("(%d,\'%s\',\'%s\',\'%d\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\')",
                    c.getCustomer_id(), c.getFirst_name(), c.getLast_name(), c.getNum(), c.getStreet(), c.getCity(), c.getState(), c.getZip(), c.getEmail()));
            if( i != customers.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }
        return sb.toString();
    }

}
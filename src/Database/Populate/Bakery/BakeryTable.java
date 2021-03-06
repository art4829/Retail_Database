package Database.Populate.Bakery;

import Database.Populate.Cust_Phone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BakeryTable {
    public static void createTable(Connection conn){
        try {
            String query = "Create table bakery(\n" +
                    "UPC varchar(255) primary key,\n" +
                    "serving varchar(255),\n" +
                    "expiration_date varchar(255));" ;

            /**
             * Create a query and execute
             */
            Statement stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void populateTable(Connection conn,
                                              String fileName)
            throws SQLException {
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Bakery> people = new ArrayList<Bakery>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                people.add(new Bakery(split));
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        String sql = createInsertSQL(people);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static String createInsertSQL(ArrayList<Bakery> customers){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO bakery (UPC, serving, expiration_date) VALUES");

        /**
         * For each person append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last person add a comma to seperate
         *
         * If it is the last person add a semi-colon to end the statement
         */
        for(int i = 0; i < customers.size(); i++){
            Bakery c = customers.get(i);
            sb.append(String.format("(\'%s\',\'%s\',\'%s\')",
                    c.getUPC(), c.getServing(), c.getExpiration_date()));
            if( i != customers.size()-1){
                sb.append(",");
            }
            else{
                sb.append(";");
            }
        }
        System.out.println(sb.toString());
        return sb.toString();
    }
}

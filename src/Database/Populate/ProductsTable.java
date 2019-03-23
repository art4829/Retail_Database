package Database.Populate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ProductsTable {
    public static void createProductsTable(Connection conn){
        try {
            String query = "Create table product(\n" +
                    "UPC varchar(255) primary key,\n" +
                    "name varchar(255),\n" +
                    "size real,\n" +
                    "price real,\n" +
                    "vendor_id varchar(255),\n" +
                    "brand_id varchar(255));" ;

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

    public static void populateProductsTable(Connection conn,
                                                    String fileName)
            throws SQLException {
        /**
         * Structure to store the data as you read it in
         * Will be used later to populate the table
         *
         * You can do the reading and adding to the table in one
         * step, I just broke it up for example reasons
         */
        ArrayList<Products> people = new ArrayList<Products>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                people.add(new Products(split));
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        String sql = createProductsInsertSQL(people);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static String createProductsInsertSQL(ArrayList<Products> customers){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO product (UPC, name, size, price, vendor_id, brand_id) VALUES");

        /**
         * For each person append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last person add a comma to seperate
         *
         * If it is the last person add a semi-colon to end the statement
         */
        for(int i = 0; i < customers.size(); i++){
            Products c = customers.get(i);
            sb.append(String.format("(\'%s\',\'%s\',%.2f,%.2f,\'%s\',\'%s\')",
                    c.getUPC(), c.getName(), c.getSize(), c.getPrice(), c.getVendor_id(), c.getBrand_id()));
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

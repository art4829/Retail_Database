package Database.Populate;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class IncludesTable {
    public static void createTable(Connection conn){
        try {
            String query = "Create table includes(\n" +
                    "order_id varchar(255),\n" +
                    "UPC varchar(255));" ;

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
        ArrayList<Includes> people = new ArrayList<Includes>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                people.add(new Includes(split));
            }
            br.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        String sql = createIncludesInsertSQL(people);

        /**
         * Create and execute an SQL statement
         *
         * execute only returns if it was successful
         */
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
    }

    public static String createIncludesInsertSQL(ArrayList<Includes> customers){
        StringBuilder sb = new StringBuilder();

        /**
         * The start of the statement,
         * tells it the table to add it to
         * the order of the data in reference
         * to the columns to ad dit to
         */
        sb.append("INSERT INTO includes (order_id, UPC) VALUES");

        /**
         * For each person append a (id, first_name, last_name, MI) tuple
         *
         * If it is not the last person add a comma to seperate
         *
         * If it is the last person add a semi-colon to end the statement
         */
        for(int i = 0; i < customers.size(); i++){
            Includes c = customers.get(i);
            sb.append(String.format("(\'%s\',\'%s\')",
                    c.getOrder_id(), c.getUPC()));
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

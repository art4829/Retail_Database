package Applications;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CheckRegisterApplication {
    private Connection connection;

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
        boolean ret = stmt.execute(query);
        if (ret) {
            ResultSet result = stmt.executeQuery(query);
            ResultSetMetaData rsmd = result.getMetaData();
            int columnCount = rsmd.getColumnCount();
            // The column count starts from 1

            ArrayList<String> column_names = new ArrayList<String>();
            for (int i = 1; i <= columnCount; i++) {
                String name = rsmd.getColumnName(i);
                column_names.add(name);
                System.out.print(name + " ");
                // Do stuff with name
            }
            System.out.println();

            while (result.next()) {
                for (int i = 0; i < columnCount; i++) {
                    System.out.print(result.getString(i + 1) + " ");
                }
                System.out.println();
            }
            // STEP 5: Clean-up environment
            result.close();
        }
    }

    public static void main( String[] args ) {
        CheckRegisterApplication app = new CheckRegisterApplication();
        String location = "./retailDb/retailDb";
        String user = "cskid";
        String password = "retaildomain";
        //Create database connection
        app.createConnection(location, user, password);

        Connection conn = app.getConnection();

        System.out.println("Enter sql command ('quit' to stop) : ");
        Scanner scan = new Scanner(System.in);
        String query = scan.nextLine();
        while (!query.equals("quit")) {
            try {

                Statement stmt = conn.createStatement();

                //check if returns
                app.executeQuery(stmt,query);

            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Enter sql command ('quit' to stop) : ");
            scan = new Scanner(System.in);
            query = scan.nextLine();
        }
    }
}

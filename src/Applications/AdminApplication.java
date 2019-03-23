package Applications;


import java.sql.*;
import java.util.Scanner;

public class AdminApplication {
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

    public static void main(String[] args) {
        AdminApplication app = new AdminApplication();
        // Hard drive location of database
        String location = "./retailDb/retailDb";
        String user = "cskid";
        String password = "retaildomain";
        //Create database connection
        app.createConnection(location, user, password);

        Connection conn = app.getConnection();
        System.out.println("Enter sql command: ");
        Scanner scan = new Scanner(System.in);
        String query = scan.nextLine();
        try {


            Statement stmt = conn.createStatement();
            ResultSet result = stmt.executeQuery(query);

            /*
            ResultSetMetaData rsmd = result.getMetaData();
            String name = rsmd.getColumnLabel(1);*/

            ResultSetMetaData rsmd = result.getMetaData();
            int columnCount = rsmd.getColumnCount();

// The column count starts from 1
            for (int i = 1; i <= columnCount; i++ ) {
                String name = rsmd.getColumnName(i);
                System.out.print(name + " ");
                // Do stuff with name
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}

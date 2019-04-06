package Applications;

import javax.naming.OperationNotSupportedException;
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

    public void editData( int UPC ) {

    }

    public static void main( String[] args ) {
        CheckRegisterApplication app = new CheckRegisterApplication();
        String location = "./retailDb/retailDb";
        String user = "cskid";
        String password = "retaildomain";
        //Create database connection
        app.createConnection(location, user, password);
        Connection conn = app.getConnection();

        //login
        OnlineApplication onlineApplication = new OnlineApplication();
        System.out.println("-------------CHECKOUT ITEMS-----------");
        System.out.println("Please enter 1 to LOGIN");
        System.out.println("Please enter 2 to SIGNUP");

        System.out.println("Enter sql command ('quit' to stop) : ");
        Scanner scan = new Scanner(System.in);
        String query = scan.nextLine();
        while (!query.equals("quit")) {
            try {

                Statement stmt = conn.createStatement();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Enter sql command ('quit' to stop) : ");
            scan = new Scanner(System.in);
            query = scan.nextLine();
        }
    }
}

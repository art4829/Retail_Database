package Applications;

import java.sql.*;
import java.util.Scanner;

public class VendorApplication {
    private Connection connection;
    private final String EMAIL_DOESNT_EXIST="Email Address Doesn't exist\nPlease Sign-up!";
    private final String INCORRECT_PW="Incorrect password, Please enter again";
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

    public String loginVendor(Connection connection, String vendor_id){
        String query="select vendor_name, password from vendor_id where email = "+"\'"+vendor_id+"\'\n";
        String vendor_name;
        String password;

        try {
            /**
             * Execute the query and return the result set
             */
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            result.next();

            vendor_name=result.getString("vendor_name");
            password=result.getString("password");


            Scanner pw= new Scanner(System.in);
            System.out.print("Please Enter your Password: ");
            String password_input =pw.nextLine();

            // if incorrect password
            while(!password_input.equals(password)){
                System.out.println(INCORRECT_PW);
                System.out.print("Please Enter your Password: ");
                password_input = pw.nextLine();
            }
            return "Welcome! " +vendor_name;


        } catch (SQLException e) {
            return EMAIL_DOESNT_EXIST;
        }

    }
}

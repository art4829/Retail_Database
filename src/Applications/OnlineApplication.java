package Applications;

import java.sql.*;
import java.util.Scanner;

public class OnlineApplication {

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

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if email is valid, if valid, returns welcome message
     * @param connection
     * @param email
     * @return
     */
    public String login(Connection connection, String email){
        String query="select first_name, last_name from customer where email = "+"\'"+email+"\'\n";
        String first_name;
        String last_name;
        try {
            /**
             * Execute the query and return the result set
             */
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            result.next();

            first_name=result.getString("first_name");
            last_name=result.getString("last_name");

            return "Welcome! " +first_name+" "+last_name;
        } catch (SQLException e) {
            return "Email Address Doesn't exist\nPlease Sign-up!";
        }

    }
    public static void main(String[] args) {

        OnlineApplication app = new OnlineApplication();
        // Hard drive location of database
        String location = "./retailDb/retailDb";
        String user = "cskid";
        String password = "retaildomain";
        //Create database connection
        app.createConnection(location, user, password);
        Scanner scan = new Scanner(System.in);

        System.out.println("-------------WELCOME TO RETAIL-----------");
        System.out.println("Please enter 1 to LOGIN");
        System.out.println("Please enter 2 to SIGNUP");
        int check = scan.nextInt();

        if(check==1) {
            // Get email to check
            System.out.println("Please Enter your Email: ");
            Scanner scan2=new Scanner(System.in);
            String email = scan2.nextLine();

            String display = app.login(app.getConnection(), email);
            System.out.println(display);
        }else if(check==2){
            System.out.println("Registering you");
        }






    }
}

package Applications;

import java.sql.*;
import java.util.Scanner;

public class OnlineApplication {

    private Connection connection;
    private final String EMAIL_DOESNT_EXIST="Email Address Doesn't exist\nPlease Sign-up!";
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
            return EMAIL_DOESNT_EXIST;
        }
    }


    /**
     * Register the user
     * @param app this class object
     */
    public void register(OnlineApplication app){
        String first_name;
        String last_name;
        String email;
        // you have to create more variables and prompt the user .
        String address; // Remember address is divied into - num, street, city, state, zip
        Scanner scanner= new Scanner(System.in);
        System.out.print("Please enter your first name: ");
        first_name = scanner.nextLine();
        System.out.print("Please enter your last name:");
        last_name= scanner.nextLine();
        System.out.print("Please enter your Email: ");
        email= scanner.nextLine();
        String check = app.login(app.getConnection(),email);
        if (check.startsWith("Welcome")){
            System.out.println("Email already exists, please enter a new one");
        }else{
            System.out.println("Welcome! "+ first_name+" "+ last_name);

            // add to database
//            try{
//                String insert="insert into customer";
//                Statement stmt = connection.createStatement();
//                stmt.execute(insert);
//            }catch (SQLException e){
//                System.out.println(e.getMessage());
//            }
        }
    }

    /**
     * Run the application
     * @param args
     */
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
            app.register(app);

        }






    }
}

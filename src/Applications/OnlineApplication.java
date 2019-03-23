package Applications;

import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class OnlineApplication {

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
        String query="select first_name, last_name, password from customer where email = "+"\'"+email+"\'\n";
        String first_name;
        String last_name;
        String password;

        try {
            /**
             * Execute the query and return the result set
             */
            Statement stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery(query);
            result.next();

            first_name=result.getString("first_name");
            last_name=result.getString("last_name");
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
        int num;
        String street;
        String city;
        String state;
        String zip;
        String password;
        String passwordCheck;


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
            System.out.print("Please enter a password for your account: ");
            password = scanner.nextLine();
            System.out.print("Please Re-enter Password: ");
            passwordCheck = scanner.nextLine();
            while(!passwordCheck.equals(password)){
                System.out.println("Passwords Don't Match, Re-enter");
                passwordCheck = scanner.nextLine();
            }
            System.out.print("Please enter your Street Number: ");
            num=Integer.parseInt(scanner.nextLine());
            System.out.print("Please enter your Street Name: ");
            street=scanner.nextLine();
            System.out.print("Please enter your City: ");
            city=scanner.nextLine();
            System.out.print("Please enter your State: ");
            state=scanner.nextLine();
            System.out.print("Please enter your ZipCode: ");
            zip=scanner.nextLine();

            // add to database
            try{

                String count_query= "select count(customer_id) as count from customer";
                Statement stmt = connection.createStatement();

                ResultSet r= stmt.executeQuery(count_query);
                r.next();
                int count = r.getInt("count") + 1;

                // Building sql query to insert
                StringBuilder sb = new StringBuilder();

                sb.append("INSERT INTO customer (customer_id, first_name, last_name, num, street, city, state, zip, email, password) VALUES");
                sb.append(String.format("(%d,\'%s\',\'%s\',%d,\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\')",
                        count, first_name, last_name, num, street, city, state, zip, email, password));
                // executing the sql command to insert
                stmt.execute(sb.toString());
                System.out.println("Welcome! " +first_name+" "+last_name);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
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
            // Check login and display
            String display = app.login(app.getConnection(), email);
            System.out.println(display);
        }else if(check==2){
            // If sign up, Register user
            System.out.println("Registering you");
            app.register(app);
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Welcome To Our Online Store: ");



    }
}

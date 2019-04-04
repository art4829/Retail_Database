package Applications;

import Database.Populate.Products;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.zip.CheckedOutputStream;

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

    public ArrayList<String> viewItem(Connection connection, String item){
        ArrayList<String> arry= new ArrayList<String>();
        try{
            String query = "SELECT name \n" +
                    "  FROM "+item+" JOIN Product\n" +
                    "    ON Product.UPC = "+item+".UPC;";

            Statement stmt = connection.createStatement();

            ResultSet r= stmt.executeQuery(query);
            while(r.next()){
                arry.add(r.getString(1));
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return arry;
    }

    public String getUPC(String name, Connection connection){
        String retString="";
        try {
            String query = "SELECT UPC FROM  Product where name='" + name + "'";

            Statement stmt = connection.createStatement();

            ResultSet r = stmt.executeQuery(query);
            r.next();
            retString=r.getString(1);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return retString;
    }

    public String getPrice(String UPC){
        String price="";
        try {
            String query = "Select price from product \n" +
                    "where UPC = '"+UPC+"';";

            Statement stmt = connection.createStatement();

            ResultSet r = stmt.executeQuery(query);
            r.next();
            price =r.getString(1);
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return price;
    }

    public void buyProduct(String UPC){
        String amount="";
        int updateAmt=0;
        try {
            String query = "Select amount from contains \n" +
                    "where UPC = '"+UPC+"' and store_id = '5';";

            Statement stmt = connection.createStatement();

            ResultSet r = stmt.executeQuery(query);
            r.next();
            amount=r.getString(1);
            updateAmt=Integer.parseInt(amount)-1;
            String updatequery="Update contains\n" +
                    "set amount='"+updateAmt+"' where upc='"+UPC+"' and store_id='5';";
            Statement stm2= connection.createStatement();
            stm2.execute(updatequery);
        }catch(SQLException e){
            System.out.println(e.getMessage());
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

        if (check == 1) {
            // Get email to check
            System.out.println("Please Enter your Email: ");
            Scanner scan2 = new Scanner(System.in);
            String email = scan2.nextLine();
            // Check login and display
            String display = app.login(app.getConnection(), email);
            System.out.println(display);
        } else if (check == 2) {
            // If sign up, Register user
            System.out.println("Registering you");
            app.register(app);
        }
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Welcome To Our Online Store! \n\n");
        while(true) {
            System.out.println("What Product will you like to Buy today?\n");
            System.out.println("Press 1 for Bakery");
            System.out.println("Press 2 for Beverage");
            System.out.println("Press 3 for Grocery");
            System.out.println("Press 4 for Pantry\n\n");

            int product_choice = scan.nextInt();
            int count;
            ArrayList<String> itms;
            int answer = 0;
            Map<Integer, String> menu= new HashMap<Integer, String>();

            switch (product_choice) {
                case 1:
                    System.out.println("Press 1 for Bakery");
                    System.out.println("Press 2 for Bread");
                    System.out.println("Press 3 for Pastry\n\n");
                    int bakery_choice = scan.nextInt();

                    switch (bakery_choice) {
                        case 1:
                            count = 1;
                            itms = app.viewItem(app.getConnection(), "bakery");
                            System.out.println("Please enter the number of the Item:- ");
                            for (String it : itms) {
                                System.out.println(count + ": " + it);
                                menu.put(count, it);
                                count += 1;
                            }
                            answer = scan.nextInt();
                            break;
                        case 2:
                            count = 1;
                            itms = app.viewItem(app.getConnection(), "bread");
                            System.out.println("Please enter the number of the Item:- ");
                            for (String it : itms) {
                                System.out.println(count + ": " + it);
                                menu.put(count, it);
                                count += 1;
                            }
                            answer = scan.nextInt();
                            break;
                        case 3:
                            count = 1;
                            itms = app.viewItem(app.getConnection(), "pastry");
                            System.out.println("Please enter the number of the Item:- ");
                            for (String it : itms) {
                                System.out.println(count + ": " + it);
                                menu.put(count, it);
                                count += 1;
                            }
                            answer = scan.nextInt();
                            break;
                    }
                    break;
                case 2:
                    count = 1;
                    itms = app.viewItem(app.getConnection(), "beverage");
                    System.out.println("Please enter the number of the Item:- ");
                    for (String it : itms) {
                        System.out.println(count + ": " + it);
                        menu.put(count, it);
                        count += 1;
                    }
                    answer = scan.nextInt();
                    break;
                case 3:
                    System.out.println("Press 1 for Dairy");
                    System.out.println("Press 2 for Grocery");
                    System.out.println("Press 3 for Meat");
                    System.out.println("Press 4 for Produce\n\n");
                    int grocery_choice = scan.nextInt();

                    switch (grocery_choice) {
                        case 1:
                            count = 1;
                            itms = app.viewItem(app.getConnection(), "dairy");
                            System.out.println("Please enter the number of the Item:- ");
                            for (String it : itms) {
                                System.out.println(count + ": " + it);
                                menu.put(count, it);
                                count += 1;
                            }
                            answer = scan.nextInt();
                            break;
                        case 2:
                            count = 1;
                            itms = app.viewItem(app.getConnection(), "grocery");
                            System.out.println("Please enter the number of the Item:- ");
                            for (String it : itms) {
                                System.out.println(count + ": " + it);
                                menu.put(count, it);
                                count += 1;
                            }
                            answer = scan.nextInt();
                            break;
                        case 3:
                            count = 1;
                            itms = app.viewItem(app.getConnection(), "meat");
                            System.out.println("Please enter the number of the Item:- ");
                            for (String it : itms) {
                                System.out.println(count + ": " + it);
                                menu.put(count, it);
                                count += 1;
                            }
                            answer = scan.nextInt();
                            break;
                        case 4:
                            count = 1;
                            itms = app.viewItem(app.getConnection(), "produce");
                            System.out.println("Please enter the number of the Item:- ");
                            for (String it : itms) {
                                System.out.println(count + ": " + it);
                                menu.put(count, it);
                                count += 1;
                            }
                            answer = scan.nextInt();
                            break;
                    }
                    break;
                case 4:
                    count = 1;
                    itms = app.viewItem(app.getConnection(), "pastry");
                    System.out.println("Please enter the number of the Item:- ");
                    for (String it : itms) {
                        System.out.println(count + ": " + it);
                        menu.put(count, it);
                        count += 1;
                    }
                    answer = scan.nextInt();
                    break;

            }
            System.out.println("\n You have chosen: " + menu.get(answer));
            String UPC = app.getUPC(menu.get(answer), app.getConnection());
            System.out.println("The price for the chosen item is: " + app.getPrice(UPC));
            System.out.println("Do you still want to buy it?(Y/N)");
            Scanner buyoption = new Scanner(System.in);
            String option = buyoption.nextLine().toLowerCase();


            if(option.equals("y")){
                    app.buyProduct(UPC);
                    System.out.println("Congratulations, you have succesfully bought "+menu.get(answer));
            }
            System.out.println("Would you like to look at another item?(Y/N)");
            String option2 = buyoption.nextLine().toLowerCase();
            if(option2.equals("n")){
                break;
            }
        }

    }
}

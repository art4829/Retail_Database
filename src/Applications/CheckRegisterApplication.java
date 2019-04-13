package Applications;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class CheckRegisterApplication extends MethodCalls {
    private Connection connection;


    public void buyProduct(String UPC) {
        String amount = "";
        int updateAmt = 0;
        try {
            String query = "Select amount from contains \n" +
                    "where UPC = '" + UPC + "' and store_id = '5';";
            Statement stmt = connection.createStatement();

            ResultSet r = stmt.executeQuery(query);
            r.next();
            amount = r.getString(1);
            updateAmt = Integer.parseInt(amount) - 1;
            String updatequery = "Update contains\n" +
                    "set amount='" + updateAmt + "' where upc='" + UPC + "' and store_id='5';";

            Statement stm2 = connection.createStatement();
            stm2.execute(updatequery);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main( String[] args ) {
        MethodCalls app = new CheckRegisterApplication();

        // Hard drive location of database
        String location = "./retailDb/retailDb";
        String user = "cskid";
        String password = "retaildomain";
        //Create database connection
        app.createConnection(location, user, password);
        Scanner scan = new Scanner(System.in);

        System.out.println("|-----------------------------------------------------|");
        System.out.println("|-------------WELCOME TO RETAIL-----------------------|");
        System.out.println("|----------Please enter 1 to LOGIN--------------------|");
        System.out.println("|----------Please enter 2 to SIGNUP-------------------|");
        System.out.println("|----------Please enter 3 to CHECKOUT AS GUEST--------|");
        System.out.println("|----------Please enter 0 to SIGN OUT-----------------|");
        System.out.println("|-----------------------------------------------------|");
        int check = scan.nextInt();
        String email = "";
        if (check == 1) {
            // Get email to check
            System.out.println("Please Enter your Email: ");
            Scanner scan2 = new Scanner(System.in);
            email = scan2.nextLine();
            // Check login and display
            String display = app.login(app.getConnection(), email);

            System.out.println(display);
            if (display.equals(EMAIL_DOESNT_EXIST)) {
                email=app.register(app);
            }
        } else if (check == 2) {
            // If sign up, Register user
            System.out.println("Registering you");
            email = app.register(app);
        }


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n\n|--------------------------------------|");
        System.out.println("|     Welcome To Our Online Store!     |");
        System.out.println("| To SignOut please press 0 at anytime |");
        System.out.println("|--------------------------------------|\n\n");

        while(true) {

            System.out.println("|-----------------------|");
            System.out.println("| Press 1 to Checkout   |");
            System.out.println("| Press 0 to Sign out   |");
            System.out.println("|-----------------------|");
            int answer = scan.nextInt();
            int storeID = 0;

            if( answer == 1 ) {
                while(true) {
                    System.out.println("Please enter the store ID: ");
                    storeID = scan.nextInt();

                    //Checkif valid store ID
                    if( storeID > 0 && storeID < 5 ) {
                        break;
                    }
                    System.out.println("Invalid store ID.");
                }

                System.out.println("Please enter the product UPC: ");
                String UPC = "";
                UPC = scan.next();

                //reduce the number of item in the database
                System.out.println("\n\n");
                System.out.println(">>>>>>>----- The price for the chosen item is: " + app.getPrice(UPC));
                System.out.println(">>>>>>>----- Do you still want to buy it?(Y/N)");

                String option = "";
                option = scan.next();

                //check if the customer still wants to buy the item
                if( option.toLowerCase().equals("y") || option.toLowerCase().equals("yes") ) {
                    app.buyProductStore(UPC,storeID);
                    System.out.println(">>>>>>>----- Congratulations, you have successfully bought the item\n\n");
                }

            } else if( answer == 0 ) {
                break;
            }
        }
    }
}
